package com.ciesa.tcc.vitality.view;

import com.ciesa.tcc.vitality.R;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends ActionBarActivity {

	// Atributos
	private DrawerLayout vDrawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private String[] textOpcoes;
	private ListView drawerList;
	private android.support.v7.app.ActionBar actionBar;

	// métodos getters e setters
	// ...

	// construtor
	// ...

	// métodos
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		initView();
		onConfigListener();
		onConfigListItem();
		onConfigActionBar();
	}
	
	
	

	public void initView() {
		vDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		actionBar = getSupportActionBar();

		drawerToggle = new ActionBarDrawerToggle(this, // Contexto do activity
														// atual.
				vDrawerLayout, // Objeto DrawerLayout.
				R.drawable.ic_drawer, // Ícone que irá aparecer no canto
										// superior esquerdo da activity.
				R.string.drawer_open, // Descrição "Abrir Drawer".
				R.string.drawer_close // Descrição "Fechar Drawer".
		) {

			/**
			 * Chamado quando o drawer for atribuído em um completo estado de
			 * fechado.
			 */
			@Override
			public void onDrawerClosed(View drawerView) {
				supportInvalidateOptionsMenu();
			}

			/**
			 * Chamado quando o drawer tem atribuido em um completo estado de
			 * aberto.
			 */
			@Override
			public void onDrawerOpened(View drawerView) {
				supportInvalidateOptionsMenu();
			}
		};
		textOpcoes = getResources().getStringArray(R.array.itens_menu_array);
		vDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,	GravityCompat.START);
	}
	
	private void onConfigListener(){
		drawerList.setOnItemClickListener(new DrawerItemClickListener(this, vDrawerLayout, drawerList));
	}
	
	private void onConfigListItem(){
		drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, textOpcoes));
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}




	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}




	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)){
			return true;
		}
			
		return super.onOptionsItemSelected(item);
	}




	private void onConfigActionBar(){
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}
	
	
	
	
	/**
	 * Classe DrawerItemClickListener, que irá implementar o evento de click nos
	 * itens do ListView.
	 * 
	 * @author Jorenilson Lopes
	 *
	 */
	public class DrawerItemClickListener implements OnItemClickListener{
		private Context context;
		private DrawerLayout drawerLayout;
		private ListView drawerList;
		
		public DrawerItemClickListener(Context context, DrawerLayout drawerLayout, ListView drawerList){
			this.drawerLayout = drawerLayout;
			this.drawerList = drawerList;
			this.context = context;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
		}
		
		private void selectItem(long posicao){
			Toast.makeText(context, "Clicou na opção: "+ (posicao+1), Toast.LENGTH_LONG).show(); // Emite um alerta
			drawerLayout.closeDrawer(drawerList); // Fecha o menu
		}
		
	}
}
