package com.ciesa.tcc.vitality.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;

import com.ciesa.tcc.vitality.R;

public class FragmentTelaRotinaDeExercicios extends Fragment {
	private Spinner spListaDeAtividades;
	private Chronometer chronometer;
	private Button btIniciar, btPausar, btReiniciar;
	private ArrayAdapter<String> menuDeOpcoes;
	private String[] lista;
	boolean isPausado = false;
	long tempoQuandoParado = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.layoutrotinadeexercicios,
				container, false);

		spListaDeAtividades = (Spinner) view
				.findViewById(R.id.spListaExercicios);
		btIniciar = (Button) view.findViewById(R.id.btCalcular);
		btPausar = (Button) view.findViewById(R.id.bPausarRotinaExercicios);
		btReiniciar = (Button) view
				.findViewById(R.id.bReiniciarRotinaExercicios);
		chronometer = (Chronometer) view.findViewById(R.id.cronometro);

		setarValoresSpinners();

		btIniciar.setOnClickListener(eventoBtIniciar);
		btPausar.setOnClickListener(eventoBtPausar);
		btReiniciar.setOnClickListener(eventoBtReiniciar);

		return view;
	}

	public void setarValoresSpinners() {
		lista = getActivity().getResources().getStringArray(
				R.array.listaDeExercicios);
		menuDeOpcoes = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(), R.layout.spinnerlayout, lista);

		spListaDeAtividades.setAdapter(menuDeOpcoes);
	}

	// Botão iniciar
	OnClickListener eventoBtIniciar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isPausado) {
				chronometer.setBase(SystemClock.elapsedRealtime()
						+ tempoQuandoParado);
				chronometer.start();
				tempoQuandoParado = 0;
				isPausado = false;
			} else {
				chronometer.setBase(SystemClock.elapsedRealtime());
				chronometer.start();
				tempoQuandoParado = 0;
			}
		}
	};

	// Botão Pausar
	OnClickListener eventoBtPausar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isPausado == false){
				tempoQuandoParado = chronometer.getBase() - SystemClock.elapsedRealtime();
			}
			chronometer.stop();
			isPausado = true;
		}
	};
	
	// Botão Reiniciar
	OnClickListener eventoBtReiniciar = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			chronometer.stop();
			chronometer.setText("00:00");
			tempoQuandoParado = 0;
		}
	};
}