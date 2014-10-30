package com.example.splitv6.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.splitv6.R;
import com.example.splitv6.adapters.DrawerAdapter;
import com.example.splitv6.contracts.OnFragmentSelectedListener;
import com.example.splitv6.controller.SplitApplication;
import com.example.splitv6.controller.SplitController;
import com.example.splitv6.fragments.FragmentAjuda;
import com.example.splitv6.fragments.FragmentHistorico;
import com.example.splitv6.fragments.FragmentMain;
import com.example.splitv6.fragments.FragmentSobre;
import com.example.splitv6.model.DrawerItem;

public class MainActivity extends Activity implements OnFragmentSelectedListener
{
	public static final String TAG = "MainActivity";
	private static final boolean GERAR_EVENTO_AUTOMATICO = true;
	private SplitApplication app;

	private Fragment mFragment;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private DrawerAdapter adapter;

	private List<DrawerItem> mDrawerItems;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SplitController.controller = new SplitController();

		app = (SplitApplication) getApplicationContext();

		// inicializando:
		mDrawerItems = new ArrayList<DrawerItem>();
		mTitle = getTitle();
		mDrawerTitle = getResources().getString(R.string.drawer_title);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// setando a largura do drawer:
		int width = (int) (app.getConfig().getDisplayWidth() * (app.getConfig().getLarguraPercentualDrawer() / 100f));
		DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
		params.width = width;
		mDrawerList.setLayoutParams(params);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		setDrawerItems(); // adiciona os items do menu ao drawer

		// inicializando drawer adapter:
		adapter = new DrawerAdapter(this, R.layout.drawer_list_item, mDrawerItems);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
				R.drawable.ic_drawer_2,
				R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view)
			{
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // cria a chamada para onPrepareOptionMenu()
			}

			public void onDrawerOpened(View drawerView)
			{
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // cria a chamada para onPrepareOptionMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) { // se inicializando pela primeira vez, seleciona o item 0
			selectDrawerItem(0);
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		if (GERAR_EVENTO_AUTOMATICO) {
			Intent intent = new Intent(this, CriarNovoEventoActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return mDrawerToggle.onOptionsItemSelected(item);
	}

	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onFragmentSelected(int drawerPosition)
	{
		mDrawerList.setItemChecked(drawerPosition, true);
		setTitle(getResources().getStringArray(R.array.drawer_titles)[drawerPosition].toString());
	}

	/**
	 * Apos o usuario ter selecionado um item do drawer, exibe o fragmento correspondente a este item.
	 * @param position A posicao do item do drawer que foi selecionada pelo usuario.
	 */
	public void selectDrawerItem(int position)
	{
		Fragment fragment = null;
		boolean addToBackStack = true;

		switch (position) {
			case 0:
				addToBackStack = false; // fragmento nao sera adicionado ao back stack.
				fragment = new FragmentMain();
				break;
			case 1:
				addToBackStack = true;
				fragment = new FragmentHistorico();
				break;
			case 2:
				addToBackStack = true;
				fragment = new FragmentAjuda();
				break;
			case 3:
				addToBackStack = true;
				fragment = new FragmentSobre();
				break;
			default:
				break;
		}

		if (fragment != null) {
			showFragment(fragment, addToBackStack);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	/**
	 * Exibe um fragmento no <code>FrameLayout</code> prinicpal, adicionando o fragmento atual ao BackStack.
	 * @param fragment O fragmento a ser exibido.
	 * @param title O novo titulo para a <code>ActionBar</code>.
	 */
	public void showFragment(Fragment fragment)
	{
		showFragment(fragment, true);
	}

	/**
	 * Exibe um fragmento no <code>FrameLayout</code> prinicpal.
	 * @param fragment O fragmento a ser exibido.
	 * @param title O novo titulo para a <code>ActionBar</code>.
	 * @param addToBackStack <strong>True</strong> adiciona o fragmento atual ao BackStack, <strong>false</strong> nao adiciona.
	 */
	public void showFragment(Fragment fragment, boolean addToBackStack)
	{
		// substituindo o fragmento atual pelo novo fragmento.
		mFragment = fragment;

		FragmentManager fragmentManager = getFragmentManager();

		if (addToBackStack) {
			// efetua a transicao adicionando o fragmento atual ao back stack:
			fragmentManager.beginTransaction()
					.replace(R.id.frame_conteudo, fragment)
					.addToBackStack(null)
					.commit();
		} else {
			// efetua a transicao sem adicionar o fragmento atual ao back stack:
			fragmentManager.beginTransaction()
					.replace(R.id.frame_conteudo, fragment)
					.commit();
		}
	}

	public void hideKeyboard()
	{
		if (getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
		}
		//		View view = this.getCurrentFocus();
		//		Log.e("focus", String.valueOf((view != null)));
		//		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//		if (view != null) {
		//			inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
		//		}
	}

	/**
	 * Seta os itens para o drawer.
	 */
	private void setDrawerItems()
	{
		int id = 0;
		for (String nome : getResources().getStringArray(R.array.drawer_items)) {
			mDrawerItems.add(new DrawerItem(id, nome, true));
			++id;
		}
	}

	/* ******************************************************************************
	 * RESPONDENDO AOS EVENTOS OCORRIDOS NOS FRAGMENTOS
	 * ******************************************************************************
	 */
	public void onClick(View view)
	{
		if (mFragment instanceof FragmentClick) {
			((FragmentClick) mFragment).onClick(view);
		}
	}

	/* ******************************************************************************
	 * INTERFACES
	 * ******************************************************************************
	 */
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			selectDrawerItem(position);
		}
	}

	/**
	 * Interface a ser utilizada por fragmentos que possuem botoes.
	 */
	public interface FragmentClick
	{
		public void onClick(View view);
	}
}
