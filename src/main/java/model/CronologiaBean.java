package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CronologiaBean implements Serializable{
	private static final long serialVersionUID = 1l;
	private int idUtente;
	private int idTarget;
	private LocalDateTime dataVisita;
	private boolean prodotto;
	
	public CronologiaBean() {}
	
	public CronologiaBean(int idUtente, int idTarget, boolean isProdotto, LocalDateTime dataVisita) {
		this.idTarget = idTarget;
		this.idUtente = idUtente;
		this.dataVisita = dataVisita;
		this.prodotto = isProdotto;
	}
	
	/**
	 * @return the dataVisita
	 */
	public LocalDateTime getDataVisita() {
		return dataVisita;
	}

	/**
	 * @param dataVisita the dataVisita to set
	 */
	public void setDataVisita(LocalDateTime dataVisita) {
		this.dataVisita = dataVisita;
	}

	/**
	 * @return the isProdotto
	 */
	public boolean isProdotto() {
		return prodotto;
	}

	/**
	 * @param isProdotto the isProdotto to set
	 */
	public void setProdotto(boolean isProdotto) {
		this.prodotto = isProdotto;
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
