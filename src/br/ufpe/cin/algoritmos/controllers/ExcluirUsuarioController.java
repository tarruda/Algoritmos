package br.ufpe.cin.algoritmos.controllers;

import javax.servlet.http.Cookie;

import br.ufpe.cin.algoritmos.models.Usuario;
import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class ExcluirUsuarioController extends ControllerBase {

	public Result post(String id) {

		Cookie[] cookies = request.getCookies();
		boolean temMesa = false;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("idMesa"))
				temMesa = true;
		}

		if (temMesa)
			Usuario.list.remove(new Usuario(Integer.decode(id), null, null));

		return redirect("usuarios");
	}
}
