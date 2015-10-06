package com.ciesa.tcc.vitality.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ciesa.tcc.vitality.R;
import com.ciesa.tcc.vitality.controller.UsuarioController;
import com.ciesa.tcc.vitality.model.Usuario;

public class Cadastro extends Activity {

	// atributos da classe
	private EditText edNome;
	private EditText edUsuario;
	private EditText edEmail;
	private EditText edSenha;
	private EditText edIdade;
	private Context context;
	private UsuarioController usuarioController;
	private AlertDialog.Builder alerta;

	// getters e setters
	// ...

	// Métodos
	public void onCreate(Bundle savedInstanceState) {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novo);

		context = this;
		usuarioController = usuarioController.getInstance(context);

		edNome = (EditText) findViewById(R.id.edtNome);
		edUsuario = (EditText) findViewById(R.id.edtUsuario);
		edEmail = (EditText) findViewById(R.id.edtEmail);
		edIdade = (EditText) findViewById(R.id.edtIdade);
		edSenha = (EditText) findViewById(R.id.edtSenha);

		try {

		} catch (Exception e) {
			exibeDialogo("Erro inicializando o banco de dados");
			e.printStackTrace();
		}
	}

	/*
	 * Exibe para na tela uma caixa de diálogo exibindo uma mensagem
	 * personalizada de acordo com a mensagem passada por parâmetro para o
	 * método exibeDialogo.
	 */
	public void exibeDialogo(String mensagem) {
		alerta = new AlertDialog.Builder(context);
		alerta.setPositiveButton("OK", null);
		alerta.setMessage(mensagem);
		alerta.create().show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Método chamado ao clicar no botão salvar. Criar um novo objeto
	 * BancoDeDados instanciando de acordo como o contexto atual da aplicação.
	 * Em seguida abrimos o banco de dados e verifica se o objeto usuário é
	 * nulo. Pois somente assim o aplicativo poderá saber se o evento se trata
	 * de uma edição ou uma inclusão.
	 */
	public void inserirCampos(View view) throws Exception {
		Usuario usuario = new Usuario();
		boolean usuarioExiste;

		usuario.setNome(edNome.getText().toString());
		usuario.setUsuario(edUsuario.getText().toString());
		usuario.setIdade(edUsuario.getText().toString());
		usuario.setEmail(edEmail.getText().toString());
		usuario.setSenha(edSenha.getText().toString());
		usuarioExiste = usuarioController.consultaUsuario(usuario.getUsuario());

		if (usuarioExiste) {
			exibeDialogo("O cadastro não pôde ser realizado. Usuário já consta no banco de dados.");
		} else {
			usuarioController.inserir(usuario);
			exibeDialogo("Seu cadastro foi realizado com sucesso.");
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			
		}
	}

}
