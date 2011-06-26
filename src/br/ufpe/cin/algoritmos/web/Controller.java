package br.ufpe.cin.algoritmos.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	protected final Result html(String name) {
		return new HtmlResult(name, null);
	}

	protected final Result html(String name, Object model) {
		return new HtmlResult(name, model);
	}

	protected final Result json(Object obj) {
		return new JsonResult(obj);
	}

	protected final Result redirect(String url) {
		return new RedirectResult(url);
	}

	public void setRequest(HttpServletRequest req) {
		this.request = req;
	}

	public void setResponse(HttpServletResponse resp) {
		this.response = resp;
	}

	public Result beforeRequest() {
		return null;
	}
}
