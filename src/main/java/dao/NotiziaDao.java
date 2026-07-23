package dao;

import java.sql.SQLException;
import java.util.List;

import model.NotiziaBean;

public interface NotiziaDao {
	
	public boolean saveNotizia(NotiziaBean notizia) throws SQLException;
	
	public boolean deleteNotizia(int id) throws SQLException;
	
	public List<NotiziaBean> retrieveAll(int limit, int page) throws SQLException;
	
	public List<NotiziaBean> retrieveAll() throws SQLException;
	
	public boolean changeTitolo(NotiziaBean notizia) throws SQLException;
	
	public boolean changeCorpo(NotiziaBean notizia) throws SQLException;
	
}
