package model;

import java.io.Serializable;

public class TrappolaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String tipologia;
	
	public TrappolaBean() {
	}

	public TrappolaBean(int id, String tipologia) {
		super();
		this.id = id;
		this.tipologia = tipologia;
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
	
	
}