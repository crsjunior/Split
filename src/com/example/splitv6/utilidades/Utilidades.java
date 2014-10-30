package com.example.splitv6.utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utilidades
{
	/**
	 * Exibe um <code>Toast</code> contendo uma mensagem que fica visivel durante um intervalo de tempo estabelecido.
	 * @param context O contexto do qual sera enviada a mensagem.
	 * @param mensagem O texto da mensagem.
	 * @param duracao O intervalo de tempo que sera exibida a mensagem.
	 */
	public static void enviarToast(Context context, String mensagem, int duracao)
	{
		final Toast toast = Toast.makeText(context, mensagem, Toast.LENGTH_LONG);
		toast.show();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				toast.cancel();
			}
		}, duracao);
	}

	/**
	 * Exibe um <code>Toast</code> contendo uma mensagem que fica visivel durante um intervalo de tempo estabelecido.
	 * @param context O contexto do qual sera enviada a mensagem.
	 * @param mensagem O texto da mensagem.
	 * @param duracao O intervalo de tempo que sera exibida a mensagem.
	 * @param atraso O intervalo de tempo antes de exibir a mensagem.
	 */
	public static void enviarToast(final Context context, final String mensagem, final int duracao, int atraso)
	{
		final Handler handlerA = new Handler();
		handlerA.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				final Toast toast = Toast.makeText(context, mensagem, Toast.LENGTH_LONG);
				toast.show();
				final Handler handlerB = new Handler();
				handlerB.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						toast.cancel();
					}
				}, duracao);
			}
		}, atraso);
	}

	/**
	 * Exibe um <code>Toast</code> contendo uma mensagem que fica visivel durante um intervalo de tempo estabelecido,
	 * sumprimindo o teclado durante a sua exibicao.
	 * @param context O contexto do qual sera enviada a mensagem.
	 * @param mensagem O texto da mensagem.
	 * @param duracao O texto da mensagem.
	 */
	public static void enviarToastSemTeclado(final Context context, final String mensagem, final int duracao)
	{
		final int atrasoToast = 200;
		final int atrasoTeclado = 600;
		final Activity activity = (Activity) context;
		final Handler handlerA = new Handler();
		final Handler handlerB = new Handler();
		final Handler handlerC = new Handler();

		// suprime o teclado:
		suprimirTeclado(activity);

		handlerA.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				// exibe o toast:
				final Toast toast = Toast.makeText(context, mensagem, Toast.LENGTH_LONG);
				toast.show();
				handlerB.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						// cancela o toast:
						toast.cancel();
						handlerC.postDelayed(new Runnable()
						{
							@Override
							public void run()
							{
								// exibe o teclado:
								exibirTeclado(activity);
							}
						}, atrasoTeclado);
					}
				}, duracao);
			}
		}, atrasoToast);
	}

	public static void enviarToastSemTeclado(final Context context, final View view, final String mensagem, final int duracao)
	{
		final int atrasoToast = 200;
		final int atrasoTeclado = 600;
		final Activity activity = (Activity) context;
		final Handler handlerA = new Handler();
		final Handler handlerB = new Handler();
		final Handler handlerC = new Handler();

		// suprime o teclado:
		suprimirTeclado(activity);

		handlerA.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				// exibe o toast:
				final Toast toast = Toast.makeText(context, mensagem, Toast.LENGTH_LONG);
				toast.show();
				handlerB.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						// cancela o toast:
						toast.cancel();
						handlerC.postDelayed(new Runnable()
						{
							@Override
							public void run()
							{
								// exibe o teclado:
								exibirTeclado(activity);
								view.setEnabled(true);
							}
						}, atrasoTeclado);
					}
				}, duracao);
			}
		}, atrasoToast);
	}

	/**
	 * Suprime o teclado, caso este esteja sendo exibido.
	 * @param activity A activity do qual suprimir o teclado.
	 */
	public static void suprimirTeclado(Activity activity)
	{
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = activity.getCurrentFocus();
		if (view != null) {
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * Exibe o teclado, caso este nao esteja sendo exibido.
	 * @param activity A activity a qual exibir o teclado.
	 */
	public static void exibirTeclado(Activity activity)
	{
		exibirTeclado(activity, 0);
	}

	/**
	 * Exibe o teclado, caso este nao esteja sendo exibido.
	 * @param activity A activity a qual exibir o teclado.
	 * @param atraso O intervalo de tempo antes de exibir o teclado.
	 */
	public static void exibirTeclado(Activity activity, int atraso)
	{
		final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		final View view = activity.getCurrentFocus();
		if (view != null) {
			if (atraso > 0) {
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
					}
				}, atraso);
			} else {
				inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
			}
		}
	}

	/**
	 * Volta para a activity pai apos um atraso determinado.
	 * @param activity A activity atual.
	 * @param duracaoAtraso O intervalo de tempo antes de voltar para a activity pai.
	 */
	public static void voltarComAtraso(final Activity activity, final int duracaoAtraso)
	{
		voltarComAtraso(activity, duracaoAtraso, 0, 0);
	}

	/**
	 * Volta para a activity pai apos um atraso determinado, exibindo uma transicao personalizada entre as activities.
	 * @param activity A activity atual.
	 * @param duracaoAtraso O intervalo de tempo antes de voltar para a activity pai.
	 * @param enterAnim A animacao de transicao de entrada.
	 * @param exitAnim A animacao de transicao de saida.
	 */
	public static void voltarComAtraso(final Activity activity, final int duracaoAtraso, final int enterAnim, final int exitAnim)
	{
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				NavUtils.navigateUpTo(activity, activity.getIntent());
				if (enterAnim > 0 && exitAnim > 0) {
					activity.overridePendingTransition(enterAnim, exitAnim);
				}
			}
		}, duracaoAtraso);
	}

	/**
	 * Abre uma activity a partir de uma intent apos um atraso de tempo de 100ms.
	 * @param activity A activity atual.
	 * @param intent A intent para a activity que sera aberta.
	 */
	public static void abrirComAtraso(final Activity activity, final Intent intent)
	{
		abrirComAtraso(activity, intent, 0, 0);
	}

	/**
	 * Abre uma activity a partir de uma intent apos um atraso de tempo de 100ms,
	 * exibindo uma transicao personalizada entre as activities.
	 * @param activity A activity atual.
	 * @param intent A intent para a activity que sera aberta.
	 * @param enterAnim A animacao de transicao de entrada.
	 * @param exitAnim A animacao de transicao de saida.
	 */
	public static void abrirComAtraso(final Activity activity, final Intent intent, final int enterAnim, final int exitAnim)
	{
		suprimirTeclado(activity);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				activity.startActivity(intent);
				if (enterAnim > 0 && exitAnim > 0) {
					activity.overridePendingTransition(enterAnim, exitAnim);
				}
			}
		}, 100);
	}
}
