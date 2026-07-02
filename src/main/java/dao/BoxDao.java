package dao;

import java.sql.SQLException;
import java.util.List;

import model.BoxBean;

public interface BoxDao extends ConfezionatoDao {
	
	public boolean saveProdotto(BoxBean box) throws SQLException;
	
	public boolean saveProdottoYGO(BoxBean box) throws SQLException;
	
	public boolean saveConfezionato(BoxBean box) throws SQLException;

	public boolean saveBox(BoxBean box) throws SQLException;
	
	public boolean deleteBox(int id) throws SQLException;
	
	public BoxBean retrieveByKey(int id) throws SQLException;
	
	public List<BoxBean> retrieveFiltered(BoxBean box) throws SQLException;
	
	public List<BoxBean> retrieveFiltered(BoxBean box, int page, int limit) throws SQLException;
}
