package com.ciesa.tcc.vitality.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

	private final static int VERSAO_BANCO = 1;
	
	
	
	// Campos
	public static String KEY_ID = "_id";
	public static String KEY_NOME = "nome";
	public static String KEY_USUARIO = "usuario";
	public static String KEY_IDADE="idade";
	public static String KEY_EMAIL = "email";
	public static String KEY_SENHA = "senha";
	
	// nome do banco de dados da aplicação.
	private final static String NOME_BANCO = "bd_vitality.db";
	
	/*
	 * Variável de String com a cláusula SQL responsável pela criação da tabela
	 * no banco de dados.
	 */
	String SQL_CREATE_TABLE = "create table usuario "+"("+KEY_ID
														 +" integer primary key autoincrement,"
														 +KEY_NOME+" text not null,"
														 +KEY_USUARIO+" text not null,"
														 +KEY_IDADE+" text,"
														 +KEY_EMAIL+" text,"
														 +KEY_SENHA+" text)";
	protected SQLiteDatabase dataBase;
	
	public DataBase(Context context) {
		super(context, NOME_BANCO, null, VERSAO_BANCO);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try{
			db.execSQL(SQL_CREATE_TABLE);
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public SQLiteDatabase getDatabase(){
		if(dataBase == null){
			dataBase = getWritableDatabase();
		}
		return dataBase;
	}

}
