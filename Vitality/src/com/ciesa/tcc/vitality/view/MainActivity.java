package com.ciesa.tcc.vitality.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.ciesa.tcc.vitality.R;
import com.ciesa.tcc.vitality.controller.UsuarioController;
import com.ciesa.tcc.vitality.model.Usuario;

public class MainActivity extends ActionBarActivity {

	// Declara��o dos atributos da classe MainActivity.java
	private EditText edtUsuario;
	private EditText edtSenha;
	private Context context;
	private UsuarioController usuarioController;
	private AlertDialog.Builder alerta;

	// m�todos getters e setters.
	// ...

	// Declara��o do construtor.
	// ...

	// M�todos.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		context = this;
		usuarioController = usuarioController.getInstance(context);

		edtUsuario = (EditText) findViewById(R.id.cpUsuario);
		edtSenha = (EditText) findViewById(R.id.cpSenha);

		try {
			testaInicializacao();
		} catch (Exception e) {
			exibeDialogo("Erro inicializando o banco de dados");
			e.printStackTrace();
		}

	}

	/*
	 * O m�todo testaInicializacao ir� verificar durante a inicializa��o da
	 * aplica��o se j� existe usu�rio devidamente cadastrado no banco de dados.
	 * Caso n�o exista, o sistema ir� criar automaticamente um novo usu�rio.
	 */
	public void testaInicializacao() throws Exception {
		if (usuarioController.findAll().isEmpty()
				|| usuarioController.findAll() == null) {
			Usuario usuario = new Usuario();
			usuario.setNome("Administrador");
			usuario.setIdade("30");
			usuario.setUsuario("admin");
			usuario.setEmail("jorenilsonlopes@gmail.com");
			usuario.setSenha("senha");
			usuarioController.inserir(usuario);
		}
	}

	/*
	 * Exibe para na tela uma caixa de di�logo exibindo uma mensagem
	 * personalizada de acordo com a mensagem passada por par�metro para o
	 * m�todo exibeDialogo.
	 */
	public void exibeDialogo(String mensagem) {
		alerta = new AlertDialog.Builder(context);
		alerta.setPositiveButton("OK", null);
		alerta.setMessage(mensagem);
		alerta.create().show();
	}

	/*
	 * Este m�todo ir� primeiramente verifica se os campos usu�rio e senha foram
	 * devidamente preenchidos de cordo com o item FP04. Em seguida o sistema
	 * ir� realizar a valida��o dos dados informados comparando com o dado
	 * retornado do banco de dados de acordo com o item A.1.1 do documento de
	 * caso de uso.
	 */
	public void validar(View view) {
		String usuario = edtUsuario.getText().toString();
		String senha = edtSenha.getText().toString();
		try {
			if (usuario.isEmpty() || senha.isEmpty()) {
				exibeDialogo("Preenchimento dos campos � obrigat�rio. Dado(s) inv�lido(s).");
			} else {
				boolean isValid = usuarioController.validaLogin(usuario, senha);
				if (isValid) {
					// Login validado com sucesso, carrega a tela home da aplica��o.
					telaHome(view);
					this.finish();
				} else {
					exibeDialogo("Verifique usu�rio e senha.");
				}
			}
		} catch (Exception e) {
			exibeDialogo("Erro validando usu�rio e senha.");
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/*
	 * Este m�todo ir� carregar a tela de cadastro de novos usu�rios.
	 */
	public void cadastrarNovo(View view) {
		Intent intent = new Intent(this, Cadastro.class);
		startActivity(intent);
	}
	
	/*
	 * Este m�etodo ir� carregar a tela Home, caso o login seja validade com sucesso.
	 */
	public void telaHome(View view){
		Intent intent = new Intent(this, Home.class);
		startActivity(intent);
	}
}
