package model;

public class ConfezionatoBean extends ProdottoYGOBean{
	private static final long serialVersionUID = 1l;
	private int id;
	private int idSet;
	
	public ConfezionatoBean() {
		super();
	}
	
	public ConfezionatoBean(int idP, String nome, int qnt, int prezzo, String descrizione, int sconto, String pathImg,
			String mimeType, String lingua,int id, int idSet) {
		super(idP, nome, qnt, prezzo, descrizione, sconto, pathImg, mimeType, lingua);
		this.id = id;
		this.idSet = idSet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdSet() {
		return idSet;
	}

	public void setIdSet(int idSet) {
		this.idSet = idSet;
	}
	
	
}
