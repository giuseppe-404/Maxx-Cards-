package model;

import java.io.Serializable;

public class WantsBean implements Serializable{
	private static final long serialVersionUID = 1l;
	
	private int idUtente;
	private int idProdotto;
	
	public WantsBean() {}
	
	public WantsBean(int idUtente, int idProdotto) {
		this.idUtente = idUtente;
		this.idProdotto = idProdotto;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}
}
