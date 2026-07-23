package model;

import java.io.Serializable;

public class ProdottiHomeBean implements Serializable{
	private static final long serialVersionUID = 1l;
	private int id;
	
	public ProdottiHomeBean() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
