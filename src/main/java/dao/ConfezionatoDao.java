package dao;

import java.sql.SQLException;
import java.util.List;

import model.ConfezionatoBean;

public interface ConfezionatoDao extends ProdottoYGODao{
	
	public boolean saveConfezionato(ConfezionatoBean confezionato) throws SQLException;
	
	public boolean deleteConfezionato(int id) throws SQLException;
	
	public ConfezionatoBean retrieveByKey(int id ) throws SQLException;
	
	public List<ConfezionatoBean> retrieveFiltered(ConfezionatoBean confezionato) throws SQLException;
	
	public List<ConfezionatoBean> retrieveFiltered(ConfezionatoBean confezionato, int page, int limit) throws SQLException;
	
	public boolean changeIdSet(ConfezionatoBean confezionato) throws SQLException;
}
