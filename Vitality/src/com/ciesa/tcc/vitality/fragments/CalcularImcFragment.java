package com.ciesa.tcc.vitality.fragments;

import com.ciesa.tcc.vitality.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalcularImcFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragmento_calcular_imc, container, false);
		return view;
	}

}
;