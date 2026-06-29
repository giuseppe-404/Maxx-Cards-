package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.CartaSingolaBean;
import model.ProdottoBean;
import model.ProdottoYGOBean;

public class CartaSingolaDaoImpl extends ProdottoYGODaoImpl implements CartaSingolaDao {
	private static final String TABLE_NAME = "cartaSingola";
	private static final String MIDDLE_NAME = "prodottoYGO";
	private static final String SUPER_NAME = "prodotto";
	private DataSource ds = null;
	
	public CartaSingolaDaoImpl(DataSource ds) {
		super(ds);
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean saveProdottoYGO(ProdottoYGOBean prodotto) throws SQLException {
		if(prodotto instanceof CartaSingolaBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdottoYGO(prodotto);
					return saveCartaSingola((CartaSingolaBean)prodotto);
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
		if(prodotto instanceof CartaSingolaBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdotto(prodotto);
					return saveCartaSingola((CartaSingolaBean)prodotto);
				}
				finally {
					connection.setAutoCommit(true);
				}
			}
		}
		else return false;
	}
	
	@Override
	public synchronized boolean saveCartaSingola(CartaSingolaBean carta) throws SQLException {
		ProdottoYGOBean prod = super.retrieveByKey(carta.getId());
		if(prod == null) return false;
		String sql = "INSERT INTO "+TABLE_NAME+"(id,quality,id_set) VALUES (?,?,?);";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, carta.getId());
			ps.setString(2, carta.getQuality());
			ps.setInt(3,carta.getIdSet());
		}
		return false;
	}
	
	@Override
	public synchronized boolean deleteCartaSingola(int id) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id = ?;";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0;
		}
	}

	public synchronized List<CartaSingolaBean> retrieveFiltered(CartaSingolaBean carta) throws SQLException{
		List<CartaSingolaBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT * FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, carta);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					CartaSingolaBean temp = new CartaSingolaBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	public synchronized List<CartaSingolaBean> retrieveFiltered(CartaSingolaBean carta, int page, int limit) throws SQLException{
		List<CartaSingolaBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT * FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, carta);
		sql.append(" LIMIT "+ limit+ " OFFSET "+page*limit);
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					CartaSingolaBean temp = new CartaSingolaBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<ProdottoYGOBean> retrieveFiltered(ProdottoYGOBean prodotto) throws SQLException {
		List<ProdottoYGOBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT * FROM "
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
					CartaSingolaBean temp = new CartaSingolaBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}

	@Override
	public synchronized List<ProdottoYGOBean> retrieveFiltered(ProdottoYGOBean prodotto, int page, int limit) throws SQLException {
		List<ProdottoYGOBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT * FROM "
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
					CartaSingolaBean temp = new CartaSingolaBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}

	public synchronized List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto) throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT * FROM "
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
					CartaSingolaBean temp = new CartaSingolaBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	public synchronized List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto, int page, int limit) throws SQLException{
		List<ProdottoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT * FROM "
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
					ProdottoYGOBean temp = new ProdottoYGOBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<ProdottoBean> retrieveAll(int page, int limit) throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		String sql = "SELECT * FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id LIMIT "+ limit+ " OFFSET "+page*limit;
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					CartaSingolaBean prod = new CartaSingolaBean();
					fillBean(prod,rs);
					list.add(prod);
				}
			}
		}
		return list;
	}

	@Override
	public synchronized List<ProdottoBean> retrieveAll() throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		String sql = "SELECT * FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					CartaSingolaBean prod = new CartaSingolaBean();
					fillBean(prod,rs);
					list.add(prod);
				}
			}
		}
		return list;
	}

	@Override
	public synchronized CartaSingolaBean retrieveByKey(int id) throws SQLException {
		CartaSingolaBean carta = new CartaSingolaBean();
		String sql = "SELECT * FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id WHERE "
						+ " id = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					fillBean(carta,rs);
				}
			}
		} return carta;
	}
	
	@Override
	public synchronized boolean changeQuality(CartaSingolaBean carta) throws SQLException {
		String sql = "UPDATE "+ TABLE_NAME + " SET quality = ? where id=?";
		try(Connection connection = ds.getConnection();
				 PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, carta.getQuality());
			ps.setInt(2, carta.getId());
			
			int rowChanged = ps.executeUpdate();
			return rowChanged != 0;
		}
	}

	@Override
	public synchronized boolean changeIdSet(CartaSingolaBean carta) throws SQLException {
		String sql = "UPDATE "+ TABLE_NAME + " SET id_set = ? where id=?";
		try(Connection connection = ds.getConnection();
				 PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, carta.getIdSet());
			ps.setInt(2, carta.getId());
			int rowChanged = ps.executeUpdate();
			return rowChanged != 0;
		}
	}
	
	@Override
	public synchronized boolean changeIdCarta(CartaSingolaBean carta) throws SQLException {
		String sql = "UPDATE "+ TABLE_NAME + " SET id_carta = ? where id=?";
		try(Connection connection = ds.getConnection();
				 PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, carta.getIdCarta());
			ps.setInt(2, carta.getId());
			int rowChanged = ps.executeUpdate();
			return rowChanged != 0;
		}
	}

	public synchronized void fillBean(CartaSingolaBean carta, ResultSet rs) throws SQLException {
		super.fillBean(carta, rs);
		carta.setQuality(rs.getString("quality"));
		carta.setIdSet(rs.getInt("id_set"));
		carta.setIdCarta(rs.getInt("id_carta"));
	}
	
	public synchronized boolean buildProdottoFilter(StringBuilder st, ArrayList<String> attribute, CartaSingolaBean carta) {
		boolean primo = super.buildProdottoFilter(st, attribute, carta);
		if(!carta.getQuality().equals("")) {
			if(primo) {
				st.append(" WHERE ");
			}else st.append(" AND "); 
			st.append(" quality = ? ");
			attribute.add(carta.getQuality());
		}
		if(carta.getIdSet() == 0) {
			if(primo) {
				st.append(" WHERE ");
			}else st.append(" AND "); 
			st.append(" id_set = ? ");
			attribute.add(Integer.toString(carta.getIdSet()));
		}
		if(carta.getIdCarta() == 0) {
			if(primo) {
				st.append(" WHERE ");
			}else st.append(" AND ");
			st.append(" id_carta = ? ");
			attribute.add(Integer.toString(carta.getIdCarta()));
		}
		return primo;
	}
	
}
