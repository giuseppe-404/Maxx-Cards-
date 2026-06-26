package dao;

import java.sql.SQLException;
import java.util.List;

import model.ProdottoBean;

public interface ProdottoDao {
	
	public void saveProdotto(ProdottoBean prodotto) throws SQLException;
	
	public ProdottoBean retrieveByKey(int idProdotto) throws SQLException;
	
	public List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto)throws SQLException; 
	
	public List<ProdottoBean> retrieveAll(int page, int limit) throws SQLException;
	
	public List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto, int page, int limit)throws SQLException; 
	
	public List<ProdottoBean> retrieveAll() throws SQLException;
	
	public boolean deleteProdotto(int idProdotto) throws SQLException;
	
	public boolean changeNome(int idProdotto, String nome) throws SQLException;
	
	public boolean changeQnt(int idProdotto, int qnt) throws SQLException;
	
	public boolean changePrezzo(int idProdotto, int prezzo) throws SQLException;
	
	public boolean changeDescrizione(int idProdotto, String descrizione) throws SQLException;
	
	public boolean changeSconto(int idProdotto, int sconto) throws SQLException;
	
	public boolean changeImage(int idProdotto, String path, String mime) throws SQLException;
	
}
