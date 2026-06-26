package dao;

import java.sql.SQLException;
import java.util.List;

import model.MagiaBean;

public interface MagiaDao extends CartaDao{

	public boolean saveMagia(MagiaBean magia) throws SQLException;
	
	public boolean deleteMagia(int id) throws SQLException;
	
	public MagiaBean retrieveByKey(int id) throws SQLException;
	
	public List<MagiaBean> retrieveFiltered(MagiaBean magia) throws SQLException;
		
	public List<MagiaBean> retrieveFiltered(MagiaBean magia, int limit, int page) throws SQLException;
		
	public boolean changeTipologia(MagiaBean magia) throws SQLException;
}
