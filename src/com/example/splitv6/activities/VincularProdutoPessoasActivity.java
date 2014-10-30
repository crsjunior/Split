package com.example.splitv6.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.example.splitv6.R;
import com.example.splitv6.adapters.VincularProdutoPessoasAdapter;
import com.example.splitv6.controller.SplitApplication;
import com.example.splitv6.model.Pessoa;

public class VincularProdutoPessoasActivity extends Activity implements DialogInterface.OnClickListener
{
	private SplitApplication app;
	private VincularProdutoPessoasAdapter adapter;
	private Animation animZoom;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vincular_produto_pessoas);

		app = (SplitApplication) getApplicationContext();

		TextView textView_linhaDescricao = (TextView) findViewById(R.id.textview_linha_descricao);
		textView_linhaDescricao.setText("Selecione as pessoas que consumirão este produto");

		// criando e setando o adapter para a list view
		adapter = new VincularProdutoPessoasAdapter(this,
				R.layout.listview_vincular_produto_pessoas_2,
				(ArrayList<Pessoa>) app.getEvento().getPessoas());

		((ListView) findViewById(R.id.listview_vincular_produto_pessoas)).setAdapter(adapter);

		animZoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_linha_descricao);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);

		textView_linhaDescricao.startAnimation(animZoom);
	}

	@Override
	public void onBackPressed()
	{
		// verificando alteracoes na lista de pessoas vinculadas:
		boolean iguais = true;
		for (Pessoa p1 : app.getProdutoInfo().getPessoasVinculadas()) {
			if (!adapter.getPessoasSelecionadas().contains(p1)) {
				iguais = false;
				break;
			}
		}
		if (iguais) {
			for (Pessoa p : adapter.getPessoasSelecionadas()) {
				if (!app.getProdutoInfo().getPessoasVinculadas().contains(p)) {
					iguais = false;
					break;
				}
			}
		}

		if (iguais) {
			voltarParaPai();
		} else { // houve alteracao na lista de pessoas vinculadas.
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString(R.string.vincluar_produto_pessoas_dialog_titulo));
			builder.setMessage("\n" + getResources().getString(R.string.vincluar_produto_pessoas_dialog_mensagem) + "\n");
			builder.setPositiveButton(getResources().getString(R.string.vincluar_produto_pessoas_dialog_botao_positivo), this);
			builder.setNegativeButton(getResources().getString(R.string.vincluar_produto_pessoas_dialog_botao_negativo), this);
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	public void onClick(View view)
	{
		app.getProdutoInfo().setPessoasVinculadas(adapter.getPessoasSelecionadas());
		voltarParaPai();
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				app.getProdutoInfo().setPessoasVinculadas(adapter.getPessoasSelecionadas());
				break;
			case DialogInterface.BUTTON_NEGATIVE:

				break;
		}
		voltarParaPai();
	}

	private void voltarParaPai()
	{
		NavUtils.navigateUpTo(this, getIntent());
		overridePendingTransition(R.anim.deslizar_abre_pai, R.anim.deslizar_fecha_filho);
	}
}
