package br.ufpe.cin.algoritmos.web;

import java.io.PrintWriter;

public abstract class Result {

	public abstract String getContentType();

	public String getCharset() {
		return "utf-8";
	}

	public abstract void render(PrintWriter writer);
}