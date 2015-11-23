package com.ciesa.tcc.vitality.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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

	// construtor
	// ...

	// Métodos
	public void onCreate(Bundle savedInstanceState) {
		// habilito o botão boltar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layoutcadastro);

		usuarioController = usuarioController.getInstance(this);

		edNome = (EditText) findViewById(R.id.edtNome);
		edUsuario = (EditText) findViewById(R.id.edtUsuario);
		edEmail = (EditText) findViewById(R.id.edtEmail);
		edIdade = (EditText) findViewById(R.id.edtIdade);
		edSenha = (EditText) findViewById(R.id.edtSenha);
	}

	/*
	 * Exibe para na tela uma caixa de diálogo exibindo uma mensagem
	 * personalizada de acordo com a mensagem passada por parâmetro para o
	 * método exibeDialogo.
	 */
	public void exibeDialogo(String titulo, String mensagem) {
		alerta = new AlertDialog.Builder(context);
		alerta.setTitle(titulo);
		alerta.setMessage(mensagem);
		alerta.setPositiveButton("OK", null);
		alerta.create().show();
	}

	/*
	 * Exibe na tela uma caixa de diálogo exibindo uma confirmação de que o
	 * cadastro foi realizado com sucesso.
	 */
	public void exibirDialogoConfirmacaoCadastro(View view) {
		alerta = new AlertDialog.Builder(context);
		alerta.setTitle("Seja bem vindo!");
		alerta.setMessage("Seu cadastro foi realizado com sucesso.");
		alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				exibirTelaLogin();
			}
		});
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
		try {

			Usuario usuario = new Usuario();
			boolean usuarioExiste;
			String varNome = edNome.getText().toString();
			String varUsuario = edUsuario.getText().toString();
			String varIdade = edIdade.getText().toString();
			String varEmail = edEmail.getText().toString();
			String varSenha = edSenha.getText().toString();

			usuario.setNome(varNome);
			usuario.setUsuario(varUsuario);
			usuario.setIdade(varIdade);
			usuario.setEmail(varEmail);
			usuario.setSenha(varSenha);
			usuarioExiste = usuarioController.consultaUsuario(usuario.getUsuario());

			if (varNome.isEmpty() || varUsuario.isEmpty() || varIdade.isEmpty() || varEmail.isEmpty() || varSenha.isEmpty()){
				exibeDialogo("Opa!", "O preenchimento dos campos é obrigatorio; Dado(s) inválido(s).");
			}else if (usuarioExiste) {
				exibeDialogo(
						"Quase lá...",
						"O cadastro não pôde ser realizado. Usuário informado já consta no banco de dados.");
			} else {
				usuarioController.inserir(usuario);
				exibirDialogoConfirmacaoCadastro(view);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void exibirTelaLogin() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
