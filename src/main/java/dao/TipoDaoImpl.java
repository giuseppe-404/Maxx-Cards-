package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.TipoBean;

public class TipoDaoImpl implements TipoDao {
	
	private static final String TABLE_NAME = "tipo";
	private DataSource ds;
	
	public TipoDaoImpl(DataSource ds) {
		this.ds=ds;
	}

	@Override
	public void saveTipo(TipoBean tipo) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " (tipo) VALUES (?)";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setString(1, tipo.getTipo());
			ps.executeUpdate();
		}		
	}

	@Override
	public boolean deleteTipo(String id) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE tipo = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setString(1, id);
			int result = ps.executeUpdate();
			return result != 0;
		}
	}

	@Override
	public TipoBean retrieveByKey(String id) throws SQLException {
		TipoBean tipo = new TipoBean();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE tipo = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery(sql)){
				if (rs.next()) {
					tipo.setTipo(rs.getString("tipo"));
				}
			}
		}
		return tipo;
	}

	@Override
	public List<TipoBean> retrieveAll() throws SQLException {
		ArrayList<TipoBean> lista = new ArrayList<TipoBean>();
		String sql = "SELECT * FROM " + TABLE_NAME;
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					TipoBean tipo = new TipoBean();
					tipo.setTipo(rs.getString("tipo"));
					lista.add(tipo);
				}
			}
		}
		return lista;
	}

	@Override
	public boolean changeTipo(TipoBean tipo, String originalId) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET tipo = ? WHERE tipo = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, tipo.getTipo());
        	ps.setString(2, originalId);
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}



}
