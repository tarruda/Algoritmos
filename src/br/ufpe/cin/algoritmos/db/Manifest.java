package br.ufpe.cin.algoritmos.db;

import java.io.Serializable;

public class Manifest implements Serializable {

	private static final long serialVersionUID = -3314061327482200635L;
	private DatabaseIndex toUserIndex;
	private DatabaseIndex fromUserIndex;

	public DatabaseIndex getToUserIndex() {
		if (toUserIndex == null)
			toUserIndex = new DatabaseIndex();
		return toUserIndex;
	}

	public DatabaseIndex getFromUserIndex() {
		if (fromUserIndex == null)
			fromUserIndex = new DatabaseIndex();
		return fromUserIndex;
	}

}
