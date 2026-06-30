package model;

import java.io.Serializable;

public class InfoSpedBean implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	private int id;
	private int idUtente;
	private String nome;
	private String cognome;
	private String via;
	private int civico;
	private int cap;

	public InfoSpedBean() {}

	
	public InfoSpedBean(int id, int idUtente, String nome, String cognome, String via, int civico, int cap) {
		super();
		this.id = id;
		this.idUtente = idUtente;
		this.nome = nome;
		this.cognome = cognome;
		this.via = via;
		this.civico = civico;
		this.cap = cap;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public int getCivico() {
		return civico;
	}

	public void setCivico(int civico) {
		this.civico = civico;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}
	
	
}
