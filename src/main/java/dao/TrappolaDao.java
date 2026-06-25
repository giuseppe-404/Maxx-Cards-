package dao;

import java.sql.SQLException;
import java.util.List;

import model.TrappolaBean;

public interface TrappolaDao extends CartaDao{

	public void saveTrappola(TrappolaBean trappola) throws SQLException;
	
	public boolean deleteTrappola(int id) throws SQLException;
	
	public TrappolaBean retriveByKey(int code) throws SQLException;
	
	public List<TrappolaBean> retriveFiltered(TrappolaBean trappola) throws SQLException;
		
	public List<TrappolaBean> retriveFiltered(TrappolaBean trappola, int limit, int page) throws SQLException;
		
	public boolean changeTipologia(TrappolaBean trappola) throws SQLException;
}
