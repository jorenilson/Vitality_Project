package com.ciesa.tcc.vitality.fragments;

import com.ciesa.tcc.vitality.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

public class AvaliacaoFisicaFragment extends Fragment {
	private String[] listaOpcoes;
	private Spinner sp01;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.avaliacaofisica, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		sp01 = (Spinner)view.findViewById(R.id.lista1);
		listaOpcoes = getResources().getStringArray(R.array.lista_intensidade);
		
	}
	
	

}
