package br.ufpe.cin.algoritmos.controllers;

import javax.servlet.http.Cookie;

import br.ufpe.cin.algoritmos.web.Result;

public class LogoutController extends ControllerBase {

	@Override
	public Result get() {		
		response.addCookie(new Cookie("userName", "-1"));
		return redirect("/inbox");
	}

}
