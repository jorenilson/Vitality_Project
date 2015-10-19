package com.ciesa.tcc.vitality.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ciesa.tcc.vitality.R;

public class RotinaDeExerciciosFragment extends Fragment {

	private Spinner spExercicios;
	String[] listaOpcoes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragmento_rotina_exercicios,
				container, false);
		
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Captura as Views
		spExercicios = (Spinner) view.findViewById(R.id.spListaExercicios);
		listaOpcoes = getResources().getStringArray(R.array.lista_exercicios);

		//spExercicios.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaOpcoes));
		
		// Recebe parâmetros
		Bundle args = getArguments();
		
	}

}
