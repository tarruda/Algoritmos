package br.ufpe.cin.algoritmos.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.algoritmos.models.Message;

public class FileDatabase {

	private static Object dbLock = new Object();
	private static FileDatabase instance;

	public static FileDatabase getInstance() throws IOException,
			ClassNotFoundException {
		if (instance == null)
			synchronized (dbLock) {
				if (instance == null)
					instance = new FileDatabase("algoritmos.db");				
			}
		return instance;
	}

	private Manifest manifest;
	private File dbFile;
	private File idxFile;

	protected FileDatabase(String dbFileName) throws IOException,
			ClassNotFoundException {
		dbFile = new File(dbFileName);
		String idxFileName = "." + dbFileName;
		idxFile = new File(idxFileName);

		if (!dbFile.exists())
			dbFile.createNewFile();
		
		manifest = initialize(idxFile);
	}

	

	private Manifest initialize(File idxFile) throws IOException,
			ClassNotFoundException {
		Manifest ret = null;

		if (idxFile.exists()) {
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(idxFile)));
			ret = (Manifest) ois.readObject();
		} else
			ret = new Manifest();

		return ret;
	}

	public void insert(Message message) throws FileNotFoundException,
			IOException, InterruptedException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(buffer);
		oos.writeObject(message);
		byte[] bytes = buffer.toByteArray();

		synchronized (dbLock) {
			int pos = (int) dbFile.length();

			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(dbFile, true));

			fos.write(bytes);
			fos.flush();

			String fromKey = message.getFrom();
			String toKey = message.getTo();
			updateIndex(pos, fromKey, manifest.getFromUserIndex());
			updateIndex(pos, toKey, manifest.getToUserIndex());
			flushIndex();
		}
	}

	public List<Message> messagesFrom(String userName) throws IOException,
			ClassNotFoundException {
		return readMessages(manifest.getFromUserIndex().lookup(userName));
	}

	public List<Message> messagesTo(String userName) throws IOException,
			ClassNotFoundException {
		return readMessages(manifest.getToUserIndex().lookup(userName));
	}

	private List<Message> readMessages(Iterable<Integer> positions)
			throws IOException, ClassNotFoundException {
		ArrayList<Message> ret = new ArrayList<Message>();
		synchronized (dbLock) {
			if (positions != null) {
				RandomAccessFile raf = new RandomAccessFile(dbFile, "r");
				for (Integer pos : positions) {
					raf.seek(pos);
					ret.add(readMessage(raf));
				}
			}
		}
		return ret;
	}

	private Message readMessage(RandomAccessFile raf) throws IOException,
			ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new RAFInputStream(raf));
		return (Message) ois.readObject();
	}

	private void updateIndex(int pos, String key, DatabaseIndex index) {
		List<Integer> positions = index.lookup(key);
		if (positions == null) {
			positions = new ArrayList<Integer>();
			index.insert(key, positions);
		}
		positions.add(pos);
	}
	
	private void flushIndex() throws FileNotFoundException, IOException {
		synchronized (dbLock) {
			if(!idxFile.exists())
			idxFile.delete();
			idxFile.createNewFile();		
			ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(idxFile)));
			oos.writeObject(manifest);
			oos.flush();
			oos.close();
		}
		
	}
	
	private class RAFInputStream extends InputStream {

		private RandomAccessFile file;

		public RAFInputStream(RandomAccessFile file) {
			this.file = file;
		}

		@Override
		public int read() throws IOException {
			return file.read();
		}
	}
}
