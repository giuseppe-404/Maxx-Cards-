package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import model.CSetBean;

public interface CSetDao {
	
	public void saveCSet(CSetBean set) throws SQLException;
	
	public CSetBean retrieveByKey(int id) throws SQLException;
	
	public boolean deleteCSet(int id) throws SQLException;
	
	public boolean changeNome(CSetBean set) throws SQLException;
	
	public boolean changeReleaseDate(CSetBean set) throws SQLException;
	
	public List<CSetBean> retrieveAll() throws SQLException;
	
	public List<CSetBean> retrieveAll(int page, int limit) throws SQLException;
	
	public List<CSetBean> retrieveFiltered(CSetBean set, Date data) throws SQLException;
	
	public List<CSetBean> retrieveFiltered(CSetBean set, Date data, int page, int limit) throws SQLException;
}
