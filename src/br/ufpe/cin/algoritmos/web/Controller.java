package br.ufpe.cin.algoritmos.web;

public abstract class Controller {

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
}
