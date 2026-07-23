package model;

import java.io.Serializable;

public class NotiziaBean implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	private int id;
	private String titolo;
	private String corpo;

	public NotiziaBean() {
		titolo = "";
		corpo = "";
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the titolo
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * @param titolo the titolo to set
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * @return the corpo
	 */
	public String getCorpo() {
		return corpo;
	}

	/**
	 * @param corpo the corpo to set
	 */
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	
	
}
