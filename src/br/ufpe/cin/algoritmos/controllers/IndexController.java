package br.ufpe.cin.algoritmos.controllers;

import javax.servlet.http.Cookie;

import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class IndexController extends ControllerBase {

	public Result get() {
		//response.addCookie(new Cookie("idMesa", "1"));
		return html("index", "World");
	}
}

