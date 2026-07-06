package model;

import java.io.Serializable;
import java.sql.Date;

public class OrdineBean implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private String stato;
	private int idOrdine;
	private int idUtente;
	private int idMetodo;
	private int idInfoSped;
	private Date dataAcquisto;
	private Date dataConsegna;
	
	public OrdineBean() {}

	public OrdineBean(int id_ordine, int id_utente, int id_metodo, int id_infosped) {
		this.stato = "Carrello";
		this.idOrdine = id_ordine;
		this.idUtente = id_utente;
		this.idMetodo = id_metodo;
		this.idInfoSped = id_infosped;
		this.dataAcquisto = null;
		this.dataConsegna = null;
	}

	public OrdineBean(String stato, int id_ordine, int id_utente, int id_metodo, int id_infosped, Date data_acquisto,
			Date data_consegna) {
		super();
		this.stato = stato;
		this.idOrdine = id_ordine;
		this.idUtente = id_utente;
		this.idMetodo = id_metodo;
		this.idInfoSped = id_infosped;
		this.dataAcquisto = data_acquisto;
		this.dataConsegna = data_consegna;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int id_ordine) {
		this.idOrdine = id_ordine;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int id_utente) {
		this.idUtente = id_utente;
	}

	public int getIdMetodo() {
		return idMetodo;
	}

	public void setIdMetodo(int id_metodo) {
		this.idMetodo = id_metodo;
	}

	public int getIdInfoSped() {
		return idInfoSped;
	}

	public void setIdInfoSped(int id_infosped) {
		this.idInfoSped = id_infosped;
	}

	public Date getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(Date data_acquisto) {
		this.dataAcquisto = data_acquisto;
	}

	public Date getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(Date data_consegna) {
		this.dataConsegna = data_consegna;
	}
}
