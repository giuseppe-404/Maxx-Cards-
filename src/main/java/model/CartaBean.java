package model;

import java.io.Serializable;

public class CartaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	private int punteggio;
	private String nomeIt;
	private String nomeEn;
	private String nomeJp;
	private String testo;
	private String pathImg;
	private String mimeType;
	
	public CartaBean() {
		id=0;
		punteggio=-1;
		nomeIt="";
		nomeEn="";
		nomeJp="";
		testo="";
		pathImg="";
		mimeType="";
	}
	
	public CartaBean(int id, int punteggio, String nomeIt, String nomeEn, String nomeJp, String testo, String pathImg,
			String mimeType) {
		super();
		this.id = id;
		this.punteggio = punteggio;
		this.nomeIt = nomeIt;
		this.nomeEn = nomeEn;
		this.nomeJp = nomeJp;
		this.testo = testo;
		this.pathImg = pathImg;
		this.mimeType = mimeType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public String getNomeIt() {
		return nomeIt;
	}

	public void setNomeIt(String nomeIt) {
		this.nomeIt = nomeIt;
	}

	public String getNomeEn() {
		return nomeEn;
	}

	public void setNomeEn(String nomeEn) {
		this.nomeEn = nomeEn;
	}

	public String getNomeJp() {
		return nomeJp;
	}

	public void setNomeJp(String nomeJp) {
		this.nomeJp = nomeJp;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
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
