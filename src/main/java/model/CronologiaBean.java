package model;

import java.io.Serializable;

public class CronologiaBean implements Serializable{
	private static final long serialVersionUID = 1l;
	private int idUtente;
	private int idTarget;
	private boolean isProdotto;
	
	public CronologiaBean() {}
	
	public CronologiaBean(int idUtente, int idTarget, boolean isProdotto) {
		this.idTarget = idTarget;
		this.idUtente = idUtente;
		this.isProdotto = isProdotto;
	}
	
	/**
	 * @return the isProdotto
	 */
	public boolean isProdotto() {
		return isProdotto;
	}

	/**
	 * @param isProdotto the isProdotto to set
	 */
	public void setProdotto(boolean isProdotto) {
		this.isProdotto = isProdotto;
	}

	/**
	 * @return the idUtente
	 */
	public int getIdUtente() {
		return idUtente;
	}

	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	/**
	 * @return the idTarget
	 */
	public int getIdTarget() {
		return idTarget;
	}

	/**
	 * @param idTarget the idTarget to set
	 */
	public void setIdTarget(int idTarget) {
		this.idTarget = idTarget;
	}
	
	
}
