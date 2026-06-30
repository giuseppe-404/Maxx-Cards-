package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.DeckBean;
import model.ProdottoBean;
import model.ProdottoYGOBean;

public class DeckDaoImpl extends ProdottoYGODaoImpl implements DeckDao{
	private static final String TABLE_NAME = "deck";
	private static final String MIDDLE_NAME = "prodottoYGO";
	private static final String SUPER_NAME = "prodotto";
	private DataSource ds = null;
	
	public DeckDaoImpl(DataSource ds) {
		super(ds);
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean saveProdottoYGO(ProdottoYGOBean prodotto) throws SQLException {
		if(prodotto instanceof DeckBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdottoYGO(prodotto);
					return saveDeck((DeckBean)prodotto);
				}
				finally {
					connection.setAutoCommit(true);
				}
			}
		}
		else return false;
	}
	
	@Override
	public synchronized boolean saveProdotto(ProdottoBean prodotto) throws SQLException {
		if(prodotto instanceof DeckBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdotto(prodotto);
					return saveDeck((DeckBean)prodotto);
				}
				finally {
					connection.setAutoCommit(true);
				}
			}
		}
		else return false;
	}
	
	@Override
	public synchronized boolean saveDeck(DeckBean deck) throws SQLException {
		ProdottoYGOBean prod = super.retrieveByKey(deck.getId());
		if(prod == null) return false;
		String sql = "INSERT INTO "+TABLE_NAME+"(id) VALUES (?);";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, deck.getId());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
		
	}

	@Override
	public synchronized boolean deleteDeck(int id) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id = ?;";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0;
		}
	}
	
	@Override
	public synchronized List<ProdottoBean> retrieveAll() throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		String sql = "SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					DeckBean prod = new DeckBean();
					fillBean(prod,rs);
					list.add(prod);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<ProdottoBean> retrieveAll(int page, int limit) throws SQLException{
		List<ProdottoBean> list = new ArrayList<>();
		String sql = "SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id LIMIT "+ limit+ " OFFSET "+page*limit;
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					DeckBean prod = new DeckBean();
					fillBean(prod,rs);
					list.add(prod);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<ProdottoYGOBean> retrieveFiltered(ProdottoYGOBean prod) throws SQLException {
		List<ProdottoYGOBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, prod);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					DeckBean temp = new DeckBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<ProdottoYGOBean> retrieveFiltered(ProdottoYGOBean prod, int page, int limit) throws SQLException {
		List<ProdottoYGOBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, prod);
		sql.append(" LIMIT "+ limit+ " OFFSET "+page*limit);
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					DeckBean temp = new DeckBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto, int page, int limit) throws SQLException{
		List<ProdottoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, prodotto);
		sql.append(" LIMIT "+ limit+ " OFFSET "+page*limit);
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					DeckBean temp = new DeckBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	public synchronized List<ProdottoBean> retrieveFiltered ( ProdottoBean prodotto) throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, prodotto);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					DeckBean temp = new DeckBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<DeckBean> retrieveFiltered(DeckBean Deck) throws SQLException {
		List<DeckBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, Deck);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					DeckBean temp = new DeckBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<DeckBean> retrieveFiltered(DeckBean Deck, int page, int limit)
			throws SQLException {
		List<DeckBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, Deck);
		sql.append(" LIMIT "+ limit+ " OFFSET "+page*limit);
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					DeckBean temp = new DeckBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}

	
	public synchronized DeckBean retrieveByKey(int id) throws SQLException{
		DeckBean deck = new DeckBean();
		String sql = "SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id WHERE "
						+ " id = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					fillBean(deck,rs);
				}
			}
		} return deck;
	}
	
	protected  synchronized void fillBean(DeckBean deck, ResultSet rs) throws SQLException {
		super.fillBean(deck, rs);
	}
	
	protected synchronized boolean buildProdottoFilter(StringBuilder sql, ArrayList<String> attribute, DeckBean deck) {
		return super.buildProdottoFilter(sql,attribute,deck);
	}
}
