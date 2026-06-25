package dao;

import java.sql.SQLException;
import java.util.List;

import model.InfoSpedBean;

public interface InfoSpedDao{
	
	public boolean saveInfoSped(InfoSpedBean info) throws SQLException;
	
	public InfoSpedBean retrieveByKey(int id, int idUtente) throws SQLException;
	
	public List<InfoSpedBean> retrieveByIdUtente(int idUtente) throws SQLException;
	
	public boolean deleteByKey(int id, int idUtente) throws SQLException;
	
	public boolean deleteByIdUtente(int idUtente) throws SQLException;
	
	public boolean updateNome(int id, int idUtente, String name) throws SQLException;
	
	public boolean updateCognome(int id, int idUtente, String surname) throws SQLException;
	
	public boolean updateVia(int id, int idUtente, String Via) throws SQLException;

	public boolean updateCivico(int id, int idUtente, int civico) throws SQLException;

	public boolean updateCAP(int id, int idUtente, int cap) throws SQLException;

}
