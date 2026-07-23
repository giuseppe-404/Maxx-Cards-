package dao;

import java.sql.SQLException;
import java.util.List;

import model.ProdottiHomeBean;

public interface ProdottiHomeDao {
	
	public boolean saveProdottiHome(ProdottiHomeBean prod) throws SQLException;
	
	public boolean deleteProdottiHome(int id) throws SQLException;
	
	public List<Integer> retrieveAll() throws SQLException; 
	
}