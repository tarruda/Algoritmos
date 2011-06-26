package br.ufpe.cin.algoritmos.controllers;

import java.util.ArrayList;

import javax.servlet.http.Cookie;

import br.ufpe.cin.algoritmos.models.Usuario;
import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class UsuariosController extends ControllerBase {
		
	public Result get() {			
		return html("usuarios", Usuario.list);
	}
	
	public Result post(String id, String nome, String telefone) {
		//TODO tirar isso daqui
		
		Usuario u = new Usuario(Integer.decode(id), nome, telefone);
		Usuario.list.add(u);
		return redirect("/usuarios");
	}

}
