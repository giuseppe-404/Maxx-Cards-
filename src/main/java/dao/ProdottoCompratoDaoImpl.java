package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import model.ProdottoCompratoBean;

public class ProdottoCompratoDaoImpl implements ProdottoCompratoDao {
	private static final String TABLE_NAME = "prodotto_comprato";
	private DataSource ds = null;
	
	public ProdottoCompratoDaoImpl(DataSource ds){
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean saveProdottoComprato(ProdottoCompratoBean prodotto) throws SQLException {
		String sql = "INSERT "+TABLE_NAME+"(id,id_ordine,id_originale,prezzo,nome,qnt,info) "
				+ "VALUES (?,?,?,?,?,?,?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, prodotto.getId());
			ps.setInt(2, prodotto.getIdOrdine());
			ps.setInt(3, prodotto.getIdOriginale());
			ps.setInt(4, prodotto.getPrezzo());
			ps.setString(5, prodotto.getNome());
			ps.setInt(6, prodotto.getQnt());
			ps.setString(7, prodotto.getInfo());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized ProdottoCompratoBean retrieveByKey(int id, int idOrdine) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=? and id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.setInt(2,idOrdine);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ProdottoCompratoBean prod = new ProdottoCompratoBean(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getString(5),
						rs.getInt(6),rs.getString(7));
				return prod;
			}
			return null;
		}
	}

	@Override
	public synchronized List<ProdottoCompratoBean> retrieveByIdOrdine(int idOrdine) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,idOrdine);
			ResultSet rs = ps.executeQuery();
			List<ProdottoCompratoBean> list = new ArrayList<>();
			while(rs.next()) {
				ProdottoCompratoBean prod = new ProdottoCompratoBean(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getString(5),
						rs.getInt(6),rs.getString(7));
				list.add(prod);
			}
			return list;
		}
	}

	@Override
	public synchronized boolean deleteProdottoComprato(int id, int idOrdine) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id=? and id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.setInt(2, idOrdine);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean deleteProdottoCompratoByIdOrdine(int idOrdine) throws SQLException {
		String sql = " SET SQL_SAFE_UPDATES = 0; DELETE FROM "+TABLE_NAME+" WHERE id_ordine=?; SET SQL_SAFE_UPDATE = 1;";
		try (Connection connection = ds.getConnection()){
			connection.setAutoCommit(false);
			try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idOrdine);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
			}finally{
				connection.setAutoCommit(true);
			}	
		}
	}
	@Override
	public synchronized boolean changeIdOriginale(ProdottoCompratoBean prodotto) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+ " SET id_originale=? WHERE id=? and id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, prodotto.getIdOriginale());
			ps.setInt(2, prodotto.getId());
			ps.setInt(3, prodotto.getIdOrdine());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
		
	}

	@Override
	public synchronized boolean changePrezzo(ProdottoCompratoBean prodotto) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+ " SET prezzo=? WHERE id=? and id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, prodotto.getPrezzo());
			ps.setInt(2, prodotto.getId());
			ps.setInt(3, prodotto.getIdOrdine());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeNome(ProdottoCompratoBean prodotto) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+ " SET nome=? WHERE id=? and id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, prodotto.getNome());
			ps.setInt(2, prodotto.getId());
			ps.setInt(3, prodotto.getIdOrdine());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeQnt(ProdottoCompratoBean prodotto) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+ " SET qnt=? WHERE id=? and id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, prodotto.getQnt());
			ps.setInt(2, prodotto.getId());
			ps.setInt(3, prodotto.getIdOrdine());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeInfo(ProdottoCompratoBean prodotto) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+ " SET info=? WHERE id=? and id_ordine=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, prodotto.getInfo());
			ps.setInt(2, prodotto.getId());
			ps.setInt(3, prodotto.getIdOrdine());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

}
