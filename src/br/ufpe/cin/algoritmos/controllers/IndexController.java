package br.ufpe.cin.algoritmos.controllers;

import java.util.HashMap;

import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class IndexController extends Controller {

	public Result get() {
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("title", "Título");
		m.put("name", "Thiago");
		return html("index", m);
	}
}
