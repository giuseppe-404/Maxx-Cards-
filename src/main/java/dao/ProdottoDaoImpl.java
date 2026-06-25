package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.ProdottoBean;

public class ProdottoDaoImpl implements ProdottoDao {
	private static final String TABLE_NAME = "prodotto";
	private DataSource ds = null;
	
	public ProdottoDaoImpl(DataSource ds){
		this.ds = ds;
	}
	
	@Override
	public synchronized void saveProdotto(ProdottoBean prodotto) throws SQLException {
		String sql = "INSERT INTO "+TABLE_NAME+"(id,nome,qnt,prezzo,descrizione,sconto,path_img,mime_type)"
				+ "VALUES(?,?,?,?,?,?,?,?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, prodotto.getId());
			ps.setString(2, prodotto.getNome());
			ps.setInt(3, prodotto.getQnt());
			ps.setInt(4,prodotto.getPrezzo());
			ps.setString(5, prodotto.getDescrizione());
			ps.setInt(6, prodotto.getSconto());
			ps.setString(7, prodotto.getPathImg());
			ps.setString(8, prodotto.getMimeType());
			ps.executeQuery();
		}
	}

	@Override
	public synchronized ProdottoBean retrieveByKey(int idProdotto) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idProdotto);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ProdottoBean prodotto = new ProdottoBean(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getInt(6),rs.getString(7),rs.getString(8));
				return prodotto;
			}
		} return null;
	}

	@Override
	public synchronized List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT * FROM "+TABLE_NAME+" ");
		boolean primo=true;
		ArrayList<String> attributi = new ArrayList<>();
		if(!prodotto.getNome().equals("")) {
			if(primo) {
				primo = false;
				sql.append("WHERE ");
			}else sql.append(" AND ");
			sql.append(" nome LIKE ? ");
			attributi.add("%"+prodotto.getNome()+"%");
		}
		if(prodotto.getQnt() < 0) {
			if(primo) {
				primo = false;
				sql.append("WHERE ");
			}else sql.append(" AND ");
			sql.append(" qnt=? ");
			attributi.add(Integer.toString(prodotto.getQnt()));
		}
		if(prodotto.getPrezzo() < 0) {
			if(primo) {
				primo = false;
				sql.append("WHERE ");
			}else sql.append(" AND ");
			sql.append(" (prezzo*(100-sconto)/100)<=? ");
			attributi.add(Integer.toString(prodotto.getPrezzo()));
		}
		if(!prodotto.getDescrizione().equals("")) {
			if(primo) {
				primo = false;
				sql.append("WHERE ");
			}else sql.append(" AND ");
			sql.append(" descrizione LIKE ? ");
			attributi.add("%"+prodotto.getDescrizione()+"%");
		}
		sql.append(";");
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())) {
				for (int i = 0; i < attributi.size(); i++) {
					ps.setString(i + 1, attributi.get(i));
				}
				ResultSet rs = ps.executeQuery();
				List<ProdottoBean> list = new ArrayList<>();
				while(rs.next()) {
					ProdottoBean temp = new ProdottoBean(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getInt(6),rs.getString(7),rs.getString(8));
					list.add(temp);
				}
				return list;
		}
	}

	@Override
	public synchronized List<ProdottoBean> retrieveAll(int page, int limit) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" LIMIT "+limit+" OFFSET "+page*limit+";";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			List<ProdottoBean> list = new ArrayList<>();
			while(rs.next()) {
				ProdottoBean temp = new ProdottoBean(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getInt(6),rs.getString(7),rs.getString(8));
				list.add(temp);
			}
			return list;
		}
	}

	@Override
	public synchronized boolean deleteProdotto(int idProdotto) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" where id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idProdotto);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0;
		}
	}

	@Override
	public synchronized boolean changeNome(int idProdotto, String nome) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET nome=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, nome);
			ps.setInt(2, idProdotto);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeQnt(int idProdotto, int qnt) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET qnt=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, qnt);
			ps.setInt(2, idProdotto);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changePrezzo(int idProdotto, int prezzo) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET prezzo=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, prezzo);
			ps.setInt(2, idProdotto);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeDescrizione(int idProdotto, String descrizione) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET descrizione=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, descrizione);
			ps.setInt(2, idProdotto);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeSconto(int idProdotto, int sconto) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET sconto=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, sconto);
			ps.setInt(2, idProdotto);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean changeImage(int idProdotto, String path, String mime) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET path_img=?, mime_type=? WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, path);
			ps.setString(2, mime);
			ps.setInt(3, idProdotto);
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

}
