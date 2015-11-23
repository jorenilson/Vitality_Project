package com.ciesa.tcc.vitality.view;

import com.ciesa.tcc.vitality.R;
import com.ciesa.tcc.vitality.fragments.FragmentTelaAvaliacaoFisica;
import com.ciesa.tcc.vitality.fragments.FragmentTelaCalcularImc;
import com.ciesa.tcc.vitality.fragments.FragmentTelaPrincipal;
import com.ciesa.tcc.vitality.fragments.FragmentTelaRotinaDeExercicios;

//import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Home extends ActionBarActivity {

	// Atributos
	private DrawerLayout vDrawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private String[] textOpcoes;
	private ListView drawerList;
	private android.support.v7.app.ActionBar actionBar;
	
	// Instância dos fragmentos de tela
	private Fragment fragmentTelaPrincipal = new FragmentTelaPrincipal();


	// métodos
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telahome);
		
		adicionaFragment(fragmentTelaPrincipal);
		
		initView(); // Inicializa os componentes da tela

		
		/*if (savedInstanceState == null) {
			criaFragment(new PrincipalFragment());
		}*/
	}

	public void initView() {
		vDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		actionBar = getSupportActionBar();

		drawerToggle = new ActionBarDrawerToggle(this, // Contexto do activity
				vDrawerLayout, // Objeto DrawerLayout.
				R.drawable.ic_drawer, // Ícone que irá aparecer
				R.string.drawer_open, // Descrição "Abrir Drawer"
				R.string.drawer_close // Descrição "Fechar Drawer"
		) {

			// Chamado quando o drawer list for fechado.
			@Override
			public void onDrawerClosed(View drawerView) {
				supportInvalidateOptionsMenu();
			}

			// Chamado quando o drawer list for aberto.
			@Override
			public void onDrawerOpened(View drawerView) {
				supportInvalidateOptionsMenu();
			}
		};

		textOpcoes = getResources().getStringArray(R.array.itens_menu_array);
		drawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, textOpcoes));
		drawerList.setOnItemClickListener(listener);

		vDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

	}

	// Escuta do ListView(DrawerList)
	OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selecionarItem(position);
			vDrawerLayout.closeDrawer(Gravity.LEFT);
		}
	};

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Alterna o drawertoggle quando clicado no ícone da actionbar
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/*
	 * Este método é responsável por invocar a classe MeuFragmento, que irá
	 * realizar a substituição de fragmento na tela.
	 */
	public void selecionarItem(int posicao) {
		switch (posicao) {
		case 0:
			FragmentTelaPrincipal principalFragment = new FragmentTelaPrincipal();
			substituirFragment(principalFragment);
			setTitle(textOpcoes[posicao]);
			break;
		case 1:
			FragmentTelaCalcularImc calcularImcFragment = new FragmentTelaCalcularImc();
			substituirFragment(calcularImcFragment);
			setTitle(textOpcoes[posicao]);
			break;
		case 2:
			FragmentTelaRotinaDeExercicios rotinaDeExerciciosFragment = new FragmentTelaRotinaDeExercicios();
			substituirFragment(rotinaDeExerciciosFragment);
			setTitle(textOpcoes[posicao]);
			break;
		case 3:
			FragmentTelaAvaliacaoFisica avaliaFisicaFragment = new FragmentTelaAvaliacaoFisica();
			substituirFragment(avaliaFisicaFragment);
			setTitle(textOpcoes[posicao]);
			break;
		default:
			setTitle("Vitality");
			break;
		}
		
		vDrawerLayout.closeDrawer(Gravity.LEFT);
	}

	/*
	 * Este método ira criar os fragmentos de tela a serem exibidos no
	 * content_frame do home.xml
	 */
	
	
	// Adicionar estaticamente um novo fragmento de tela
	public void adicionaFragment(Fragment fragment){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.content_frame, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	// substitui o fragmento de tela recebido por parâmetro
	public void substituirFragment(Fragment fragment){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.content_frame, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
