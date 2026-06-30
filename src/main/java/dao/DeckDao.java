package dao;

import java.sql.SQLException;
import java.util.List;

import model.DeckBean;

public interface DeckDao extends ProdottoYGODao{
	
	public boolean saveDeck(DeckBean deck) throws SQLException;
	
	public boolean deleteDeck(int id) throws SQLException;
	
	public DeckBean retrieveByKey(int id) throws SQLException;
	
	public List<DeckBean> retrieveFiltered(DeckBean deck) throws SQLException;
	
	public List<DeckBean> retrieveFiltered(DeckBean deck, int page, int limit) throws SQLException;
	
}
