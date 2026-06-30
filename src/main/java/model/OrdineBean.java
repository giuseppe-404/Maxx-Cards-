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

	public OrdineBean(int id_ordine, int id_utente, int id_metodo, int id_infosped) {
		this.stato = "Carrello";
		this.id_ordine = id_ordine;
		this.id_utente = id_utente;
		this.id_metodo = id_metodo;
		this.id_infosped = id_infosped;
		this.data_acquisto = null;
		this.data_consegna = null;
	}

	public OrdineBean(String stato, int id_ordine, int id_utente, int id_metodo, int id_infosped, Date data_acquisto,
			Date data_consegna) {
		super();
		this.stato = stato;
		this.id_ordine = id_ordine;
		this.id_utente = id_utente;
		this.id_metodo = id_metodo;
		this.id_infosped = id_infosped;
		this.data_acquisto = data_acquisto;
		this.data_consegna = data_consegna;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public int getIdOrdine() {
		return id_ordine;
	}

	public void setIdOrdine(int id_ordine) {
		this.id_ordine = id_ordine;
	}

	public int getIdUtente() {
		return id_utente;
	}

	public void setIdUtente(int id_utente) {
		this.id_utente = id_utente;
	}

	public int getIdMetodo() {
		return id_metodo;
	}

	public void setIdMetodo(int id_metodo) {
		this.id_metodo = id_metodo;
	}

	public int getIdInfoSped() {
		return id_infosped;
	}

	public void setIdInfoSped(int id_infosped) {
		this.id_infosped = id_infosped;
	}

	public Date getDataAcquisto() {
		return data_acquisto;
	}

	public void setDataAcquisto(Date data_acquisto) {
		this.data_acquisto = data_acquisto;
	}

	public Date getDataConsegna() {
		return data_consegna;
	}

	public void setDataConsegna(Date data_consegna) {
		this.data_consegna = data_consegna;
	}
}
