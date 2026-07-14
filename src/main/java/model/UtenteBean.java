package model;

import java.io.Serializable;

public class UtenteBean implements Serializable{
	private static final long serialVersionUID = 1l;

	private int id;
	private byte[] salt;
	private String email;
	private String pwd;
	private boolean admin;
	
	public UtenteBean() {
	}

	
	
	public UtenteBean(int id, byte[] salt, String email, String pwd) {
		super();
		this.id = id;
		this.salt = salt;
		this.email = email;
		this.pwd = pwd;
		this.admin = false;
	}

	public UtenteBean(int id, byte[] salt, String email, String pwd, boolean admin) {
		super();
		this.id = id;
		this.salt = salt;
		this.email = email;
		this.pwd = pwd;
		this.admin = admin;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean isAdmin() {
		return admin;
	}



	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
}
