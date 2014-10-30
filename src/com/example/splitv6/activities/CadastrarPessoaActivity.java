package com.example.splitv6.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.splitv6.R;
import com.example.splitv6.controller.SplitApplication;
import com.example.splitv6.model.Pessoa;
import com.example.splitv6.utilidades.Utilidades;

public class CadastrarPessoaActivity extends Activity implements AnimationListener
{
	private SplitApplication app;
	private TextView textView_status;
	private EditText editText_nomePessoa;
	private Button button_enviarCadastroPessoa;
	private Animation animZoom;
	private boolean voltar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_pessoa);

		app = (SplitApplication) getApplicationContext();

		textView_status = (TextView) findViewById(R.id.textView_status);
		editText_nomePessoa = (EditText) findViewById(R.id.edittext_nome_pessoa);
		button_enviarCadastroPessoa = (Button) findViewById(R.id.button_enviar_cadastro_pessoa);

		animZoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.text_info_zoom);
		animZoom.setAnimationListener(this);

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);

		app.suprimirTeclado(getWindow());
	}

	@Override
	public void onBackPressed()
	{
		NavUtils.navigateUpTo(this, getIntent());
		overridePendingTransition(R.anim.esmaecer_abre_pai, R.anim.esmaecer_fecha_filho);
	}

	public void onClick(View view)
	{
		//		view.setEnabled(false);
		String sNome = ((EditText) findViewById(R.id.edittext_nome_pessoa)).getText().toString().trim();

		if (sNome.equals("")) {
			voltar = false;
			textView_status.setText("Informe o nome da pessoa.");
			textView_status.startAnimation(animZoom);
			//			Utilidades.enviarToastSemTeclado(this, view, "Informe o nome da pessoa.", 1000);
			return;
		}

		app.getEvento().adicionarPessoa(new Pessoa(sNome));

		voltar = true;
		textView_status.setText("Pessoa adicionada.");
		textView_status.startAnimation(animZoom);

		//		Utilidades.suprimirTeclado(this);
		//		Utilidades.enviarToast(getApplicationContext(), "Pessoa adicionada.", 1000, 200);
		//		Utilidades.voltarComAtraso(this, 1800);
		//		onNavigateUp();
	}

	@Override
	public void onAnimationEnd(Animation animation)
	{
		if (voltar) {
			NavUtils.navigateUpTo(this, getIntent());
			overridePendingTransition(R.anim.esmaecer_abre_pai, R.anim.esmaecer_fecha_filho);
		} else {
			editText_nomePessoa.setEnabled(true);
			button_enviarCadastroPessoa.setEnabled(true);
			Utilidades.exibirTeclado(this);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation)
	{

	}

	@Override
	public void onAnimationStart(Animation animation)
	{
		editText_nomePessoa.setEnabled(false);
		button_enviarCadastroPessoa.setEnabled(false);
	}
}
