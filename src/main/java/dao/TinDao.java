package dao;

import java.sql.SQLException;
import java.util.List;

import model.TinBean;

public interface TinDao extends ConfezionatoDao {
	
	public boolean saveTin(TinBean tin) throws SQLException;
	
	public boolean deleteTin(int id) throws SQLException;
	
	public TinBean retrieveByKey(int id) throws SQLException;
	
	public List<TinBean> retrieveFiltered(TinBean tin) throws SQLException;
	
	public List<TinBean> retrieveFiltered(TinBean tin, int page, int limit) throws SQLException;
}
