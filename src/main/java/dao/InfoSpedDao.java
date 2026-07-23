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
	
	public boolean changeNome(InfoSpedBean info) throws SQLException;
	
	public boolean changeCognome(InfoSpedBean info) throws SQLException;
	
	public boolean changeVia(InfoSpedBean info) throws SQLException;

	public boolean changeCivico(InfoSpedBean info) throws SQLException;

	public boolean changeCAP(InfoSpedBean info) throws SQLException;

}
