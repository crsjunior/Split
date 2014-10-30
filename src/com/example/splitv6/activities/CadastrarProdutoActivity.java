package com.example.splitv6.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.splitv6.R;
import com.example.splitv6.controller.SplitApplication;
import com.example.splitv6.model.Pessoa;
import com.example.splitv6.model.Produto;
import com.example.splitv6.utilidades.Utilidades;

public class CadastrarProdutoActivity extends Activity
{
	private SplitApplication app;
	private EditText editText_nomeProduto;
	private EditText editText_precoProduto;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_produto);

		app = (SplitApplication) getApplicationContext();
		editText_nomeProduto = (EditText) findViewById(R.id.edittext_nome_produto);
		editText_precoProduto = (EditText) findViewById(R.id.edittext_preco_produto);

		app.suprimirTeclado(getWindow());

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		editText_nomeProduto.setText(app.getProdutoInfo().getNome());
		editText_precoProduto.setText(app.getProdutoInfo().getPreco());

		TextView textView_buttonVincularPessoas = (TextView) findViewById(R.id.button_vincular_pessoas_textview);
		textView_buttonVincularPessoas.setText(String.valueOf(app.getProdutoInfo().getPessoasVinculadas().size()));
		/*
		 * Nas linhas comentadas abaixo, sao aplicados estilos personalizados ao TextView caso
		 * o usuario nao tenha vinculado nenhuma pessoa ao produto.
		 */
		//		if (app.getProdutoInfo().getPessoasVinculadas().size() > 0) {
		//			textView_buttonVincularPessoas.setTextColor(getResources().getColor(R.color.button_vincular_pessoas_textview_ok));
		//			textView_buttonVincularPessoas.setTypeface(null, Typeface.NORMAL);
		//		} else {
		//			textView_buttonVincularPessoas.setTextColor(getResources().getColor(R.color.button_vincular_pessoas_textview_erro));
		//			textView_buttonVincularPessoas.setTypeface(null, Typeface.BOLD);
		//		}
	}

	@Override
	public void onBackPressed()
	{
		NavUtils.navigateUpTo(this, getIntent());
		overridePendingTransition(R.anim.esmaecer_abre_pai, R.anim.esmaecer_fecha_filho);
	}

	public void onClick_vincularPessoas(View view)
	{
		app.getProdutoInfo().setNome(editText_nomeProduto.getText().toString());
		app.getProdutoInfo().setPreco(editText_precoProduto.getText().toString());

		Intent intent = new Intent(this, VincularProdutoPessoasActivity.class);
		Utilidades.abrirComAtraso(this, intent, R.anim.deslizar_abre_filho, R.anim.deslizar_fecha_pai);
	}

	public void onClick_enviarCadastroProduto(View view)
	{
		view.setEnabled(false);
		String sNome = editText_nomeProduto.getText().toString().trim();
		String sPreco = editText_precoProduto.getText().toString().trim();
		float preco;

		if (sNome.equals("")) {
			Utilidades.enviarToastSemTeclado(this, view, "Informe o nome do produto.", 1000);
			return;
		}
		try {
			preco = Float.parseFloat(sPreco);
		} catch (Exception e) {
			Utilidades.enviarToastSemTeclado(this, view, "Informe o preço do produto.", 1000);
			return;
		}
		if (app.getProdutoInfo().getPessoasVinculadas().size() == 0) {
			Utilidades.enviarToastSemTeclado(this, view, "Vincule as pessoas do produto.", 1000);
			return;
		}

		Produto produto = new Produto(sNome, preco);
		app.getEvento().adicionarProduto(produto);
		for (Pessoa pessoa : app.getProdutoInfo().getPessoasVinculadas()) {
			pessoa.vincularProduto(produto);
		}

		Utilidades.suprimirTeclado(this);
		Utilidades.enviarToast(getApplicationContext(), "Produto adicionado.", 1000, 200);
		Utilidades.voltarComAtraso(this, 1800, R.anim.esmaecer_abre_pai, R.anim.esmaecer_fecha_filho);
	}
}
