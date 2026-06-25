package model;

import java.io.Serializable;

public class ProdottoBean implements Serializable{
	private static final long serialVersionUID = 1l;
	private int id;
	private String nome;
	private int qnt;
	private int prezzo;
	private String descrizione;
	private int sconto;
	private String pathImg;
	private String mimeType;
	
	public ProdottoBean(){}
	
	public ProdottoBean(int id, String nome, int qnt, int prezzo, String descrizione, int sconto, String pathImg,
			String mimeType) {
		this.id = id;
		this.nome = nome;
		this.qnt = qnt;
		this.prezzo = prezzo;
		this.descrizione = descrizione;
		this.sconto = sconto;
		this.pathImg = pathImg;
		this.mimeType = mimeType;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getSconto() {
		return sconto;
	}
	public void setSconto(int sconto) {
		this.sconto = sconto;
	}
	public String getPathImg() {
		return pathImg;
	}
	public void setPathImg(String pathImg) {
		this.pathImg = pathImg;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	
}
