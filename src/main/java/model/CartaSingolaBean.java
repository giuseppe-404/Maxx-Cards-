package model;

public class CartaSingolaBean extends ProdottoYGOBean{
	private static final long serialVersionUID = 1l;
	private String quality;
	private int id;
	private int idSet;
	private int idCarta;
	
	public CartaSingolaBean(){
		super();
		quality="";
	}
	
	public CartaSingolaBean(int idP, String nome, int qnt, int prezzo, String descrizione, int sconto, String pathImg,
			String mimeType, String lingua, String quality, int id, int idSet, int idCarta) {
		super(idP, nome, qnt, prezzo, descrizione, sconto, pathImg, mimeType, lingua);
		this.quality = quality;
		this.id = id;
		this.idSet = idSet;
		this.idCarta = idCarta;
	}
	
	public String getQuality() {
		return quality;
	}
	
	public void setQuality(String quality) {
		this.quality = quality;
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

	public int getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(int idCarta) {
		this.idCarta = idCarta;
	}
	
	
	
	
}
