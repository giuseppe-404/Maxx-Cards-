package dao;

import java.sql.SQLException;
import java.util.List;

import model.ContieneDeckBean;

public interface ContieneDeckDao {
	
	public boolean saveContieneDeck(ContieneDeckBean cont) throws SQLException;
	
	public boolean deleteContieneDeck(int idDeck, int idCarta) throws SQLException;
	
	public boolean changeQnt(ContieneDeckBean cont) throws SQLException;
	
	public List<ContieneDeckBean> retrieveByIdDeck(int idDeck) throws SQLException;

}
