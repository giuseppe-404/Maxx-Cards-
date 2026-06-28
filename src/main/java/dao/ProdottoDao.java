package dao;

import java.sql.SQLException;
import java.util.List;

import model.ProdottoBean;

public interface ProdottoDao {
	
	public boolean saveProdotto(ProdottoBean prodotto) throws SQLException;
	
	public ProdottoBean retrieveByKey(int idProdotto) throws SQLException;
	
	public List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto)throws SQLException; 
	
	public List<ProdottoBean> retrieveAll(int page, int limit) throws SQLException;
	
	public List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto, int page, int limit)throws SQLException; 
	
	public List<ProdottoBean> retrieveAll() throws SQLException;
	
	public boolean deleteProdotto(int idProdotto) throws SQLException;
	
	public boolean changeNome(ProdottoBean prodotto) throws SQLException;
	
	public boolean changeQnt(ProdottoBean prodotto) throws SQLException;
	
	public boolean changePrezzo(ProdottoBean prodotto) throws SQLException;
	
	public boolean changeDescrizione(ProdottoBean prodotto) throws SQLException;
	
	public boolean changeSconto(ProdottoBean prodotto) throws SQLException;
	
	public boolean changeImage(ProdottoBean prodotto) throws SQLException;
	
}
