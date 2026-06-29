package dao;

import java.sql.SQLException;
import java.util.List;

import model.PacchettoBean;

public interface PacchettoDao extends ConfezionatoDao {
	
	public boolean savePacchetto(PacchettoBean pack) throws SQLException;
	
	public boolean deletePacchetto(int id) throws SQLException;
	
	public PacchettoBean retrieveByKey(int id) throws SQLException;
	
	public List<PacchettoBean> retrieveFiltered(PacchettoBean pack) throws SQLException;
	
	public List<PacchettoBean> retrieveFiltered(PacchettoBean pack, int page, int limit) throws SQLException;
	
}
