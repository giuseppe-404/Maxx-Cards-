package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import model.UtenteBean;

public class UtenteDaoImpl implements UtenteDao{
	private static final String TABLE_NAME = "utente";
	private DataSource ds = null;
	
	public UtenteDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public synchronized boolean createUtente(UtenteBean utente) throws SQLException {
		String sql = "INSERT INTO "+ TABLE_NAME + "(id,salt,email,pwd,dark_theme) "
				+ "values (?,?,?,?,?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,utente.getId());
			ps.setBytes(2,utente.getSalt());
			ps.setString(3,utente.getEmail());
			ps.setString(4, utente.getPwd());
			ps.setBoolean(5, utente.isDarkTheme());
			int rowAdded = ps.executeUpdate();
			return rowAdded != 0;
		}		
	}

	@Override
	public synchronized UtenteBean retrieveByEmail(String email) throws SQLException {
		String sql = "SELECT * from "+TABLE_NAME+" where email=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				UtenteBean utente = new UtenteBean(rs.getInt(1),rs.getBytes(2),rs.getString(3),rs.getString(4),rs.getBoolean(5));
				return utente;
				
			}
			return null;
		}
	}

	@Override
	public synchronized boolean changeTheme(int id, boolean darkTheme) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET dark_theme=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setBoolean(1, darkTheme);
			ps.setInt(2, id);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated!=0;
		}
	}

	@Override
	public synchronized boolean deleteAccount(int id) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted!=0;
		}
	}
	
	public synchronized boolean changePassword(int id,String pwd) throws SQLException {
		String sql = "UPDATE "+ TABLE_NAME +" SET pwd=? where id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, pwd);
			ps.setInt(2, id);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated!=0;
		}
	}

	@Override
	public synchronized boolean makeAdmin(String email, boolean isAdmin) throws SQLException {
		String sql = "UPDATE "+ TABLE_NAME + "SET admin=? where email=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setBoolean(1, isAdmin);
			ps.setString(2,email);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated!=0;
		}
}

	@Override
	public synchronized List<UtenteBean> retrieveAll() throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+";";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			List<UtenteBean> list = new ArrayList<>();
			while(rs.next()) {
				UtenteBean utente = new UtenteBean(rs.getInt(1),rs.getBytes(2),rs.getString(3),rs.getString(4),rs.getBoolean(5));
				list.add(utente);
			}
			return list;
		}
	}

	@Override
	public synchronized List<UtenteBean> retrieveAll(int page, int limit) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" LIMIT "+limit+" OFFSET "+page*limit+";";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			List<UtenteBean> list = new ArrayList<>();
			while(rs.next()) {
				UtenteBean utente = new UtenteBean(rs.getInt(1),rs.getBytes(2),rs.getString(3),rs.getString(4),rs.getBoolean(5));
				list.add(utente);
			}
			return list;
		}
	}

	@Override
	public synchronized List<UtenteBean> retrieveAmministratore() throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE amministratore=true";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			List<UtenteBean> list = new ArrayList<>();
			while(rs.next()) {
				UtenteBean utente = new UtenteBean(rs.getInt(1),rs.getBytes(2),rs.getString(3),rs.getString(4),rs.getBoolean(5));
				list.add(utente);
			}
			return list;
		}
	}

	@Override
	public synchronized List<UtenteBean> retrieveAmministratore(int page, int limit) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE amministratore=true LIMIT "+limit+" OFFSET "+page*limit+";";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			List<UtenteBean> list = new ArrayList<>();
			while(rs.next()) {
				UtenteBean utente = new UtenteBean(rs.getInt(1),rs.getBytes(2),rs.getString(3),rs.getString(4),rs.getBoolean(5));
				list.add(utente);
			}
			return list;
		}
	}
	
	
	
}
