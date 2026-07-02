package model;


public class DeckBean extends ProdottoYGOBean {
	private static final long serialVersionUID = 1l;
	
	public DeckBean(int id, String nome, int qnt, int prezzo, String descrizione, int sconto, String pathImg,
			String mimeType, String lingua) {
		super(id, nome, qnt, prezzo, descrizione, sconto, pathImg, mimeType,lingua);
	}
	
	public DeckBean() {}
	
}
