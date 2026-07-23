package model;

public class CartaSingolaBean extends ProdottoYGOBean{
	private static final long serialVersionUID = 1l;
	private String quality;
	private String idSet;
	private int idCarta;
	
	public CartaSingolaBean(){
		super();
		quality = "";
		idSet = "";
	}
	
	public CartaSingolaBean(int idP, String nome, int qnt, int prezzo, String descrizione, int sconto, String pathImg,
			String mimeType, String lingua, String quality, String idSet, int idCarta) {
		super(idP, nome, qnt, prezzo, descrizione, sconto, pathImg, mimeType, lingua);
		this.quality = quality;
		this.idSet = idSet;
		this.idCarta = idCarta;
	}
	
	public String getQuality() {
		return quality;
	}
	
	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	public String getIdSet() {
		return idSet;
	}
	
	public void setIdSet(String idSet) {
		this.idSet = idSet;
	}

	public int getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(int idCarta) {
		this.idCarta = idCarta;
	}
	
	
	
	
}
