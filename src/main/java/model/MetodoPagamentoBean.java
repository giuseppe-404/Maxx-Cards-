package model;

import java.io.Serializable;

public class MetodoPagamentoBean implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	private String metodo;
	private int id;
	private int idUtente;
	
	public MetodoPagamentoBean() {}
	
	public MetodoPagamentoBean(String metodo, int id, int idUtente) {
		super();
		this.metodo = metodo;
		this.id = id;
		this.idUtente = idUtente;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
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
	
	

}
