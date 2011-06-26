package br.ufpe.cin.algoritmos.controllers;

import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class ControllerBase extends Controller {

	@Override
	public Result beforeRequest() {

		
		boolean autenticado = true;

		if (autenticado)
			return null;

		return redirect("index");

	}

}
