package br.ufpe.cin.algoritmos.db;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel.MapMode;

public class FileDatabase extends Database {

	private static final int maxLength = Integer.MAX_VALUE;

	public FileDatabase(String fileName) throws IOException {
		this(new File(fileName));
	}

	public FileDatabase(File file) throws IOException {
		super(new ByteStream(getMappedBuffer(file)));
	}
	
	private static MappedByteBuffer getMappedBuffer(File file) throws IOException{
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		FileChannel channel = raf.getChannel();
		FileLock lock = channel.tryLock();
		if (lock == null)
			throw new IOException("Database file locked.");
		return channel.map(MapMode.READ_WRITE, 0, maxLength);		
	}
}
