package dao;

import java.sql.SQLException;
import java.util.List;
import model.ProdottoCompratoBean;

public interface ProdottoCompratoDao {
	
	public boolean saveProdottoComprato(ProdottoCompratoBean prodotto) throws SQLException;
	
	public ProdottoCompratoBean retrieveByKey(int id, int idOrdine) throws SQLException;
	
	public List<ProdottoCompratoBean> retrieveByIdOrdine(int idOrdine) throws SQLException;
	
	public boolean deleteProdottoComprato(int id, int idOrdine) throws SQLException;
	
	public boolean deleteProdottoCompratoByIdOrdine(int idOrdine) throws SQLException;
	
	public boolean changeIdOriginale(ProdottoCompratoBean prodotto) throws SQLException;
	
	public boolean changePrezzo(ProdottoCompratoBean prodotto) throws SQLException;
	
	public boolean changeNome(ProdottoCompratoBean prodotto) throws SQLException;
	
	public boolean changeQnt(ProdottoCompratoBean prodotto) throws SQLException;
	
	public boolean changeInfo(ProdottoCompratoBean prodotto) throws SQLException;
	
}
