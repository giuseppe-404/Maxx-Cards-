package model;

import java.io.Serializable;
import java.util.BitSet;

public class MostroBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String tipologia;
	private int livello;
	private String attributo;
	private int atk;
	private int def;
	private String categoria;
	private boolean tuner;
	private BitSet frecce_link;
	private int scala_pendulum;
	
	public MostroBean() {
	}
	
	public MostroBean(int id, String tipologia, int livello, String attributo, int atk, int def, String categoria,
			boolean tuner, BitSet frecce_link, int scala_pendulum) {
		this.id = id;
		this.tipologia = tipologia;
		this.livello = livello;
		this.attributo = attributo;
		this.atk = atk;
		this.def = def;
		this.categoria = categoria;
		this.tuner = tuner;
		this.frecce_link = frecce_link;
		this.scala_pendulum = scala_pendulum;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public BitSet getFrecce_link() {
		return frecce_link;
	}
	public void setFrecce_link(BitSet frecce_link) {
		this.frecce_link = frecce_link;
	}
	public int getScala_pendulum() {
		return scala_pendulum;
	}
	public void setScala_pendulum(int scala_pendulum) {
		this.scala_pendulum = scala_pendulum;
	}
	
	
}
