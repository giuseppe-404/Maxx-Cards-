package model;

import java.io.Serializable;
import java.sql.Date;

public class OrdineBean implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private String stato;
	private int id_ordine;
	private int id_utente;
	private int id_metodo;
	private int id_infosped;
	private Date data_acquisto;
	private Date data_consegna;
	
	public OrdineBean() {}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public int getId_ordine() {
		return id_ordine;
	}

	public void setId_ordine(int id_ordine) {
		this.id_ordine = id_ordine;
	}

	public int getId_utente() {
		return id_utente;
	}

	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}

	public int getId_metodo() {
		return id_metodo;
	}

	public void setId_metodo(int id_metodo) {
		this.id_metodo = id_metodo;
	}

	public int getId_infosped() {
		return id_infosped;
	}

	public void setId_infosped(int id_infosped) {
		this.id_infosped = id_infosped;
	}

	public Date getData_acquisto() {
		return data_acquisto;
	}

	public void setData_acquisto(Date data_acquisto) {
		this.data_acquisto = data_acquisto;
	}

	public Date getData_consegna() {
		return data_consegna;
	}

	public void setData_consegna(Date data_consegna) {
		this.data_consegna = data_consegna;
	}

}
