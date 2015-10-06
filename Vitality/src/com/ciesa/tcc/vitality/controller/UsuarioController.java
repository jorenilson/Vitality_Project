package com.ciesa.tcc.vitality.controller;

import java.util.List;

import android.content.Context;

import com.ciesa.tcc.vitality.dao.UsuarioDao;
import com.ciesa.tcc.vitality.model.Usuario;
import com.ciesa.tcc.vitality.view.MainActivity;

public class UsuarioController {

	private static UsuarioDao usuarioDao;
	private static UsuarioController instance;
	private static MainActivity principal;

	public static UsuarioController getInstance(Context context) {
		if (instance == null) {
			instance = new UsuarioController();
			principal = new MainActivity();
			usuarioDao = new UsuarioDao(context);
		}
		return instance;
	}
	
	/*
	 * Realiza a inserção de novo usuário no banco de dados.
	 */
	public void inserir(Usuario usuario) throws Exception{
		usuarioDao.inserirUsuario(usuario);
	}
	
	/*
	 *  Consulta no banco de dados se o usuário informado já existe.
	 *  Caso já exista, o sistema retorna true ou false.
	 */
	public boolean consultaUsuario(String usuario){
		return usuarioDao.consultaUsuario(usuario);
	}
	
	public boolean validaLogin(String usuario, String senha) throws Exception{
		Usuario user = usuarioDao.findByLogin(usuario, senha);
		if(user == null || user.getUsuario() == null || user.getSenha() == null){
			return false;
		}
		
		String informado = usuario+senha;
		String esperado = user.getUsuario()+user.getSenha();
		if(informado.equals(esperado)){
			return true;
		}
		return false;
	}
	
	public List<Usuario> findAll() throws Exception{
		return usuarioDao.findAll();
	}
}
