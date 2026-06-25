package model;

import java.io.Serializable;

public class MagiaBean extends CartaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String tipologia;
	
	public MagiaBean() {
	}

	public MagiaBean(int id, int punteggio, String nomeIt, String nomeEn, String nomeJp, String testo, String pathImg, String mimeType,
			String tipologia) {
		super(id, punteggio, nomeIt, nomeEn, nomeJp, testo, pathImg, mimeType);
		this.tipologia = tipologia;
	}


	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	
}
