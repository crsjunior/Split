package com.example.splitv6.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.view.Window;
import android.view.WindowManager;

import com.example.splitv6.model.Evento;
import com.example.splitv6.model.Pessoa;

public class SplitApplication extends Application
{
	private Evento evento;
	private SplitConfig config;
	private boolean activityAvancando = true;
	private ProdutoInfo produtoInfo;

	/**
	 * Retorna o evento atual.
	 * @return O evento atual.
	 */
	public synchronized Evento getEvento()
	{
		return evento;
	}

	/**
	 * Retorna as configuracoes do aplicativo Split.
	 * @return As configuracoes do aplicativo Split.
	 */
	public synchronized SplitConfig getConfig()
	{
		if (config == null) {
			config = new SplitConfig(getApplicationContext());
		}
		return config;
	}

	/**
	 * Retorna um <code>boolean</code> indicando o tipo de transicao ocorrendo entre as activities.
	 * @return Um <code>boolean</code> indicando o tipo de transicao ocorrendo entre as activities.
	 */
	public boolean isActivityAvancando()
	{
		return activityAvancando;
	}

	/**
	 * Seta o tipo de transicao que ocorrera entre activities como <strong>avancando</strong>.
	 */
	public void setActivityAvancando()
	{
		this.activityAvancando = true;
	}

	/**
	 * Seta o tipo de transicao que ocorrera entre activities como <strong>voltando</strong>.
	 */
	public void setActivityVoltando()
	{
		this.activityAvancando = false;
	}

	/**
	 * Retorna as informacoes armazenadas temporariamente de um produto.
	 * @return As informacoes armazenadas temporariamente de um produto.
	 */
	public synchronized ProdutoInfo getProdutoInfo()
	{
		if (produtoInfo == null) {
			produtoInfo = new ProdutoInfo();
		}
		return produtoInfo;
	}

	/**
	 * Cria um novo evento global.
	 * @param nome O nome do novo evento.
	 * @return O evento global.
	 */
	public Evento criarEvento(String nome)
	{
		evento = new Evento(nome);
		return evento;
	}

	/**
	 * Verifica se o evento global existe.
	 * @return True se o evento global existe, caso contrario, false.
	 */
	public boolean existeEvento()
	{
		return (evento != null);
	}

	public void suprimirTeclado(Window window)
	{
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public class ProdutoInfo
	{
		private String nome;
		private String preco;
		private List<Pessoa> pessoasVinculadas;

		public ProdutoInfo()
		{
			resetar();
		}

		public String getNome()
		{
			return nome;
		}

		public void setNome(String nome)
		{
			this.nome = nome;
		}

		public String getPreco()
		{
			return preco;
		}

		public void setPreco(String preco)
		{
			this.preco = preco;
		}

		public List<Pessoa> getPessoasVinculadas()
		{
			return pessoasVinculadas;
		}

		public void setPessoasVinculadas(List<Pessoa> pessoasVinculadas)
		{
			this.pessoasVinculadas = pessoasVinculadas;
		}

		public void resetar()
		{
			nome = "";
			preco = "";
			pessoasVinculadas = new ArrayList<Pessoa>();
		}
	}
}
