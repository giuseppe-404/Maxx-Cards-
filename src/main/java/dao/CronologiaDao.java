package dao;

import java.sql.SQLException;
import java.util.List;

import model.CronologiaBean;

public interface CronologiaDao {
	
	public boolean saveCronologia(CronologiaBean cronologia) throws SQLException;
	
	public List<CronologiaBean> retrieveByIdUtente(int idUtente) throws SQLException; 
	
	public void deleteByIdUtente(int idUtente) throws SQLException;
}
