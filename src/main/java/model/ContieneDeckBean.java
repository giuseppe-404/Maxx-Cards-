package model;

import java.io.Serializable;

public class ContieneDeckBean implements Serializable {
	private static final long serialVersionUID = 1l;
	private int idDeck;
	private int idCarta;
	private int qnt;
	
	public int getIdDeck() {
		return idDeck;
	}
	public void setIdDeck(int idDeck) {
		this.idDeck = idDeck;
	}
	public int getIdCarta() {
		return idCarta;
	}
	public void setIdCarta(int idCarta) {
		this.idCarta = idCarta;
	}
	public int getQnt() {
		return qnt;
	}
	public void setQnt(int qnt) {
		this.qnt = qnt;
	}
	
	
}
