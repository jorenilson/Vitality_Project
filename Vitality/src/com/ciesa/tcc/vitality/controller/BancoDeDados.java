package com.ciesa.tcc.vitality.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/***
 * Classe que terá todas as ações que envolvem diretamente o banco de dados
 * SQLite. Desta forma o projeto fica mais organizado e separamos a parte do
 * modelo de dados das visões das aplicações(telas) e da lógica, conforme o
 * design partner Model-View-Controller instrui.
 * 
 * @author Jorenilson Lopes
 * @email jorenilsonlopes@gmail.com
 * 
 */
public class BancoDeDados {

	/*
	 * definição dos objetos String que contém o nome de todos os campos da
	 * tabela que serão tratados pelo aplicativo.
	 */
	static String KEY_ID = "_id";
	public static String KEY_NOME = "nome";
	public static String KEY_USUARIO = "usuario";
	static String KEY_EMAIL = "email";
	static String KEY_SENHA = "senha";

	// nome do banco de dados da aplicação.
	String NOME_BANCO = "db_vitality";
	// nome da tabela.
	String NOME_TABELA = "vitality";
	// versão do banco de dados.
	int VERSAO_BANCO = 1;
	/*
	 * Variável de String com a cláusula SQL responsável pela criação da tabela
	 * no banco de dados.
	 */
	String SQL_CREATE_TABLE = "create table usuarios " + "(" + KEY_ID
			+ " integer primary key autoincrement," + KEY_NOME
			+ " text not null," + KEY_USUARIO + " text not null," + KEY_EMAIL
			+ " text," + KEY_SENHA + " text);";

	/*
	 * criamos a variável Context, que será necessária na classe MeuOpenHelper,
	 * que será codificada posteriormente. Também foi declarada uma variável
	 * para MeuOpenHelper e uma variável de SQLiteDataBase. É essa classe que
	 * permite chamar os métodos CRUD diretamente no banco de dados.
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
	 * auxiliar na criação e no gerenciamento das versões do banco de dados.
	 * Esta Herança implementa de forma automática três outros métodos:
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
		 * O método onCreate é chamado quando o banco de dados é criado pela
		 * primeira vez. É nele que teremos que criar nossas tabelas, com o
		 * código apresentado na linha onde é chamado
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
		 * O método onUpgrade é chamado quando temos uma nova versão do banco de
		 * dados ou da aplicação. Se mudarmos a variável com a versão do banco
		 * de dados, esse método será chamado.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS usuarios");
			onCreate(db);
		}
	}

	/*
	 * Através do método abrir com retorno do tipo BancoDeDados, será chamado o
	 * método getWritableDatabase de MeuOpenHelper. O mesmo é filho de
	 * SQLiteOpenHelper.
	 */
	public BancoDeDados abrir() throws SQLException {
		db = openHelper.getWritableDatabase();
		return this;
	}

	/*
	 * o método fechar irá chamar o método close() da instância de
	 * MeuOpenHelper. A sequência utilizada na maioria dos programas resume-se a
	 * cada operação fazer: abrir uma conexão com o banco; pegar a instância de
	 * SQLiteDatabase; executar o comando; Fechá-la.
	 */
	public void fechar() {
		openHelper.close();
	}

	/*
	 * No método inserirUsuario, recebe por parâmetro o valor de todos os campos
	 * da tabela no banco de dados. Em seguida, encontra-se uma instância de
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
	 * O método apagaUsuario, recebe o id da linha a ser removida. O que estamos
	 * fazendo é chamando o método delete de SQLiteDataBase.
	 */
	public boolean apagaUsuario(long id) {
		return db.delete(NOME_TABELA, KEY_ID + "=" + id, null) > 0;
	}

	/*
	 * Temos o método retornaTodosUsuarios responsável pela busca no banco de
	 * dados, sem filtros.
	 */
	public Cursor retornaTodosUsuarios() {
		return db.query(NOME_TABELA, new String[] { KEY_ID, KEY_NOME,
				KEY_USUARIO, KEY_EMAIL, KEY_SENHA }, null, null, null, null,
				null);
	}

	/*
	 * O método atualizaUsuario se parece bastante com o método de inserção. Ele
	 * ter apenas um parâmetro a mais, que é o id da linha que será alterada.
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
