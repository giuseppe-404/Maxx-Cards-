package dao;

import java.sql.SQLException;
import java.util.List;

import model.CartaSingolaBean;

public interface CartaSingolaDao extends ProdottoYGODao{
	
	public boolean saveCartaSingola(CartaSingolaBean carta) throws SQLException;
	
	public boolean deleteCartaSingola(int id) throws SQLException;
	
	public CartaSingolaBean retrieveByKey(int id) throws SQLException;
	
	public List<CartaSingolaBean> retrieveFiltered(CartaSingolaBean carta) throws SQLException;
	
	public List<CartaSingolaBean> retrieveFiltered(CartaSingolaBean carta, int page, int limit) throws SQLException;
	
	public boolean changeQuality(CartaSingolaBean carta) throws SQLException;
	
	public boolean changeIdSet(CartaSingolaBean carta) throws SQLException;
	
	public boolean changeIdCarta(CartaSingolaBean carta) throws SQLException;
}
