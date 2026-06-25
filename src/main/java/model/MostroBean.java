package model;

import java.io.Serializable;
import java.util.BitSet;

public class MostroBean extends CartaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String tipologia;
	private int livello;
	private String attributo;
	private int atk;
	private int def;
	private String categoria;
	private boolean tuner;
	private BitSet frecceLink;
	private int scalaPendulum;
	
	public MostroBean() {
	}
	
	public MostroBean(int id, int punteggio, String nomeIt, String nomeEn, String nomeJp, String testo, String pathImg, String mimeType, 
			String tipologia, int livello, String attributo, int atk, int def, String categoria,
			boolean tuner, BitSet frecce_link, int scala_pendulum) {
		super(id, punteggio, nomeIt, nomeEn, nomeJp, testo, pathImg, mimeType);
		this.tipologia = tipologia;
		this.livello = livello;
		this.attributo = attributo;
		this.atk = atk;
		this.def = def;
		this.categoria = categoria;
		this.tuner = tuner;
		this.frecceLink = frecce_link;
		this.scalaPendulum = scala_pendulum;
	}

	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public int getLivello() {
		return livello;
	}
	public void setLivello(int livello) {
		this.livello = livello;
	}
	public String getAttributo() {
		return attributo;
	}
	public void setAttributo(String attributo) {
		this.attributo = attributo;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public boolean isTuner() {
		return tuner;
	}
	public void setTuner(boolean tuner) {
		this.tuner = tuner;
	}
	public BitSet getFrecceLink() {
		return frecceLink;
	}
	public void setFrecceLink(BitSet frecceLink) {
		this.frecceLink = frecceLink;
	}
	public int getScalaPendulum() {
		return scalaPendulum;
	}
	public void setScalaPendulum(int scalaPendulum) {
		this.scalaPendulum = scalaPendulum;
	}
	
	
}
