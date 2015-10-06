package com.ciesa.tcc.vitality.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ciesa.tcc.vitality.model.Usuario;

public class UsuarioDao extends DataBase {
	private final String NOME_TABELA = "usuario";

	public UsuarioDao(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void inserirUsuario(Usuario usuario) throws Exception{
		ContentValues campos = new ContentValues();
		campos.put(KEY_NOME, usuario.getNome());
		campos.put(KEY_IDADE, usuario.getIdade());
		campos.put(KEY_USUARIO, usuario.getUsuario());
		campos.put(KEY_EMAIL, usuario.getEmail());
		campos.put(KEY_SENHA, usuario.getSenha());
		
		getDatabase().insert(NOME_TABELA, null, campos);
	}
	
	public Usuario montaUsuario(Cursor cursor){
		Usuario usr = new Usuario();
		if (cursor.getCount() == 0){
			return null;
		}
		Integer id = cursor.getInt(cursor.getColumnIndex("_id"));
		String nome = cursor.getString(cursor.getColumnIndex("nome"));
		String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
		String idade = cursor.getString(cursor.getColumnIndex("idade"));
		String email = cursor.getString(cursor.getColumnIndex("email"));
		String senha = cursor.getString(cursor.getColumnIndex("senha"));
		
		usr.setNome(nome);
		usr.setUsuario(usuario);
		usr.setIdade(idade);
		usr.setEmail(email);
		usr.setSenha(senha);
		
		return usr;
	}
	
	public Usuario findByLogin(String usuario, String senha){
		String sql = "SELECT * FROM "+NOME_TABELA+" WHERE usuario = ? AND senha = ?";
		String[] argumentos = new String[]{usuario, senha};
		Cursor cursor = getDatabase().rawQuery(sql, argumentos);
		cursor.moveToFirst();
		return montaUsuario(cursor);
	}
	
	public List<Usuario> findAll() throws Exception{
		List<Usuario> retorno = new ArrayList<Usuario>();
		String sql = "SELECT * FROM "+NOME_TABELA;
		Cursor cursor = getDatabase().rawQuery(sql, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			retorno.add(montaUsuario(cursor));
			cursor.moveToNext();
		}
		return retorno;
	}
	
	public boolean consultaUsuario(String usuario){
		String sql = "SELECT * FROM "+NOME_TABELA+" WHERE usuario = ?";
		String[] argumentos = new String[]{usuario};
		Cursor cursor = getDatabase().rawQuery(sql, argumentos);
		
		return cursor.moveToFirst();
	}
}
