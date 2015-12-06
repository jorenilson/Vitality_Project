package com.ciesa.tcc.vitality.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
	private Button btCadastrar;
	private Context context;
	private UsuarioController usuarioController;
	private AlertDialog.Builder alerta;
	private View view;
	private Usuario usuario;
	private String varNome, varUsuario, varEmail, varIdade, varSenha;

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
		btCadastrar = (Button) findViewById(R.id.btCadastrar);
		
		varNome =edNome.getText().toString();
		varUsuario=edUsuario.getText().toString();
		varEmail = edEmail.getText().toString();
		varIdade=edIdade.getText().toString();
		varSenha =edSenha.getText().toString();
		

		btCadastrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inserirCampos(v);
			}
		});
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

	public void inserirCampos(View v) {

		usuario = new Usuario();
		boolean usuarioExiste = false;

		usuario.setNome(edNome.getText().toString());
		usuario.setUsuario(edUsuario.getText().toString());
		usuario.setIdade(edIdade.getText().toString());
		usuario.setEmail(edEmail.getText().toString());
		usuario.setSenha(edSenha.getText().toString());
		

		if (edNome.getText().toString().isEmpty()){
			edNome.requestFocus();
			edNome.setError("Nome inválido.");
		}else if(edUsuario.getText().toString().isEmpty()){
			edUsuario.requestFocus();
			edUsuario.setError("Usuário inválido.");
		}else if(edEmail.getText().toString().isEmpty()){
			edEmail.requestFocus();
			edEmail.setError("Email inválido.");
		}else if(edIdade.getText().toString().isEmpty()){
			edIdade.requestFocus();
			edIdade.setError("Idade inválida.");
		}else if(edSenha.getText().toString().isEmpty()){
			edSenha.requestFocus();
			edSenha.setError("Senha inválida.");
		}else{
			if (usuarioExiste = usuarioController.consultaUsuario(usuario.getUsuario())){
				edUsuario.requestFocus();
				edUsuario.setError("Usuário duplicado.");
			}else{
				try {
					usuarioController.inserir(usuario);
					Toast.makeText(v.getContext(), "Parabéns! Cadastro realizado com sucesso.", Toast.LENGTH_LONG).show();
					exibirTelaLogin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * if (varNome.isEmpty() || varUsuario.isEmpty() || varIdade.isEmpty()
		 * || varEmail.isEmpty() || varSenha.isEmpty()){ exibeDialogo("Opa!",
		 * "O preenchimento dos campos é obrigatorio; Dado(s) inválido(s).");
		 * }else if (usuarioExiste) { exibeDialogo( "Quase lá...",
		 * "O cadastro não pôde ser realizado. Usuário informado já consta no banco de dados."
		 * ); } else { usuarioController.inserir(usuario);
		 * exibirDialogoConfirmacaoCadastro(view); }
		 */

	}

	public void exibirTelaLogin() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
