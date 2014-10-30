package com.example.splitv6.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Produto implements Serializable
{
	private static final long serialVersionUID = 2L;
	private String nome;
	private float preco;
	private float mediaPreco;
	private List<Pessoa> pessoas;

	public Produto()
	{
		this("", 0l);
	}

	public Produto(String nome, float preco)
	{
		this.nome = nome;
		this.preco = preco;
		this.mediaPreco = 0l;
		this.pessoas = new ArrayList<Pessoa>();
	}

	/**
	 * Retorna o nome deste produto.
	 * @return O nome deste produto.
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * Seta o nome deste produto.
	 * @param nome O novo nome deste produto.
	 */
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	/**
	 * Retorna o preco deste produto.
	 * @return O preco deste produto.
	 */
	public float getPreco()
	{
		return preco;
	}

	/**
	 * Seta o preco deste produto.
	 * @param preco O novo preco deste produto.
	 */
	public void setPreco(float preco)
	{
		this.preco = preco;
	}

	/**
	 * Retorna o valor a ser pago por cada pessoa vinculada a este produto.
	 * @return O valor a ser pago por cada pessoa vinculada a este produto.
	 */
	public float getMediaPreco()
	{
		return mediaPreco;
	}

	/**
	 * Retorna a lista de pessoas vinculadas a este produto.
	 * @return A lista de pessoas vinculadas a este produto.
	 */
	public List<Pessoa> getPessoas()
	{
		return pessoas;
	}

	/**
	 * Retorna uma <code>String</code> com o preco deste produto.
	 * @return Uma <code>String</code> com o preco deste produto.
	 */
	public String getTextoPreco()
	{
		return String.format("R$ %.2f", this.preco);
	}

	/**
	 * Retorna uma <code>String</code> com o valor a ser pago por cada pessoa vinculada a este produto.
	 * @return Uma <code>String</code> com o valor a ser pago por cada pessoa vinculada a este produto.
	 */
	public String getTextoMediaPreco()
	{
		return String.format("R$ %.2f", this.mediaPreco);
	}

	/**
	 * Retorna a quantidade de pessoas vinculadas a este produto.
	 * @return A quantidade de pessoas vinculadas a este produto.
	 */
	public int getQtdPessoas()
	{
		return this.pessoas.size();
	}

	/**
	 * Vincula uma pessoa a este produto.
	 * @param pessoa A pessoa a ser vinculada a este produto.
	 */
	protected void vincularPessoa(Pessoa pessoa)
	{
		this.pessoas.add(pessoa);
	}

	/**
	 * Calcula o valor a ser pago por cada pessoa vinculada a este produto.
	 */
	public void calcularMediaPreco()
	{
		this.mediaPreco = this.preco / getQtdPessoas();
	}

	/**
	 * Verifica se o <code>Object</code> passado eh um <code>Produto</code>, e entao se o <code>Produto</code> passado
	 * possui o mesmo nome e o mesmo preco que este <code>Produto</code>.
	 * @param o O <code>Object</code> a ser comparado com este <code>Produto.</code>
	 * @return <strong>True</strong> se forem dois <code>Produtos</code> iguais,
	 *         caso contrario, <strong>false</strong>.
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Produto) {
			Produto oProduto = (Produto) o;
			return (this.nome.equals(oProduto.getNome()) && this.preco == oProduto.getPreco());
		}
		return false;
	}
}
