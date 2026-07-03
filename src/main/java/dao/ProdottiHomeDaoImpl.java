package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.ProdottiHomeBean;

public class ProdottiHomeDaoImpl implements ProdottiHomeDao {
	private static final String TABLE_NAME = "prodotti_home";
	private DataSource ds = null; 
	
	public ProdottiHomeDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public boolean saveProdottiHome(ProdottiHomeBean prod) throws SQLException {
		String sql = "INSERTO INTO "+TABLE_NAME+"(id) VALUES (?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, prod.getId());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public boolean deleteProdottiHome(int id) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public List<Integer> retrieveAll() throws SQLException {
		List<Integer> array = new ArrayList<>();
		String sql = "SELECT * FROM "+TABLE_NAME;
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				array.add(rs.getInt(1));
			}
		}
		return array;
	}

}
