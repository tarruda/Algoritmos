package br.ufpe.cin.algoritmos.controllers;

import javax.servlet.http.Cookie;

import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class LoginController extends Controller {

	@Override
	public Result get() {
		String userName = getUserName();		
		if(userName != null && !userName.equals("-1")) 			
			return redirect("/logout");
		return html("login");
	}

	@Override
	public Result post() {	
		String userName = request.getParameter("userName");		
		if (!userName.equals("-1")) {
			Cookie cookie = new Cookie("userName", userName);
			//cookie.setMaxAge(60);
			response.addCookie(cookie);
		}
		return redirect("/inbox");
	}

}
