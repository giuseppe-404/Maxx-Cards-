package dao;

import java.sql.SQLException;
import java.util.List;

import model.WantsBean;

public interface WantsDao {
	
	public boolean saveWants(WantsBean wants) throws SQLException;
	
	public boolean deleteWants(int idUser, int idProd) throws SQLException;
	
	public boolean deleteByIdUser(int idUser) throws SQLException;
	
	public List<WantsBean> retrieveByIdUtente(int idUser) throws SQLException;
	
	public WantsBean retrieveByKey(int idUser, int idProdotto) throws SQLException;
}
