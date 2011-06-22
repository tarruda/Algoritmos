package br.ufpe.cin.algoritmos.controllers;

import java.util.ArrayList;

import br.ufpe.cin.algoritmos.model.Usuario;
import br.ufpe.cin.algoritmos.web.Controller;
import br.ufpe.cin.algoritmos.web.Result;

public class UsuariosController extends Controller {
	private static final ArrayList<Usuario> list = new ArrayList<Usuario>();
	
	static{
		list.add(new Usuario(1, "Thiago", "323242424"));
		list.add(new Usuario(2, "Sebastiao", "433"));
		list.add(new Usuario(3, "Brunno", "2332"));
		list.add(new Usuario(4, "Danilo", "4554"));
	}
	
		
	public Result get() {			
		return html("usuarios", list);
	}
	
	public Result post(String id, String nome, String telefone) {
		Usuario u = new Usuario(Integer.decode(id), nome, telefone);
		list.add(u);
		return redirect("/usuarios");
	}

}
