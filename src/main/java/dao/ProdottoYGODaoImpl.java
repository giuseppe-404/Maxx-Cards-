package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.ProdottoBean;
import model.ProdottoYGOBean;

public class ProdottoYGODaoImpl extends ProdottoDaoImpl implements ProdottoYGODao {
	private static final String TABLE_NAME = "prodottoYGO";
	private static final String SUPER_NAME = "prodotto";
	private DataSource ds = null;
	
	public ProdottoYGODaoImpl(DataSource ds) {
		super(ds);
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean saveProdotto(ProdottoBean prodotto) throws SQLException {
		if(prodotto instanceof ProdottoYGOBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdotto(prodotto);
					return saveProdottoYGO((ProdottoYGOBean)prodotto);
				}
				finally {
					connection.setAutoCommit(true);
				}
			}
		}
		else return false;
	}

	public synchronized boolean saveProdottoYGO(ProdottoYGOBean prodotto) throws SQLException {
		ProdottoBean prod = super.retrieveByKey(prodotto.getId());
		if (prod == null) return false;
		String sql = "INSERT INTO "+TABLE_NAME+"(id,lingua) VALUES (?,?);";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
					ps.setInt(1, prodotto.getId());
					ps.setString(2, prodotto.getLingua());
					int rowUpdated = ps.executeUpdate();
					return rowUpdated != 0;
		}
	}
	
	@Override
	public synchronized boolean deleteProdottoYGO(int id) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id = ?;";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0;
		}
	}

	@Override
	public synchronized ProdottoYGOBean retrieveByKey(int id) throws SQLException {
		ProdottoYGOBean prod = new ProdottoYGOBean();
		String sql = "SELECT "+SUPER_NAME+ ".*, lingua FROM " + SUPER_NAME+ " JOIN "+TABLE_NAME+ " ON " + SUPER_NAME + ".id = " +TABLE_NAME+ ".id WHERE id = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,id);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					fillBean(prod,rs);
				}
			}
		}
		return prod;	
	}

	@Override
	public synchronized List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto) throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua FROM "+SUPER_NAME+" JOIN "+TABLE_NAME+ "ON "+ SUPER_NAME+".id = "+TABLE_NAME+".id ");
		ArrayList<String> attribute = new ArrayList<>();
		super.buildProdottoFilter(sql, attribute, prodotto);
		
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

	public synchronized List<ProdottoYGOBean> retrieveFiltered(ProdottoYGOBean prodotto) throws SQLException{
		List<ProdottoYGOBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+ ".*,lingua FROM "+SUPER_NAME+" JOIN "+TABLE_NAME+" ON " + SUPER_NAME +".id = "+TABLE_NAME+ ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		
		buildProdottoFilter(sql, attribute, prodotto);
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
		} return list;
	}
	
	@Override
	public synchronized List<ProdottoBean> retrieveAll() throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		String sql = "SELECT "+ SUPER_NAME + ".*,lingua FROM "+SUPER_NAME + " JOIN " +TABLE_NAME+ " ON "+ SUPER_NAME + ".id = "+TABLE_NAME+".id ";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ProdottoYGOBean prod = new ProdottoYGOBean();
					fillBean(prod,rs);
					list.add(prod);
				}
			}
		}
		return list;
	}

	@Override
	public synchronized List<ProdottoBean> retrieveFiltered(ProdottoBean prodotto, int page, int limit) throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua FROM "+SUPER_NAME+" JOIN "+TABLE_NAME+ "ON "+ SUPER_NAME+".id = "+TABLE_NAME+".id ");
		ArrayList<String> attribute = new ArrayList<>();
		super.buildProdottoFilter(sql, attribute, prodotto);
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
	public synchronized List<ProdottoYGOBean> retrieveFiltered(ProdottoYGOBean prodotto, int page, int limit) throws SQLException {
		List<ProdottoYGOBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+ ".*,lingua FROM "+SUPER_NAME+" JOIN "+TABLE_NAME+" ON " + SUPER_NAME +".id = "+TABLE_NAME+ ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		sql.append(" LIMIT "+ limit+ " OFFSET "+page*limit);
		buildProdottoFilter(sql, attribute, prodotto);
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
		} return list;
	}
	
	@Override
	public synchronized List<ProdottoBean> retrieveAll(int page, int limit) throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		String sql = "SELECT "+ SUPER_NAME + ".*,lingua FROM "+SUPER_NAME + " JOIN " +TABLE_NAME+ " ON "+ SUPER_NAME + ".id = "+TABLE_NAME+".id LIMIT " + limit + " OFFSET " + page*limit;
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ProdottoYGOBean prod = new ProdottoYGOBean();
					fillBean(prod,rs);
					list.add(prod);
				}
			}
		}
		return list;
	}

	@Override
	public synchronized boolean changeLingua(ProdottoYGOBean prodotto) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET lingua = ? Where id = ? ";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, prodotto.getLingua());
			ps.setInt(2, prodotto.getId());
			
			int rowChanged = ps.executeUpdate();
			return rowChanged != 0;
		}
	}

	private void fillBean(ProdottoYGOBean prodotto, ResultSet rs) throws SQLException {
		super.fillBean(prodotto,rs);
		prodotto.setLingua(rs.getString("lingua"));
	}
	
	protected boolean buildProdottoFilter(StringBuilder filter, ArrayList<String> attribute, ProdottoYGOBean prodotto) {
		boolean first = super.buildProdottoFilter(filter, attribute, prodotto);
		if(!prodotto.getLingua().equals("")) {
			if(first) {
				first = false;
				filter.append("WHERE ");
			}else {
				filter.append("AND ");
			}
			filter.append(" lingua LIKE ?");
			attribute.add("%"+prodotto.getLingua()+"%");
		}
		return first;
	}
	
}
