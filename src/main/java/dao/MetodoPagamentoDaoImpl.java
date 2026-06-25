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
	public void saveMetodoPagamento(MetodoPagamentoBean met) throws SQLException{
		String sql = "INSERT into "+TABLE_NAME+"(metodo,id,id_utente)"
				+ "values(?,?,?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1,met.getMetodo());
			ps.setInt(2, met.getId());
			ps.setInt(3, met.getIdUtente());
			ps.executeQuery();
		}
	}
	
	@Override
	public MetodoPagamentoBean retrieveMetodoPagamentodByKey(int id, int idUtente) throws SQLException {
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
	public List<MetodoPagamentoBean> retrieveMetodoPagamentoByIdUtente(int idUtente) throws SQLException {
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
	public boolean deleteMetodoPagamentoByKey(int id, int idUtente) throws SQLException {
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
	public boolean deleteMetodoPagamentoByIdUtente(int idUtente) throws SQLException {
		String sql = "DELETE from "+TABLE_NAME+" where id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUtente);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0; 
		}
	}
	@Override
	public boolean updateMetodoPagamento(int id, int idUtente, String met) throws SQLException{
		String sql = "UPDATE "+TABLE_NAME+" set metodo=? where id=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1,met);
			ps.setInt(2,id);
			ps.setInt(3,idUtente);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}	
	
	
}
