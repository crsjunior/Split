package com.example.splitv6.controller;

import com.example.splitv6.model.Evento;

public final class SplitController
{
	public static SplitController controller;

	private Evento evento;

	/**
	 * Retorna o evento atual do controller.
	 * @return O evento atual do controller.
	 */
	public Evento getEvento()
	{
		return evento;
	}

	/**
	 * Cria e retorna um novo evento neste controller.
	 * @param nome O nome do novo evento que sera criado neste controller.
	 * @return O evento criado neste controller.
	 */
	public Evento criarEvento(String nome)
	{
		this.evento = new Evento(nome);
		return this.evento;
	}

	/**
	 * Verifica se existe um evento criado neste controller.
	 * @return <strong>True</strong> se existe um evento criado neste controller, caso contrario, <strong>false</strong>.
	 */
	public boolean existeEvento()
	{
		return this.evento != null;
	}
}
