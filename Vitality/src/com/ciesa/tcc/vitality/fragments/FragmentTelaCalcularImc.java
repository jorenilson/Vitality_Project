package com.ciesa.tcc.vitality.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ciesa.tcc.vitality.R;

public class FragmentTelaCalcularImc extends Fragment implements Calculadora {
	private EditText edtAltura;
	private EditText edtPeso;
	private RadioButton rbMasculino;
	private RadioButton rbFeminino;
	private RadioGroup rgSexo;
	private Button btCalcular;
	private AlertDialog alertDialog;
	private static final double PESOIDEALHOMEM = 22;
	private static final double PESOIDEALMULHER = 21;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layoutcalcularimc, container,
				false);

		edtAltura = (EditText) view.findViewById(R.id.edAltura);
		edtPeso = (EditText) view.findViewById(R.id.edPeso);
		rgSexo = (RadioGroup) view.findViewById(R.id.rgSexo);
		rbMasculino = (RadioButton) view.findViewById(R.id.rbMasculino);
		rbFeminino = (RadioButton) view.findViewById(R.id.rbFeminino);
		rgSexo = (RadioGroup) view.findViewById(R.id.rgSexo);
		btCalcular = (Button) view.findViewById(R.id.btCalcular);

		btCalcular.setOnClickListener(executarCalculo);

		return view;
	}

	OnClickListener executarCalculo = new OnClickListener() {

		@Override
		public void onClick(View v) {
			double varAltura, varPeso;
			int varSexo;

			if (validarCampos() == true) {
				varPeso = Double.parseDouble(edtPeso.getText().toString());
				varAltura = Double.parseDouble(edtPeso.getText().toString());
				if (rbMasculino.isChecked()) {
					varSexo = 0; // homem
				} else {
					varSexo = 1; // mulher
				}
				calcularImc(varPeso, varAltura, varSexo);
			}
		}
	};

	public boolean validarCampos() {
		String altura, peso;

		altura = edtAltura.getText().toString();
		peso = edtPeso.getText().toString();

		if (altura.isEmpty() || peso.isEmpty()
				|| rgSexo.getCheckedRadioButtonId() == -1) {
			exibeMsg("O Preenchimento dos Campos para obter o"
					+ " Índice de Massa Corporal e Peso Ideal é obrigatório; Dado (s) inválido (s).");
			return false;
		} else {

		}

		return true;
	}

	public void exibeMsg(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	// Subscreve o método obterImc da interface Calcular
	@Override
	public void calcularImc(double peso, double altura, int sexo) {
		double resultadoImc;
		resultadoImc = (peso / altura) * altura;
		//resultadoImc = 30.0;
		descricaoImc(resultadoImc); // Ontem a descriçao do IMC
	}

	// De acordo com o IMC calculado, exibe uma descrição chamando o Dialog
	// Message.
	@SuppressWarnings("deprecation")
	@Override
	public void descricaoImc(double imc) {
		String msg = "";
		alertDialog = new AlertDialog.Builder(getActivity()).create();

		if (imc < 18.5) {
			msg = "Seu índice de massa corporal é: " + imc + ".\n";
			msg = msg + "-> Baixo Peso\n\n";
			msg = msg
					+ "Isto significa que você está com o peso em excesso. Seu Peso Ideal é:"
					+ obterPesoIdeal();
			msg += "\nDeseja continuar?";
		} else if (imc >= 18.5 && imc <= 24.99) {
			msg = "Seu índice de massa corporal é: " + imc + ".\n";
			msg = msg + "-> Normal\n\n";
			// msg =
			// msg+"Isto significa que você está com o peso em excesso. Seu Peso Ideal é:"+obterPesoIdeal();
		} else if (imc >= 25 && imc <= 29.99) {
			msg = "Seu índice de massa corporal é: " + imc + ".\n";
			msg = msg + "-> sobrepeso\n\n";
			msg = msg
					+ "Isto significa que você está com o peso em excesso. Seu Peso Ideal é:"
					+ obterPesoIdeal();
			msg += "\nDeseja continuar?";
		} else if (imc >= 30 && imc <= 34.9) {
			msg = "Seu índice de massa corporal é: " + imc + ".\n";
			msg = msg + "-> Obesidade grau 1\n\n";
			msg = msg
					+ "Isto significa que você está com o peso em excesso. Seu Peso Ideal é:"
					+ obterPesoIdeal();
			msg += "\nDeseja continuar?";
		} else if (imc >= 35 && imc <= 39.99) {
			msg = "Seu índice de massa corporal é: " + imc + ".\n";
			msg = msg + "-> Obesidade grau 2\n\n";
			msg = msg
					+ "Isto significa que você está com o peso em excesso. Seu Peso Ideal é:"
					+ obterPesoIdeal();
			msg += "\nDeseja continuar?";
		} else if (imc >= 40) {
			msg = "Seu índice de massa corporal é: " + imc + ".\n";
			msg = msg + "-> Obesidade grau 3\n\n";
			msg = msg
					+ "Isto significa que você está com o peso em excesso. Seu Peso Ideal é:"
					+ obterPesoIdeal();
			msg += "\nDeseja continuar?";
		}

		alertDialog.setTitle("Alerta");
		alertDialog.setMessage(msg);
		alertDialog.setCancelable(false);
		alertDialog.setButton("Sim", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
				irTelaAvaliacao();
			}
		});
		alertDialog.setButton2("Não", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				irTelaHome();
			}
		});
		alertDialog.show();
	}

	@Override
	public double obterPesoIdeal() {
		double pesoIdeal, altura, peso;
		altura = Double.parseDouble(edtAltura.getText().toString());
		peso = Double.parseDouble(edtPeso.getText().toString());

		if (rbMasculino.isChecked()) {
			pesoIdeal = altura * altura * PESOIDEALMULHER;
			return pesoIdeal;
		} else {
			pesoIdeal = altura * altura * PESOIDEALHOMEM;
			return pesoIdeal;
		}
	}
	
	public void irTelaHome(){
		FragmentTelaPrincipal fragmentTelaPrincipal = new FragmentTelaPrincipal();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, fragmentTelaPrincipal, "Vitality");
		fragmentTransaction.commit();
		
	}
	
	public void irTelaAvaliacao(){
		FragmentTelaAvaliacaoFisica fragmentTelaAvaliacaoFisica = new FragmentTelaAvaliacaoFisica();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, fragmentTelaAvaliacaoFisica,"Avaliação Física");
		fragmentTransaction.commit();
	}

};
