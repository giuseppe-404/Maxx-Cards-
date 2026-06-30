package model;

import java.io.Serializable;

public class ProdottoCompratoBean implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	private int id;
	private int idOrdine;
	private int idOriginale;
	private int prezzo;
	private String nome;
	private int qnt;
	private String info;
	
	public ProdottoCompratoBean() {}
	
	public ProdottoCompratoBean(int id, int idOrdine, int idOriginale, int prezzo, String nome, int qnt, String info) {
		this.id = id;
		this.idOrdine = idOrdine;
		this.idOriginale = idOriginale;
		this.prezzo = prezzo;
		this.nome = nome;
		this.qnt = qnt;
		this.info = info;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public int getIdOriginale() {
		return idOriginale;
	}

	public void setIdOriginale(int idOriginale) {
		this.idOriginale = idOriginale;
	}

	public int getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQnt() {
		return qnt;
	}

	public void setQnt(int qnt) {
		this.qnt = qnt;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
