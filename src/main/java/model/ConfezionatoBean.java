package model;

public class ConfezionatoBean extends ProdottoYGOBean{
	private static final long serialVersionUID = 1l;
	private int idSet;
	
	public ConfezionatoBean() {
		super();
	}
	
	public ConfezionatoBean(int id, String nome, int qnt, int prezzo, String descrizione, int sconto, String pathImg,
			String mimeType, String lingua, int idSet) {
		super(id, nome, qnt, prezzo, descrizione, sconto, pathImg, mimeType, lingua);
		this.idSet = idSet;
	}

	public int getIdSet() {
		return idSet;
	}

	public void setIdSet(int idSet) {
		this.idSet = idSet;
	}
	
	
}
