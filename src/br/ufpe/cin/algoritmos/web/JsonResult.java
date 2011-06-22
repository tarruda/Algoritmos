package br.ufpe.cin.algoritmos.web;

import java.io.PrintWriter;

import com.google.gson.Gson;

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
		String ret = "null";
		Gson serializer = new Gson();
		if (object != null)
			ret = serializer.toJson(object);
		writer.write(ret);
	}
}
