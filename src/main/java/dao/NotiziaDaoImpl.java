package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.NotiziaBean;

public class NotiziaDaoImpl implements NotiziaDao {

	private static final String TABLE_NAME = "notizia";
	private DataSource ds;
	
	public NotiziaDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean saveNotizia(NotiziaBean notizia) throws SQLException {
		String sql = "INSERT INTO "+TABLE_NAME+"(id,titolo,corpo) VALUES (?,?,?)";
		try ( Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, notizia.getId());
			ps.setString(2, notizia.getTitolo());
			ps.setString(3, notizia.getCorpo());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean deleteNotizia(int id) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			return result != 0;
		}
	}

	@Override
	public synchronized List<NotiziaBean> retrieveAll(int limit, int page) throws SQLException {
		ArrayList<NotiziaBean> list = new ArrayList<NotiziaBean>();
		String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC LIMIT " + limit + " OFFSET " + page*limit;
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					NotiziaBean notizia = new NotiziaBean();
					fillBean(notizia, rs);
					list.add(notizia);
				}
			}
		}
		return list;
	}

	@Override
	public synchronized List<NotiziaBean> retrieveAll() throws SQLException {
		ArrayList<NotiziaBean> list = new ArrayList<NotiziaBean>();
		String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC";
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					NotiziaBean notizia = new NotiziaBean();
					fillBean(notizia, rs);
					list.add(notizia);
				}
			}
		}
		return list;
	}

	@Override
	public synchronized boolean changeTitolo(NotiziaBean notizia) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET titolo = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, notizia.getTitolo());
        	ps.setInt(2, notizia.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public boolean changeCorpo(NotiziaBean notizia) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET corpo = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, notizia.getCorpo());
        	ps.setInt(2, notizia.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}
	
	public void fillBean(NotiziaBean nb, ResultSet rs) throws SQLException {
		nb.setId(rs.getInt("id"));
		nb.setTitolo(rs.getString("titolo"));
		nb.setCorpo(rs.getString("corpo"));
	}

}
