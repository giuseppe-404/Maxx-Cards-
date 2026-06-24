package dao;

import java.sql.SQLException;
import java.util.List;

import model.InfoSpedBean;

public interface InfoSpedDao{
	
	public void saveInfoSped(InfoSpedBean info) throws SQLException;
	
	public InfoSpedBean retrieveInfoSpedByKey(int id, int idUtente) throws SQLException;
	
	public List<InfoSpedBean> retrieveInfoSpedByIdUtente(int idUtente) throws SQLException;
	
	public boolean deleteInfoSpedByKey(int id, int idUtente) throws SQLException;
	
	public boolean deleteInfoSpedByIdUtente(int idUtente) throws SQLException;
	
	public boolean updateInfoSpedNome(int id, int idUtente, String name) throws SQLException;
	
	public boolean updateInfoSpedCognome(int id, int idUtente, String surname) throws SQLException;
	
	public boolean updateInfoSpedVia(int id, int idUtente, String Via) throws SQLException;

	public boolean updateInfoSpedCivico(int id, int idUtente, int civico) throws SQLException;

	public boolean updateInfoSpedNameCAP(int id, int idUtente, int cap) throws SQLException;

}
