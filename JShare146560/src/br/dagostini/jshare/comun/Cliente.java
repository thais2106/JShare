package br.dagostini.jshare.comun;

import java.io.Serializable;

/**
 * Identificacao do cliente.
 * 
 * @author fernandod
 *
 */
public class Cliente implements Serializable {

	private static final long serialVersionUID = 8998030883019232904L;
	
	private String nome;
	private String ip;
	private int porta;
	
	public Cliente(String nome, String ip, int porta) {
		this.nome = nome;
		this.ip = ip;
		this.porta = porta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}
}
