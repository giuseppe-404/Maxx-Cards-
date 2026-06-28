package model;

import java.io.Serializable;

public class TipoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String tipo;

	public TipoBean() {
		tipo = "";
	}
	
	public TipoBean(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
