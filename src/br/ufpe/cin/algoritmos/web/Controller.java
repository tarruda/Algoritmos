package br.ufpe.cin.algoritmos.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	protected final Result html(String name) {
		return new HtmlResult(name, null, request.getHeader("User-Agent"),
				getUserName());
	}

	protected final Result html(String name, Object model) {
		return new HtmlResult(name, model, request.getHeader("User-Agent"),
				getUserName());
	}

	protected final Result json(Object obj) {
		return new JsonResult(obj);
	}
	
	protected String getUserName() {
		String ret = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : request.getCookies())
				if (cookie.getName().equals("userName"))
					ret = cookie.getValue();
		return ret;
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

	public Result get() {
		return null;
	}

	public Result post() {
		return null;
	}
}
