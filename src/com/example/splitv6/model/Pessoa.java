package com.example.splitv6.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pessoa implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String nome;
	private List<Produto> produtos;
	private float valorConta;

	public Pessoa()
	{
		this("");
	}

	public Pessoa(String nome)
	{
		this.nome = nome;
		this.produtos = new ArrayList<Produto>();
		this.valorConta = 0l;
	}

	/**
	 * Retorna o nome desta pessoa.
	 * @return O nome desta pessoa.
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * Seta o nome desta pessoa.
	 * @param nome O novo nome desta pessoa.
	 */
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	/**
	 * Retorna a lista de produtos vinculados a esta pessoa.
	 * @return A lista de produtos vinculados a esta pessoa.
	 */
	public List<Produto> getProdutos()
	{
		return produtos;
	}

	/**
	 * Retorna o valor da conta desta pessoa.
	 * @return O valor da conta desta pessoa.
	 */
	public float getValorConta()
	{
		return valorConta;
	}

	/**
	 * Seta o valor da conta desta pessoa.
	 * @param valorConta O valor da conta desta pessoa.
	 */
	public void setValorConta(float valorConta)
	{
		this.valorConta = valorConta;
	}

	/**
	 * Retorna uma <code>String</code> com o valor a ser pago por esta pessoa.
	 * @return Uma <code>String</code> com o valor a ser pago por esta pessoa
	 */
	public String getTextoValorConta()
	{
		return String.format("R$ %.2f", this.valorConta);
	}

	public void calcularValorConta()
	{
		this.valorConta = 0l;
		for (Produto produto : this.produtos) {
			this.valorConta += produto.getMediaPreco();
		}
	}

	/**
	 * Vincula um produto a esta pessoa, e vice-versa.
	 * @param produto O produto a ser vinculado a esta pessoa.
	 */
	public void vincularProduto(Produto produto)
	{
		this.produtos.add(produto);
		produto.vincularPessoa(this);
	}

	/**
	 * Verifica se o <code>Object</code> passado eh uma <code>Pessoa</code>, e entao se a <code>Pessoa</code> passada
	 * possui o mesmo nome que esta <code>Pessoa</code>.
	 * @param o O <code>Object</code> a ser comparado com esta <code>Pessoa</code>.
	 * @return <strong>True</strong> se forem duas <code>Pessoas</code> iguais,
	 *         caso contrario, <strong>false</strong>.
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Pessoa) {
			Pessoa oPessoa = (Pessoa) o;
			return this.nome.equals(oPessoa.getNome());
		}
		return false;
	}
}
