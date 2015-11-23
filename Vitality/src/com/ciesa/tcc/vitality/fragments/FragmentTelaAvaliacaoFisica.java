package com.ciesa.tcc.vitality.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ciesa.tcc.vitality.R;

public class FragmentTelaAvaliacaoFisica extends Fragment {

	private Spinner spAvaliacaoFisica1;
	private Spinner spAvaliacaoFisica2;
	private Spinner spAvaliacaoFisica3;
	private Spinner spAvaliacaoFisica4;

	private RadioGroup rgHernia;
	private RadioGroup rgLombalgia;
	private RadioGroup rgEscoliose;
	private RadioGroup rgColuna;

	private RadioButton rbHerniaSim;
	private RadioButton rbHerniaNao;
	private RadioButton rbLombalgiaSim;
	private RadioButton rbLombalgiaNao;
	private RadioButton rbEscolioseSim;
	private RadioButton rbEscolioseNao;
	private RadioButton rbColunaSim;
	private RadioButton rbColunaNao;

	private Button btConfirmar;

	private String[] opcoes;
	private ArrayAdapter<String> menuDeOpcoes;
	private Context ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.layoutavaliacaofisica,
				container, false);

		spAvaliacaoFisica1 = (Spinner) view.findViewById(R.id.lista1);
		spAvaliacaoFisica2 = (Spinner) view.findViewById(R.id.lista2);
		spAvaliacaoFisica3 = (Spinner) view.findViewById(R.id.lista3);
		spAvaliacaoFisica4 = (Spinner) view.findViewById(R.id.lista4);

		rgHernia = (RadioGroup) view.findViewById(R.id.rgHernia);
		rgLombalgia = (RadioGroup) view.findViewById(R.id.rgLombalgia);
		rgEscoliose = (RadioGroup) view.findViewById(R.id.rgEscoliose);
		rgColuna = (RadioGroup) view.findViewById(R.id.rgColuna);

		btConfirmar = (Button) view.findViewById(R.id.btAvaliacaoConfirmar);

		// Preencher lista de opções
		setarValoresSpinners();

		// Tratar o click do botão "Confirmar".
		btConfirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (validarCampos()) {
					Toast.makeText(
							getActivity(),
							"Sua Avaliação Física foi bem sucedida. “Sendo assim é possível realizar a Rotina de Exercício Físico”",
							Toast.LENGTH_LONG).show();
					carregarRotinaDeExercicio(view);
				}
			}
		});

		return view;
	}

	// Preencher o formulário Spinner.

	public void setarValoresSpinners() {
		opcoes = getActivity().getResources().getStringArray(
				R.array.listaDeIntensidades);
		menuDeOpcoes = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(), R.layout.spinnerlayout, opcoes);

		spAvaliacaoFisica1.setAdapter(menuDeOpcoes);
		spAvaliacaoFisica2.setAdapter(menuDeOpcoes);
		spAvaliacaoFisica3.setAdapter(menuDeOpcoes);
		spAvaliacaoFisica4.setAdapter(menuDeOpcoes);

	}

	/*
	 * Valida se todos os campos do formulário foram preenchidos.
	 */
	public boolean validarCampos() {
		// Verificar se todos os RadioGroups foram informados.
		if (rgHernia.getCheckedRadioButtonId() == -1) {
			exibeMsg("PERGUNTA 1: A seleção das opções é obrigatória para sua Avaliação Física.");
			return false;
		} else if (rgLombalgia.getCheckedRadioButtonId() == -1) {
			exibeMsg("PERGUNTA 2: A seleção das opções é obrigatória para sua Avaliação Física.");
			return false;
		} else if (rgEscoliose.getCheckedRadioButtonId() == -1) {
			exibeMsg("PERGUNTA 3: A seleção das opções é obrigatória para sua Avaliação Física.");
			return false;
		} else if (rgColuna.getCheckedRadioButtonId() == -1) {
			exibeMsg("PERGUNTA 4: A seleção das opções é obrigatória para sua Avaliação Física.");
			return false;
		}

		return true;
	}

	public void exibeMsg(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	public void carregarRotinaDeExercicio(View v) {
		FragmentTelaRotinaDeExercicios fragmentTelaRotinaDeExercicios = new FragmentTelaRotinaDeExercicios();
		android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame,
				fragmentTelaRotinaDeExercicios);
		fragmentTransaction.commit();
	}
}
