package com.example.splitv6.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.splitv6.R;
import com.example.splitv6.adapters.VerContaAdapter;
import com.example.splitv6.controller.SplitApplication;
import com.example.splitv6.model.Evento;
import com.example.splitv6.model.Pessoa;
import com.example.splitv6.utilidades.Globais.IntentExtras;

public class VerContaActivity extends Activity implements OnItemClickListener
{
	private SplitApplication app;
	private VerContaAdapter adapter;
	private ListView listView_verConta;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_conta);

		app = (SplitApplication) getApplicationContext();
		Evento evento = app.getEvento();

		// calcula toda a conta (valor total, valor por pessoa, etc...):
		evento.calcularConta();

		listView_verConta = (ListView) findViewById(R.id.listview_ver_conta);
		adapter = new VerContaAdapter(this, R.layout.listview_ver_conta, evento.getPessoas());
		listView_verConta.setAdapter(adapter);
		listView_verConta.setOnItemClickListener(this);

		// informando o nome do evento e o valor total da conta:
		((TextView) findViewById(R.id.textview_nome_evento)).setText(evento.getNome());
		((TextView) findViewById(R.id.textview_valor_total_conta)).setText(evento.getTextoValorConta());

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.ver_conta, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpTo(this, getIntent());
				return true;
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed()
	{
		NavUtils.navigateUpTo(this, getIntent());
		overridePendingTransition(R.anim.esmaecer_abre_pai, R.anim.esmaecer_fecha_filho);
	}

	/*
	 * Escuta e recebe os cliques ocorridos nos itens da ListView.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// recuperando a pessoa correspondente ao item da ListView que foi clicado:
		Pessoa pessoa = (Pessoa) this.listView_verConta.getItemAtPosition(position);

		// avancando para a activity VerContaPessoaActivity:
		app.setActivityAvancando();
		Intent intent = new Intent(this, VerContaPessoaActivity.class);
		intent.putExtra(IntentExtras.PESSOA, pessoa);
		startActivity(intent);
		overridePendingTransition(R.anim.deslizar_abre_filho, R.anim.deslizar_fecha_pai);
	}

	/*
	 * Escuta e recebe os cliques nos botoes.
	 */
	public void onClick(View view)
	{
		switch (view.getId()) {
			case R.id.button_info_conta:
				NavUtils.navigateUpTo(this, getIntent());
				overridePendingTransition(R.anim.esmaecer_abre_pai, R.anim.esmaecer_fecha_filho);
				break;
			case R.id.button_ver_conta_voltar:
				NavUtils.navigateUpTo(this, getIntent());
				overridePendingTransition(R.anim.esmaecer_abre_pai, R.anim.esmaecer_fecha_filho);
				break;
			case R.id.button_ver_conta_encerrar:

				break;
		}
	}
}
