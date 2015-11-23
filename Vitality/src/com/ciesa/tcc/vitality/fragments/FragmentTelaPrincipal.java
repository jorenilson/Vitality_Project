package com.ciesa.tcc.vitality.fragments;

import com.ciesa.tcc.vitality.R;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTelaPrincipal extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layoutprincipal, container, false);
		return view;
	}

}
