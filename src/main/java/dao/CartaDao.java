package dao;

import java.sql.SQLException;
import java.util.List;

import model.CartaBean;

public interface CartaDao {

	public boolean saveCarta(CartaBean carta) throws SQLException;
	
	public boolean deleteCarta(int id) throws SQLException;
	
	public CartaBean retrieveByKey(int id) throws SQLException;
	
	public List<CartaBean> retrieveAll() throws SQLException;
	
	public List<CartaBean> retrieveAll(int limit, int page) throws SQLException;
	
	public List<CartaBean> retrieveFiltered(CartaBean carta) throws SQLException;
	
	public List<CartaBean> retrieveFiltered(CartaBean carta, int limit, int page) throws SQLException;
	
	public boolean changeId(CartaBean carta, int originalId) throws SQLException;
	
	public boolean changePunteggio(CartaBean carta) throws SQLException;

	public boolean changeNomeIt(CartaBean carta) throws SQLException;
	
	public boolean changeNomeEn(CartaBean carta) throws SQLException;
	
	public boolean changeNomeJp(CartaBean carta) throws SQLException;

	public boolean changeTesto(CartaBean carta) throws SQLException;

	public boolean changeImage(CartaBean carta) throws SQLException;
}
