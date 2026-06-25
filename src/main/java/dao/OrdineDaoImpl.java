package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import model.OrdineBean;

public class OrdineDaoImpl implements OrdineDao{
	private static final String TABLE_NAME = "ordine";
	private DataSource ds = null;
	
	public OrdineDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public synchronized boolean createOrdine(OrdineBean ordine) throws SQLException {
		String sql = "INSERT INTO "+TABLE_NAME+"(stato,id_ordine,id_utente,id_metodo,id_infosped,data_acquisto,data_consegna)"
				+ " VALUES(?,?,?,?,?,?,?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1,ordine.getStato());
			ps.setInt(2, ordine.getIdOrdine());
			ps.setInt(3, ordine.getIdUtente());
			ps.setInt(4, ordine.getIdMetodo());
			ps.setInt(5, ordine.getIdInfoSped());
			ps.setDate(6, ordine.getDataAcquisto());
			ps.setDate(7, ordine.getDataConsegna());
			int rowAdded = ps.executeUpdate();
			return rowAdded != 0;
		}
		
	}

	@Override
	public synchronized boolean changeStato(int idOrdine, String stato) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET stato=? where id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1,stato);
			ps.setInt(2, idOrdine);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeDataAcquisto(int idOrdine, Date acquisto) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET data_acquisto=? where id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setDate(1,acquisto);
			ps.setInt(2, idOrdine);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeDataConsegna(int idOrdine, Date consegna) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET data_consegna=? where id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setDate(1,consegna);
			ps.setInt(2, idOrdine);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeMetodoPagamento(int idOrdine, int idMetodo) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET id_metodo=? where id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,idMetodo);
			ps.setInt(2, idOrdine);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeInfoSped(int idOrdine, int idInfoSped) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET id_infosped=? where id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,idInfoSped);
			ps.setInt(2, idOrdine);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean deleteOrdine(int idOrdine) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id_ordine = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idOrdine);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0; 
		}
	}

	@Override
	public synchronized OrdineBean retrieveByKey(int idOrdine) throws SQLException {
		String sql = "SELECT * from "+TABLE_NAME+ " where id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idOrdine);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				OrdineBean ordine = new OrdineBean(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getDate(6),rs.getDate(7));
				return ordine;
			}
		}
		return null;	}

	@Override
	public synchronized List<OrdineBean> retrieveByIdUtente(int idUtente) throws SQLException {
		String sql = "SELECT * from "+TABLE_NAME+ " where id_utente=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUtente);
			ResultSet rs = ps.executeQuery();
			List<OrdineBean> list = new ArrayList<OrdineBean>();
			while(rs.next()) {
				OrdineBean ordine = new OrdineBean(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getDate(6),rs.getDate(7));
				list.add(ordine);
			}
			return list;
		}
	}

}
