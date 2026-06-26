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
	
	public boolean updateIdOriginale(int id, int idOrdine, int idOriginale) throws SQLException;
	
	public boolean updatePrezzo(int id, int idOrdine, int prezzo) throws SQLException;
	
	public boolean updateNome(int id, int idOrdine, String nome) throws SQLException;
	
	public boolean updateQnt(int id, int idOrdine, int qnt) throws SQLException;
	
	public boolean updateInfo(int id, int idOrdine, String info) throws SQLException;
	
}
