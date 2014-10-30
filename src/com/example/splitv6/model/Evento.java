package com.example.splitv6.model;

import java.util.ArrayList;
import java.util.List;

public class Evento
{
	private String nome;
	private List<Pessoa> pessoas;
	private List<Produto> produtos;
	private float valorConta;

	public Evento()
	{
		this("");
	}

	public Evento(String nome)
	{
		this.nome = nome;
		this.pessoas = new ArrayList<Pessoa>();
		this.produtos = new ArrayList<Produto>();
		this.valorConta = 0l;
	}

	/**
	 * Retorna o nome deste evento.
	 * @return O nome deste evento.
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * Seta o nome deste evento.
	 * @param nome O novo nome deste evento.
	 */
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	/**
	 * Retorna a lista de pessoas vinculadas a este evento.
	 * @return A lista de pessoas vinculadas a este evento.
	 */
	public List<Pessoa> getPessoas()
	{
		return pessoas;
	}

	/**
	 * Retorn a lista de produtos vinculados a este evento.
	 * @return A lista de produtos vinculados a este evento.
	 */
	public List<Produto> getProdutos()
	{
		return produtos;
	}

	/**
	 * Retorn o valor da conta deste evento.
	 * @return O valor da conta deste evento.
	 */
	public float getValorConta()
	{
		return valorConta;
	}

	/**
	 * Retorna a quantidade de pessoas vinculadas a este evento.
	 * @return A quantidade de pessoas vinculadas a este evento.
	 */
	public int getQtdPessoas()
	{
		return this.pessoas.size();
	}

	/**
	 * Retorna a quantidade de produtos vinculadas a este evento.
	 * @return A quantidade de produtos vinculadas a este evento.
	 */
	public int getQtdProdutos()
	{
		return this.produtos.size();
	}

	/**
	 * Retorna uma <code>String</code> com a quantidade de pessoas vinculadas a este evento.
	 * @return Uma <code>String</code> com a quantidade de pessoas vinculadas a este evento.
	 */
	public String getTextoQtdPessoas()
	{
		return String.valueOf(this.pessoas.size());
	}

	/**
	 * Retorna uma <code>String</code> com a quantidade de produtos vinculados a este evento.
	 * @return Uma <code>String</code> com a quantidade de produtos vinculados a este evento.
	 */
	public String getTextoQtdProdutos()
	{
		return String.valueOf(this.produtos.size());
	}

	/**
	 * Retorna uma <code>String</code> formatada com o valor da conta deste evento.
	 * @return Uma <code>String</code> formatada com o valor da conta deste evento.
	 */
	public String getTextoValorConta()
	{
		return String.format("R$ %.2f", this.valorConta);
	}

	/**
	 * Procura e, se encontrada, retorna uma pessoa vinculada a este evento pelo seu nome.
	 * @param nome O nome da pessoa vinculada a este evento pela qual procurar.
	 * @return Uma <code>Pessoa</code> se esta for encontrada, caso contrario, <code>null</code>;
	 */
	public Pessoa getPessoaByNome(String nome)
	{
		for (Pessoa pessoa : this.pessoas) {
			if (pessoa.getNome().equals(nome)) {
				return pessoa;
			}
		}
		return null;
	}

	/**
	 * Procura e, se encontrada, retorna um produto vinculado a este evento pelo seu nome.
	 * @param nome O nome do produto vinculada a este evento pelao qual procurar.
	 * @return Um <code>Produto</code> se este for encontrada, caso contrario, <code>null</code>;
	 */
	public Produto getProdutoByNome(String nome)
	{
		for (Produto produto : this.produtos) {
			if (produto.getNome().equals(nome)) {
				return produto;
			}
		}
		return null;
	}

	/**
	 * Adiciona uma pessoa a este evento.
	 * @param pessoa A pessoa a ser adicionada a este evento.
	 */
	public void adicionarPessoa(Pessoa pessoa)
	{
		this.pessoas.add(pessoa);
	}

	/**
	 * Adiciona um produto a este evento.
	 * @param produto O produto a ser adicionado a este evento.
	 */
	public void adicionarProduto(Produto produto)
	{
		this.produtos.add(produto);
		this.valorConta += produto.getPreco();
	}

	/**
	 * Retorna uma media com o valor a ser pago por cada pessoa, conforme a quantidade de
	 * pessoas e de produtos deste evento (valor da conta / quantidade de pessoas).
	 * @return Uma media com o valor a ser pago por cada pessoa deste evento.
	 */
	public float calcularMediaGeralPreco()
	{
		return this.valorConta / this.pessoas.size();
	}

	/**
	 * Calcula toda a conta, o valor que cada pessoa tera que pagar.
	 */
	public void calcularConta()
	{
		for (Produto produto : this.produtos) {
			produto.calcularMediaPreco();
		}
		for (Pessoa pessoa : this.pessoas) {
			pessoa.calcularValorConta();
		}
	}
}
