package com.example.splitv6.activities;

import java.util.List;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.example.splitv6.R;
import com.example.splitv6.adapters.VerContaPessoaProdutoAdapter;
import com.example.splitv6.model.Pessoa;
import com.example.splitv6.model.Produto;
import com.example.splitv6.utilidades.Globais.IntentExtras;

public class VerContaPessoaProdutoActivity extends Activity
{
//	private SplitApplication app;
	private VerContaPessoaProdutoAdapter adapter;
	private Pessoa pessoa;
	private Produto produto;
	private List<Pessoa> listPessoas;
	private TextView textView_linhaDescricao;
	private Animation animZoom;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_conta_pessoa_produto);

//		app = (SplitApplication) getApplicationContext();

		pessoa = (Pessoa) getIntent().getSerializableExtra(IntentExtras.PESSOA);
		produto = (Produto) getIntent().getSerializableExtra(IntentExtras.PRODUTO);
		listPessoas = produto.getPessoas();

		textView_linhaDescricao = (TextView) findViewById(R.id.textview_linha_descricao);
		textView_linhaDescricao.setText("Pessoas que consumiram este produto");

		((TextView) findViewById(R.id.textview_nome_pessoa)).setText(pessoa.getNome());
		((TextView) findViewById(R.id.textview_valor_conta)).setText(pessoa.getTextoValorConta());
		((TextView) findViewById(R.id.textview_nome_produto)).setText(produto.getNome());
		((TextView) findViewById(R.id.textview_preco_produto)).setText(produto.getTextoPreco());

		adapter = new VerContaPessoaProdutoAdapter(this, R.layout.listview_ver_conta_pessoa_produto, listPessoas);
		((ListView) findViewById(R.id.listview_ver_conta_pessoa_produto)).setAdapter(adapter);

		animZoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_linha_descricao);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);

		textView_linhaDescricao.startAnimation(animZoom);
	}

	@Override
	public void onBackPressed()
	{
		voltarListaProduto();
	}

	/*
	 * Escuta e recebe os cliques nos botoes.
	 */
	public void onClick(View view)
	{
		switch (view.getId()) {
			case R.id.button_info_pessoa:
				voltarListaPessoa();
				break;
			case R.id.button_info_produto:
				voltarListaProduto();
				break;
		}
	}

	private void voltarListaPessoa()
	{
		Intent upIntent = NavUtils.getParentActivityIntent(this);
		if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
			// nunca aconteceu, se acontecer pode ser passivel de BUG!!!
			TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
		} else {
			upIntent.putExtra(IntentExtras.PESSOA, pessoa);
			upIntent.putExtra("voltar", true);
			NavUtils.navigateUpTo(this, upIntent);
			overridePendingTransition(R.anim.deslizar_abre_pai, R.anim.deslizar_fecha_filho);
		}
	}

	private void voltarListaProduto()
	{
		Intent upIntent = NavUtils.getParentActivityIntent(this);
		if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
			// nunca aconteceu, se acontecer pode ser passivel de BUG!!!
			TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
		} else {
			upIntent.putExtra(IntentExtras.PESSOA, pessoa);
			NavUtils.navigateUpTo(this, upIntent);
			overridePendingTransition(R.anim.deslizar_abre_pai, R.anim.deslizar_fecha_filho);
		}
	}
}
