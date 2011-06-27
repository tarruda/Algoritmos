package br.ufpe.cin.algoritmos.controllers;

import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class ControllerBase extends Controller {

	@Override
	public Result beforeRequest() {
		String userName = getUserName();
		if (userName == null || userName.equals("-1"))
			return redirect("/login");
		return null;
	}
	
}
