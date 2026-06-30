package model;

public class ProdottoYGOBean extends ProdottoBean {
	private static final long serialVersionUID = 1l;
	private String lingua;

	public ProdottoYGOBean() {
		super();
		this.lingua = "";
	}
	
	public ProdottoYGOBean(int id, String nome, int qnt, int prezzo, String descrizione, int sconto, String pathImg,
			String mimeType, String lingua) {
		super(id, nome, qnt, prezzo, descrizione, sconto, pathImg, mimeType);
		this.lingua = lingua;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	
	
	
}
