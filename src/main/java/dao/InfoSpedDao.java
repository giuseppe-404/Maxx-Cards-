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
	
	public boolean updateNome(InfoSpedBean info) throws SQLException;
	
	public boolean updateCognome(InfoSpedBean info) throws SQLException;
	
	public boolean updateVia(InfoSpedBean info) throws SQLException;

	public boolean updateCivico(InfoSpedBean info) throws SQLException;

	public boolean updateCAP(InfoSpedBean info) throws SQLException;

}
