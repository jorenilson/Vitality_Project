package com.ciesa.tcc.vitality.model;

import java.io.Serializable;
/**
 * Classe responsável pelo armazenamento dos campos da tabela
 * 
 * @author Jorenilson Lopes
 *
 */
public class Usuario {
//	private static final long serialVersionUID = 1633833011084400384L;
	
	public int id;
	public String nome;
	public String idade;
	public String usuario;
	public String email;
	public String senha;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}
	
	
	
}
