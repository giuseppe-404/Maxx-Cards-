package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.MetodoPagamentoBean;

public class MetodoPagamentoDaoImpl implements MetodoPagamentoDao{
	private static final String TABLE_NAME = "metodoPagamento";
	private DataSource ds = null;
	
	
	public MetodoPagamentoDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean saveMetodoPagamento(MetodoPagamentoBean met) throws SQLException{
		String sql = "INSERT into "+TABLE_NAME+"(metodo,id,id_utente)"
				+ "values(?,?,?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1,met.getMetodo());
			ps.setInt(2, met.getId());
			ps.setInt(3, met.getIdUtente());
			int rowAdded = ps.executeUpdate();
			return rowAdded != 0;
		}
	}
	
	@Override
	public synchronized MetodoPagamentoBean retrieveByKey(int id, int idUtente) throws SQLException {
		String sql = "SELECT * from "+TABLE_NAME+ " where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.setInt(2,idUtente);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				MetodoPagamentoBean met = new MetodoPagamentoBean(rs.getString(1),rs.getInt(2), rs.getInt(3));
				return met;
			}
		}
		return null;
	}
	@Override
	public synchronized List<MetodoPagamentoBean> retrieveByIdUtente(int idUtente) throws SQLException {
		String sql = "SELECT * from "+TABLE_NAME+" where id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUtente);
			ResultSet rs = ps.executeQuery();
			List<MetodoPagamentoBean> list = new ArrayList<MetodoPagamentoBean>();
			while(rs.next()) {
				MetodoPagamentoBean met = new MetodoPagamentoBean(rs.getString(1),rs.getInt(2),rs.getInt(3));
				list.add(met);
			} return list;
		}
	}
	@Override
	public synchronized boolean deleteByKey(int id, int idUtente) throws SQLException {
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
	public synchronized boolean deleteByIdUtente(int idUtente) throws SQLException {
		String sql = "DELETE from "+TABLE_NAME+" where id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUtente);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0; 
		}
	}
	@Override
	public synchronized boolean updateMetodoPagamento(MetodoPagamentoBean met) throws SQLException{
		String sql = "UPDATE "+TABLE_NAME+" set metodo=? where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1,met.getMetodo());
			ps.setInt(2, met.getId());
			ps.setInt(3, met.getIdUtente());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}	
	
	
}
