package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.ContieneDeckBean;

public class ContieneDeckDaoImpl implements ContieneDeckDao {
	private static final String TABLE_NAME = "contieneDeck";
	private DataSource ds = null;
	
	public ContieneDeckDaoImpl(DataSource ds){
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean saveContieneDeck(ContieneDeckBean cont) throws SQLException {
		String sql = "INSERT INTO "+TABLE_NAME+"(id_deck,id_carta,qnt)"
				+ "VALUES(?,?,?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,cont.getIdDeck());
			ps.setInt(2, cont.getIdCarta());
			ps.setInt(3, cont.getQnt());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized boolean deleteContieneDeck(int idDeck, int idCarta) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" where id_deck=? and id_carta=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idDeck);
			ps.setInt(2, idCarta);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0;
		}
	}

	@Override
	public synchronized boolean changeQnt(ContieneDeckBean cont) throws SQLException {
		String sql = "UPDATE "+TABLE_NAME+" SET qnt=? WHERE id_deck=? and id_carta=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, cont.getQnt());
			ps.setInt(2, cont.getIdDeck());
			ps.setInt(3, cont.getIdCarta());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}

	@Override
	public synchronized List<ContieneDeckBean> retrieveByIdDeck(int idDeck) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id_deck=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idDeck);
			List<ContieneDeckBean> list = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ContieneDeckBean cont = new ContieneDeckBean();
				fillBean(cont,rs);
				list.add(cont);
			}return list;
		}
	}

	public synchronized void fillBean(ContieneDeckBean cont, ResultSet rs) throws SQLException {
		cont.setIdDeck(rs.getInt(1));
		cont.setIdCarta(rs.getInt(2));
		cont.setQnt(rs.getInt(3));
	}
	
}
