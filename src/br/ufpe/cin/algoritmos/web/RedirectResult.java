package br.ufpe.cin.algoritmos.web;

import java.io.PrintWriter;

public class RedirectResult extends Result {

	private String url;

	public RedirectResult(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public void render(PrintWriter writer) {

	}
}
