package br.ufpe.cin.algoritmos.controllers;

import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class IndexController extends Controller {

	public Result get() {

		return html("index", "World");
	}
}

