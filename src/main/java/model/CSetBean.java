package model;

import java.io.Serializable;
import java.sql.Date;

public class CSetBean implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	private String nome;
	private Date releaseDate;
	
	public CSetBean(){};
	
	public CSetBean(String nome, Date releaseDate) {
		this.nome = nome;
		this.releaseDate = releaseDate;
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
