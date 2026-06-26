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
	
	public boolean changeIdOriginale(int id, int idOrdine, int idOriginale) throws SQLException;
	
	public boolean changePrezzo(int id, int idOrdine, int prezzo) throws SQLException;
	
	public boolean changeNome(int id, int idOrdine, String nome) throws SQLException;
	
	public boolean changeQnt(int id, int idOrdine, int qnt) throws SQLException;
	
	public boolean changeInfo(int id, int idOrdine, String info) throws SQLException;
	
}
