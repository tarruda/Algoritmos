package br.ufpe.cin.algoritmos.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.algoritmos.models.Message;

public class FileDatabase {

	private static final int maxLength = Integer.MAX_VALUE;
	private static FileDatabase instance;

	public static FileDatabase getInstance() throws IOException,
			ClassNotFoundException {
		if (instance == null)
			instance = new FileDatabase("algoritmos.db");
		return instance;
	}

	private Manifest manifest;
	private File dbFile;
	private File idxFile;
	private Object writeLock;

	protected FileDatabase(String dbFile) throws IOException,
			ClassNotFoundException {
		writeLock = new Object();
		manifest = initialize(dbFile);
	}

	private Manifest initialize(String dbFile) throws IOException,
			ClassNotFoundException {
		Manifest ret = null;
		String idxFileName = "." + dbFile + ".idx";
		File file = new File(idxFileName);
		if (file.exists()) {
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(file)));
			ret = (Manifest) ois.readObject();
		} else
			ret = new Manifest();

		return ret;
	}

	public void insert(Message message) throws FileNotFoundException,
			IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(buffer);
		oos.writeObject(message);
		byte[] bytes = buffer.toByteArray();

		synchronized (writeLock) {
			int pos = (int) dbFile.length();
			BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(dbFile));
			fos.write(bytes);
			
			String fromKey = message.getFrom();
			String toKey = message.getTo();			
			updateIndex(pos, fromKey, manifest.getFromUserIndex());
			updateIndex(pos, toKey, manifest.getToUserIndex());
		}
	}
	

	private void updateIndex(int pos, String key, DatabaseIndex index){
		List<Integer> positions = index.lookup(key);
		if(positions == null) {
			positions = new ArrayList<Integer>();
			manifest.getFromUserIndex().insert(key, positions);
		}
		positions.add(pos);
	}
}
