package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.CSetBean;

public class CSetDaoImpl implements CSetDao {
	
	private static final String TABLE_NAME = "CSet";
	private DataSource ds = null;

	public CSetDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public synchronized void saveCSet(CSetBean set) throws SQLException {
		String sql = "INSERT INTO "+TABLE_NAME+"(id,nome,release_date) VALUES (?,?,?);";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, set.getId());
			ps.setString(2, set.getNome());
			ps.setDate(3, set.getReleaseDate());
			ps.executeUpdate();
		}
	}

	@Override
	public synchronized CSetBean retrieveCSet(int id) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=?;";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				CSetBean set = new CSetBean(rs.getInt(1),rs.getString(2),rs.getDate(3));
				return set;
			} return null;
		}
	}
	
	public synchronized List<CSetBean> retrieveAll() throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+";";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			List<CSetBean> list = new ArrayList<>();
			if(rs.next()) {
				CSetBean set = new CSetBean(rs.getInt(1),rs.getString(2),rs.getDate(3));
				list.add(set);
			} return list;
		}
	}
	
	public synchronized List<CSetBean> retrieveAll(int page, int limit) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+"LIMIT "+limit+" OFFSET "+page*limit+";";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			List<CSetBean> list = new ArrayList<>();
			if(rs.next()) {
				CSetBean set = new CSetBean(rs.getInt(1),rs.getString(2),rs.getDate(3));
				list.add(set);
			} return list;
		}
	}

	public synchronized List<CSetBean> retrieveFiltered(CSetBean set, Date data) throws SQLException{
		StringBuilder sql = new StringBuilder("SELECT * FROM "+TABLE_NAME+" ");
		boolean primo = true;
		ArrayList<String> attributi = new ArrayList<>();
		if(!set.getNome().equals("")) {
			if(primo) {
				sql.append("WHERE ");
				primo = false;
			}else sql.append(" AND ");
			sql.append(" nome LIKE ? ");
			attributi.add("%"+set.getNome()+"%");
		}
		if((set.getReleaseDate()!=null)) {
			if(primo) {
				sql.append("WHERE ");
				primo = false;
			}else sql.append(" AND ");
			sql.append(" release_date <= ?");
			attributi.add(set.getReleaseDate().toString());
		}
		if((data!=null)) {
			if(primo) {
				sql.append("WHERE ");
				primo = false;
			}else sql.append(" AND ");
			sql.append(" release_date >= ?");
			attributi.add(set.getReleaseDate().toString());
		}
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())) {
				for (int i = 0; i < attributi.size(); i++) {
					ps.setString(i + 1, attributi.get(i));
				}
				ResultSet rs = ps.executeQuery();
				List<CSetBean> list = new ArrayList<>();
				while(rs.next()) {
					CSetBean temp = new CSetBean(rs.getInt(1),rs.getString(2),rs.getDate(3));
					list.add(temp);
				}
				return list;
		}
		
	}
	
	public synchronized List<CSetBean> retrieveFiltered(CSetBean set, Date data,int page,int limit) throws SQLException{
		StringBuilder sql = new StringBuilder("SELECT * FROM "+TABLE_NAME+" ");
		boolean primo = true;
		ArrayList<String> attributi = new ArrayList<>();
		if(!set.getNome().equals("")) {
			if(primo) {
				sql.append("WHERE ");
				primo = false;
			}else sql.append(" AND ");
			sql.append(" nome LIKE ? ");
			attributi.add("%"+set.getNome()+"%");
		}
		if((set.getReleaseDate()!=null)) {
			if(primo) {
				sql.append("WHERE ");
				primo = false;
			}else sql.append(" AND ");
			sql.append(" release_date <= ?");
			attributi.add(set.getReleaseDate().toString());
		}
		if((data!=null)) {
			if(primo) {
				sql.append("WHERE ");
				primo = false;
			}else sql.append(" AND ");
			sql.append(" release_date >= ?");
			attributi.add(set.getReleaseDate().toString());
		}
		sql.append("LIMIT "+limit+" OFFSET "+page*limit+" ;");
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())) {
				for (int i = 0; i < attributi.size(); i++) {
					ps.setString(i + 1, attributi.get(i));
				}
				ResultSet rs = ps.executeQuery();
				List<CSetBean> list = new ArrayList<>();
				while(rs.next()) {
					CSetBean temp = new CSetBean(rs.getInt(1),rs.getString(2),rs.getDate(3));
					list.add(temp);
				}
				return list;
		}
	}

	@Override
	public synchronized boolean deleteCSet(int id) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0;
		}
	}

	@Override
	public synchronized boolean changeNome(CSetBean set) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET nome=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, set.getNome());
			ps.setInt(2, set.getId());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeReleaseDate(CSetBean set) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET releaseDate=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setDate(1, set.getReleaseDate());
			ps.setInt(2, set.getId());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

}
