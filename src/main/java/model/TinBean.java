package model;

public class TinBean extends ConfezionatoBean {
	private static final long serialVersionUID = 1l;
	
	public TinBean(int id, String nome, int qnt, int prezzo, String descrizione, int sconto, String pathImg,
			String mimeType, String lingua, int idSet) {
		super(id, nome, qnt, prezzo, descrizione, sconto, pathImg, mimeType, lingua,idSet);
	}
	
	public TinBean() {
		super();
	}
}
