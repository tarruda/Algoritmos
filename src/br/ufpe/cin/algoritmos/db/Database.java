package br.ufpe.cin.algoritmos.db;


class Database {

	private ByteStream byteStream;
	private Manifest manifest;

	public Database(ByteStream byteStream) {
		manifest = initialize(byteStream);

	}

	public ByteStream getByteStream() {
		return byteStream;
	}

	private Manifest initialize(ByteStream byteStream) {
		return null;
		
	}
}
