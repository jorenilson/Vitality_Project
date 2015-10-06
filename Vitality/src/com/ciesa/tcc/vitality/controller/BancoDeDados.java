package com.ciesa.tcc.vitality.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/***
 * Classe que ter� todas as a��es que envolvem diretamente o banco de dados
 * SQLite. Desta forma o projeto fica mais organizado e separamos a parte do
 * modelo de dados das vis�es das aplica��es(telas) e da l�gica, conforme o
 * design partner Model-View-Controller instrui.
 * 
 * @author Jorenilson Lopes
 * @email jorenilsonlopes@gmail.com
 * 
 */
public class BancoDeDados {

	/*
	 * defini��o dos objetos String que cont�m o nome de todos os campos da
	 * tabela que ser�o tratados pelo aplicativo.
	 */
	static String KEY_ID = "_id";
	public static String KEY_NOME = "nome";
	public static String KEY_USUARIO = "usuario";
	static String KEY_EMAIL = "email";
	static String KEY_SENHA = "senha";

	// nome do banco de dados da aplica��o.
	String NOME_BANCO = "db_vitality";
	// nome da tabela.
	String NOME_TABELA = "vitality";
	// vers�o do banco de dados.
	int VERSAO_BANCO = 1;
	/*
	 * Vari�vel de String com a cl�usula SQL respons�vel pela cria��o da tabela
	 * no banco de dados.
	 */
	String SQL_CREATE_TABLE = "create table usuarios " + "(" + KEY_ID
			+ " integer primary key autoincrement," + KEY_NOME
			+ " text not null," + KEY_USUARIO + " text not null," + KEY_EMAIL
			+ " text," + KEY_SENHA + " text);";

	/*
	 * criamos a vari�vel Context, que ser� necess�ria na classe MeuOpenHelper,
	 * que ser� codificada posteriormente. Tamb�m foi declarada uma vari�vel
	 * para MeuOpenHelper e uma vari�vel de SQLiteDataBase. � essa classe que
	 * permite chamar os m�todos CRUD diretamente no banco de dados.
	 */
	final Context context;
	MeuOpenHelper openHelper;
	SQLiteDatabase db;

	public BancoDeDados(Context ctx) {
		this.context = ctx;
		openHelper = new MeuOpenHelper(context);
	}

	/*
	 * A classe MeuOpenHelper, estenda da classe SQLiteOpenHelper. Essa classe
	 * auxiliar na cria��o e no gerenciamento das vers�es do banco de dados.
	 * Esta Heran�a implementa de forma autom�tica tr�s outros m�todos:
	 * onCreate, onUpdate e onOpen.
	 */
	private class MeuOpenHelper extends SQLiteOpenHelper {

		MeuOpenHelper(Context context) {
			super(context, NOME_BANCO, null, VERSAO_BANCO);
		}

		public MeuOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		/*
		 * O m�todo onCreate � chamado quando o banco de dados � criado pela
		 * primeira vez. � nele que teremos que criar nossas tabelas, com o
		 * c�digo apresentado na linha onde � chamado
		 * db.ExecSQL(SQL_CREATE_TABLE).
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {
				db.execSQL(SQL_CREATE_TABLE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/*
		 * O m�todo onUpgrade � chamado quando temos uma nova vers�o do banco de
		 * dados ou da aplica��o. Se mudarmos a vari�vel com a vers�o do banco
		 * de dados, esse m�todo ser� chamado.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS usuarios");
			onCreate(db);
		}
	}

	/*
	 * Atrav�s do m�todo abrir com retorno do tipo BancoDeDados, ser� chamado o
	 * m�todo getWritableDatabase de MeuOpenHelper. O mesmo � filho de
	 * SQLiteOpenHelper.
	 */
	public BancoDeDados abrir() throws SQLException {
		db = openHelper.getWritableDatabase();
		return this;
	}

	/*
	 * o m�todo fechar ir� chamar o m�todo close() da inst�ncia de
	 * MeuOpenHelper. A sequ�ncia utilizada na maioria dos programas resume-se a
	 * cada opera��o fazer: abrir uma conex�o com o banco; pegar a inst�ncia de
	 * SQLiteDatabase; executar o comando; Fech�-la.
	 */
	public void fechar() {
		openHelper.close();
	}

	/*
	 * No m�todo inserirUsuario, recebe por par�metro o valor de todos os campos
	 * da tabela no banco de dados. Em seguida, encontra-se uma inst�ncia de
	 * ContentValues, chamada campos. Esta classe armazena pares de dados com
	 * uma chave e seu valor
	 */
	public long insereUsuario(String nome, String usuario, String email,
			String senha) {
		ContentValues campos = new ContentValues();
		campos.put(KEY_NOME, nome);
		campos.put(KEY_USUARIO, usuario);
		campos.put(KEY_EMAIL, email);
		campos.put(KEY_SENHA, senha);
		return db.insert(NOME_TABELA, null, campos);
	}

	/*
	 * O m�todo apagaUsuario, recebe o id da linha a ser removida. O que estamos
	 * fazendo � chamando o m�todo delete de SQLiteDataBase.
	 */
	public boolean apagaUsuario(long id) {
		return db.delete(NOME_TABELA, KEY_ID + "=" + id, null) > 0;
	}

	/*
	 * Temos o m�todo retornaTodosUsuarios respons�vel pela busca no banco de
	 * dados, sem filtros.
	 */
	public Cursor retornaTodosUsuarios() {
		return db.query(NOME_TABELA, new String[] { KEY_ID, KEY_NOME,
				KEY_USUARIO, KEY_EMAIL, KEY_SENHA }, null, null, null, null,
				null);
	}

	/*
	 * O m�todo atualizaUsuario se parece bastante com o m�todo de inser��o. Ele
	 * ter apenas um par�metro a mais, que � o id da linha que ser� alterada.
	 */
	public boolean atualizaUsuario(long id, String nome, String usuario,
			String email, String senha) {
		ContentValues args = new ContentValues();
		args.put(KEY_NOME, nome);
		args.put(KEY_USUARIO, usuario);
		args.put(KEY_EMAIL, email);
		args.put(KEY_SENHA, senha);
		return db.update(NOME_TABELA, args, KEY_ID + "=" + id, null) > 0;
	}

	public boolean autenticarUsuario(String usuario, String senha) {
		Cursor cursor;
		String argumentos[] = new String[] { usuario, senha };
		String where = "KEY_USUARIO = ?";

		ContentValues campos = new ContentValues();
		campos.put(KEY_USUARIO, usuario);
		campos.put(KEY_SENHA, senha);

		cursor = db.query(NOME_TABELA, new String[] {KEY_USUARIO},
				where, argumentos, null, null, null);
		
		if (cursor!=null){
			return true;
		}
		return false;
	}
}
