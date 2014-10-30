package com.example.splitv6.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.splitv6.R;
import com.example.splitv6.controller.SplitApplication;
import com.example.splitv6.model.Evento;
import com.example.splitv6.model.Pessoa;
import com.example.splitv6.model.Produto;
import com.example.splitv6.utilidades.Utilidades;

public class CriarNovoEventoActivity extends Activity
{
	public static final String TAG = "CriarNovoEventoActivity";
	private static final boolean GERAR_EVENTO_AUTOMATICO = true;
	private SplitApplication app;

	private EditText editText_nomeEvento;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criar_novo_evento);

		app = (SplitApplication) getApplicationContext();

		editText_nomeEvento = (EditText) findViewById(R.id.edittext_nome_evento);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		app.setActivityAvancando();
		if (GERAR_EVENTO_AUTOMATICO) {
			Evento e = ((SplitApplication) getApplicationContext()).criarEvento("Teste");
			Pessoa pes1 = new Pessoa("Carlos Schwarz Júnior");
			Pessoa pes2 = new Pessoa("Vanessa Cancian");
			Pessoa pes3 = new Pessoa("Daniel Lindgren");
			Produto pro1 = new Produto("Refrigerante 2L", 3.0f);
			Produto pro2 = new Produto("Cerveja", 5.0f);
			Produto pro3 = new Produto("Pizza Calabresa", 20.0f);
			pes1.vincularProduto(pro1);
			pes1.vincularProduto(pro3);
			pes2.vincularProduto(pro1);
			pes2.vincularProduto(pro3);
			pes3.vincularProduto(pro2);
			pes3.vincularProduto(pro3);
			e.adicionarPessoa(pes1);
			e.adicionarPessoa(pes2);
			e.adicionarPessoa(pes3);
			e.adicionarPessoa(new Pessoa("Saulo Vieira"));
			e.adicionarPessoa(new Pessoa("Sandrini"));
			e.adicionarProduto(pro1);
			e.adicionarProduto(pro2);
			e.adicionarProduto(pro3);
			Intent intent = new Intent(this, EventoActivity.class);
			intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
		}
	}

	public void onClick(View view)
	{
		String sNomeEvento = editText_nomeEvento.getText().toString().trim();

		if (sNomeEvento.equals("")) {
			Utilidades.enviarToast(getApplicationContext(), "Informe o nome do evento.", 500);
			return;
		}

		//		SplitController.controller.criarEvento(sNomeEvento);
		((SplitApplication) getApplicationContext()).criarEvento(sNomeEvento);

		Intent intent = new Intent(this, EventoActivity.class);
		// setando os flags para o intent:
		// - FLAG_ACTIVITY_NEW_TASK: Indica que a nova activity sera a nova raiz.
		// - FLAG_ACTIVITY_CLEAR_TASK: Limpa a lista de back stack.
		intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
