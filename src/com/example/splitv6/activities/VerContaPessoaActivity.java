package com.example.splitv6.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.splitv6.R;
import com.example.splitv6.adapters.VerContaPessoaAdapter;
import com.example.splitv6.controller.SplitApplication;
import com.example.splitv6.model.Pessoa;
import com.example.splitv6.model.Produto;
import com.example.splitv6.utilidades.Globais.IntentExtras;

public class VerContaPessoaActivity extends Activity implements OnItemClickListener
{
	private SplitApplication app;
	private VerContaPessoaAdapter adapter;
	private Pessoa pessoa;
	private List<Produto> listProdutos;
	private ListView listView_verContaPessoa;
	private TextView textView_linhaDescricao;
	private Animation animZoom;

	private boolean controlesDesligados;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_conta_pessoa);

		app = (SplitApplication) getApplicationContext();

		listView_verContaPessoa = (ListView) findViewById(R.id.listview_ver_conta_pessoa);

		pessoa = (Pessoa) getIntent().getSerializableExtra(IntentExtras.PESSOA);
		listProdutos = pessoa.getProdutos();

		textView_linhaDescricao = (TextView) findViewById(R.id.textview_linha_descricao);
		textView_linhaDescricao.setText("Produtos consumidos pela pessoa");

		((TextView) findViewById(R.id.textview_nome_pessoa)).setText(pessoa.getNome());
		((TextView) findViewById(R.id.textview_valor_conta)).setText(pessoa.getTextoValorConta());

		adapter = new VerContaPessoaAdapter(this, R.layout.listview_ver_conta_pessoa, listProdutos);
		listView_verContaPessoa.setAdapter(adapter);
		listView_verContaPessoa.setOnItemClickListener(this);

		animZoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_linha_descricao);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);

		controlesDesligados = getIntent().getBooleanExtra("voltar", false);

		if (!controlesDesligados) {
			textView_linhaDescricao.startAnimation(animZoom);
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if (controlesDesligados) {
			final Activity activity = this;
			((LinearLayout) findViewById(R.id.button_info_pessoa)).setEnabled(false);
			listView_verContaPessoa.setEnabled(false);
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					NavUtils.navigateUpFromSameTask(activity);
					overridePendingTransition(R.anim.deslizar_abre_pai, R.anim.deslizar_fecha_filho);
				}
			}, 1000);
		}
	}

	@Override
	public void onBackPressed()
	{
		if (!controlesDesligados) {
			NavUtils.navigateUpFromSameTask(this);
			overridePendingTransition(R.anim.deslizar_abre_pai, R.anim.deslizar_fecha_filho);
		}
	}

	/*
	 * Escuta e recebe os cliques ocorridos nos itens da ListView.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Produto produto = (Produto) this.listView_verContaPessoa.getItemAtPosition(position);

		app.setActivityAvancando();
		Intent intent = new Intent(this, VerContaPessoaProdutoActivity.class);
		intent.putExtra(IntentExtras.PESSOA, pessoa);
		intent.putExtra(IntentExtras.PRODUTO, produto);
		startActivity(intent);
		overridePendingTransition(R.anim.deslizar_abre_filho, R.anim.deslizar_fecha_pai);
	}

	/*
	 * Escuta e recebe os cliques nos botoes.
	 */
	public void onClick(View view)
	{
		switch (view.getId()) {
			case R.id.button_info_pessoa:
				NavUtils.navigateUpFromSameTask(this);
				overridePendingTransition(R.anim.deslizar_abre_pai, R.anim.deslizar_fecha_filho);
				break;
		}
	}
}
