package br.ufpe.cin.algoritmos.db;

import java.nio.ByteBuffer;

class ByteStream {

	private ByteBuffer buffer;

	public ByteStream(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	public final int getInt() {
		return buffer.getInt();
	}

	public final ByteStream putInt(int value) {
		buffer.putInt(value);
		return this;
	}

	public final char getChar() {
		return buffer.getChar();
	}

	public final ByteStream putChar(char value) {
		buffer.putChar(value);
		return this;
	}

	public final int position() {
		return buffer.position();
	}

	public final ByteStream position(int position) {
		buffer.position(position);
		return this;
	}

	public final int remaining() {
		return buffer.remaining();
	}

	public final int limit() {
		return buffer.limit();
	}

	// public final ObjectType readObjectType() {
	// String typeName = readString();
	// return new ObjectType(typeName);
	// }
	//
	// public final void writeObjectType(ObjectType objectType) {
	// writeString(objectType.getTypeName());
	// }

}
