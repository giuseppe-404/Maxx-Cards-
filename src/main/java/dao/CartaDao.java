package dao;

import java.sql.SQLException;
import java.util.List;

import model.CartaBean;

public interface CartaDao {

	public void SaveCarta(CartaBean carta) throws SQLException;
	
	public boolean DeleteCarta(int id) throws SQLException;
	
	public CartaBean RetriveByKey(int code) throws SQLException;
	
	public List<CartaBean> RetrieveAll() throws SQLException;
	
	public List<CartaBean> RetrieveAll(int length, int limit, int page) throws SQLException;
	
	public List<CartaBean> RetriveFiltered(String nome, String testo, int punteggio) throws SQLException;
	
	public List<CartaBean> RetriveFiltered(String nome, String testo, int punteggio, int length, int limit, int page) throws SQLException;
	
	public boolean ChangeId(CartaBean carta, int originalId) throws SQLException;
	
	public boolean ChangePunteggio(CartaBean carta) throws SQLException;

	public boolean ChangeNomeIt(CartaBean carta) throws SQLException;
	
	public boolean ChangeNomeEn(CartaBean carta) throws SQLException;
	
	public boolean ChangeNomeJp(CartaBean carta) throws SQLException;

	public boolean ChangeTesto(CartaBean carta) throws SQLException;

	public boolean ChangeImage(CartaBean carta) throws SQLException;
}
