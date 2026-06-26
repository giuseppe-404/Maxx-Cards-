package model;

import java.io.Serializable;
import java.sql.Date;

public class CSetBean implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	private int id;
	private String nome;
	private Date releaseDate;
	
	public CSetBean(){};
	
	public CSetBean(int id, String nome, Date releaseDate) {
		this.id = id;
		this.nome = nome;
		this.releaseDate = releaseDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
}
