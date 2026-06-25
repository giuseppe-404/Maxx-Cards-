package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.InfoSpedBean;

public class InfoSpedDaoImpl implements InfoSpedDao{
	private static final String TABLE_NAME = "infoSped";
	private DataSource ds = null;	

	
	
	public InfoSpedDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public synchronized void saveInfoSped(InfoSpedBean info) throws SQLException{
		String sql = "INSERT INTO "+TABLE_NAME+"(id,id_utente,nome,cognome,via,civico,cap)"
				+ "values (?,?,?,?,?,?,?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps=connection.prepareStatement(sql)){
			ps.setInt(1,info.getId());
			ps.setInt(2, info.getIdUtente());
			ps.setString(3, info.getNome());
			ps.setString(4, info.getCognome());
			ps.setString(5, info.getVia());
			ps.setInt(6, info.getCivico());
			ps.setInt(7,info.getCap());
			ps.executeQuery();
		}
		
	}

	@Override
	public synchronized InfoSpedBean retrieveInfoSpedByKey(int id, int idUtente) throws SQLException{
		String sql = "SELECT * from "+TABLE_NAME+" where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.setInt(2, idUtente);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				InfoSpedBean info = new InfoSpedBean(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7));
				return info;
			} return null;
		}
	}

	@Override
	public synchronized List<InfoSpedBean> retrieveInfoSpedByIdUtente(int idUtente) throws SQLException{
		String sql = "SELECT * from "+TABLE_NAME+" where id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUtente);
			ResultSet rs = ps.executeQuery();
			List<InfoSpedBean> list = new ArrayList<InfoSpedBean>();
			while(rs.next()) {
				InfoSpedBean info = new InfoSpedBean(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7));
				list.add(info);
			} return list;
		}
	}

	@Override
	public synchronized boolean deleteInfoSpedByKey(int id, int idUtente) throws SQLException{
		String sql = "DELETE from "+TABLE_NAME+" where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.setInt(2,idUtente);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0; 
		}
	}

	@Override
	public synchronized boolean deleteInfoSpedByIdUtente(int idUtente) throws SQLException{
		String sql = "DELETE from "+TABLE_NAME+" where id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUtente);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0; 
		}
	}

	@Override
	public synchronized boolean updateInfoSpedNome(int id, int idUtente, String nome) throws SQLException{
		String sql = "UPDATE "+TABLE_NAME+" set nome=? where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1,nome);
			ps.setInt(2,id);
			ps.setInt(3,idUtente);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean updateInfoSpedCognome(int id, int idUtente, String cognome) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" set cognome=? where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, cognome);
			ps.setInt(2,id);
			ps.setInt(3,idUtente);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean updateInfoSpedVia(int id, int idUtente, String via) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" set via=? where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1,via);
			ps.setInt(2,id);
			ps.setInt(3,idUtente);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean updateInfoSpedCivico(int id, int idUtente, int civico) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" set civico=? where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,civico);
			ps.setInt(2,id);
			ps.setInt(3,idUtente);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean updateInfoSpedNameCAP(int id, int idUtente, int cap) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" set cap=? where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,cap);
			ps.setInt(2,id);
			ps.setInt(3,idUtente);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}
}
