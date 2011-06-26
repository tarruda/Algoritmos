package br.ufpe.cin.algoritmos.models;

import java.util.ArrayList;

public class Usuario {

	private Integer id;
	private String nome;
	private String telefone;

	public static final ArrayList<Usuario> list = new ArrayList<Usuario>();

	static {
		list.add(new Usuario(1, "Thiago", "323242424"));
		list.add(new Usuario(2, "Sebastiao", "433"));
		list.add(new Usuario(3, "Brunno", "2332"));
		list.add(new Usuario(4, "Danilo", "4554"));
	}

	public Usuario(Integer id, String nome, String telefone) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public boolean equals(Object arg0) {
		if(id == null)
			return false;
		if (!(arg0 instanceof Usuario))
			return false;
		Usuario other = (Usuario) arg0;
		return id.equals(other.id);
	}

}
