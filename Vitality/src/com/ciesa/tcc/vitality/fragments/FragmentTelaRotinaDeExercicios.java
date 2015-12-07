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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ciesa.tcc.vitality.R;

public class FragmentTelaRotinaDeExercicios extends Fragment {
	private Spinner spListaDeAtividades;
	private Chronometer chronometer;
	private Button btIniciar, btPausar, btReiniciar;
	private EditText edAltura;
	private TextView tvDtInicial, tvDtFinal;
	private ArrayAdapter<String> menuDeOpcoes;
	private String[] lista;
	boolean isPausado = false;
	long tempoQuandoParado = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layoutrotinadeexercicios,container, false);

		spListaDeAtividades = (Spinner) view.findViewById(R.id.spListaExercicios);
		btIniciar = (Button) view.findViewById(R.id.btCalcular);
		btPausar = (Button) view.findViewById(R.id.bPausarRotinaExercicios);
		btReiniciar = (Button) view.findViewById(R.id.bReiniciarRotinaExercicios);
		chronometer = (Chronometer) view.findViewById(R.id.cronometro);
		edAltura = (EditText)view.findViewById(R.id.edKcal);
		tvDtInicial = (TextView)view.findViewById(R.id.tvDataInicial);
		tvDtFinal = (TextView)view.findViewById(R.id.tvDataFinal);

		setarValoresSpinners();

		btIniciar.setOnClickListener(eventoBtIniciar);
		btPausar.setOnClickListener(eventoBtPausar);
		btReiniciar.setOnClickListener(eventoBtReiniciar);
		spListaDeAtividades.setOnItemSelectedListener(eventoSelecionarExercicio);

		return view;
	}

	public void setarValoresSpinners() {
		lista = getActivity().getResources().getStringArray(R.array.listaDeExercicios);
		menuDeOpcoes = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinnerlayout, lista);

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
	
	//Evento ao alterar o componente Spinner
	OnItemSelectedListener eventoSelecionarExercicio = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			if (position==1){
				chronometer.setText("00:30");
				edAltura.setText("259");
				tvDtInicial.setText("Data Inicial: 07/12/2015");
				tvDtFinal.setText("Data Final: 10/12/2015");
			}else if(position ==2){
				chronometer.setText("00:45");
				edAltura.setText("222");
				tvDtInicial.setText("Data Inicial: 07/01/2016");
				tvDtFinal.setText("Data Final: 10/02/2016");
			}else if (position==3){
				chronometer.setText("00:30");
				edAltura.setText("148");
				tvDtInicial.setText("Data Inicial: 07/12/2015");
				tvDtFinal.setText("Data Final: 30/12/2015");
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
}