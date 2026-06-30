package dao;

import java.sql.SQLException;
import java.util.List;
import model.ProdottoYGOBean;

public interface ProdottoYGODao extends ProdottoDao{
	
	public boolean saveProdottoYGO (ProdottoYGOBean prodotto) throws SQLException;
	
	public boolean deleteProdottoYGO (int id) throws SQLException;
	
	public ProdottoYGOBean retrieveByKey(int id) throws SQLException;
	
	public List<ProdottoYGOBean> retrieveFiltered(ProdottoYGOBean prodotto) throws SQLException;
	
	public List<ProdottoYGOBean> retrieveFiltered(ProdottoYGOBean prodotto, int page, int limi) throws SQLException;
	
	public boolean changeLingua(ProdottoYGOBean prodotto) throws SQLException;
	
}
