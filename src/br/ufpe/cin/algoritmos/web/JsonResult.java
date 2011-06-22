package br.ufpe.cin.algoritmos.web;

import java.io.PrintWriter;

class JsonResult extends Result {
	private Object object;

	public JsonResult(Object object) {
		this.object = object;
	}

	@Override
	public String getContentType() {
		return "application/json";
	}

	@Override
	public void render(PrintWriter writer) {
		// TODO Auto-generated method stub

	}
}
