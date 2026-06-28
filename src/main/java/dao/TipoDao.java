package dao;

import java.sql.SQLException;
import java.util.List;

import model.TipoBean;

public interface TipoDao {

	public void saveTipo(TipoBean tipo) throws SQLException;
	
	public boolean deleteTipo(String id) throws SQLException;
	
	public TipoBean retrieveByKey(String id) throws SQLException;
	
	public List<TipoBean> retrieveAll() throws SQLException;
	
	public boolean changeTipo(TipoBean tipo, String originalId) throws SQLException;
}
