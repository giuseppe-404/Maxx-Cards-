package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.WantsBean;

public class WantsDaoImpl implements WantsDao {
	private static final String TABLE_NAME = "wants";
	private DataSource ds = null;
	
	public WantsDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public boolean saveWants(WantsBean wants) throws SQLException {
		String sql ="INSERT into "+TABLE_NAME+"(id_utente,id_prodotto) VALUES (?.?);";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, wants.getIdUtente());
			ps.setInt(2, wants.getIdProdotto());
			int rowAdded = ps.executeUpdate();
			return rowAdded != 0;
		}
		
	}

	@Override
	public boolean deleteWants(int idUser, int idProd) throws SQLException {
		String sql = "DELETE from "+TABLE_NAME+" where id_prodotto=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idProd);
			ps.setInt(2,idUser);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0; 
		}
	}

	@Override
	public boolean deleteByIdUser(int idUser) throws SQLException {
		String sql = "SET SQL_SAFE_UPDATES = 0; DELETE FROM "+TABLE_NAME+" WHERE id_utente=?; SET SQL_SAFE_UPDATE = 1;";
		try (Connection connection = ds.getConnection()){
			connection.setAutoCommit(false);
			try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUser);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
			}finally{
				connection.setAutoCommit(true);
			}	
		}
	}
	

	@Override
	public List<WantsBean> retrieveByIdUtente(int idUtente) throws SQLException {
		String sql = "SELECT * from "+TABLE_NAME+" where id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUtente);
			ResultSet rs = ps.executeQuery();
			List<WantsBean> list = new ArrayList<WantsBean>();
			while(rs.next()) {
				WantsBean wants = new WantsBean(rs.getInt(1),rs.getInt(2));
				list.add(wants);
			} return list;
		}
	}

	public WantsBean retrieveByKey(int idUser, int idProdotto) throws SQLException{
		String sql = "SELECT * from "+TABLE_NAME+ " where id_prodotto=? and id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idProdotto);
			ps.setInt(2,idUser);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				WantsBean wants = new WantsBean(rs.getInt(1),rs.getInt(2));
				return wants;
			}
			return null;
		}
	}
}
