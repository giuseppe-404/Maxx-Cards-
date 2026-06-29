package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.ConfezionatoBean;
import model.ProdottoBean;
import model.ProdottoYGOBean;

public class ConfezionatoDaoImpl extends ProdottoYGODaoImpl implements ConfezionatoDao {
	private static final String TABLE_NAME = "confezionato";
	private static final String MIDDLE_NAME = "prodottoYGO";
	private static final String SUPER_NAME = "prodotto";
	private DataSource ds = null;
	
	public ConfezionatoDaoImpl(DataSource ds) {
		super(ds);
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean saveProdottoYGO(ProdottoYGOBean prodotto) throws SQLException {
		if(prodotto instanceof ConfezionatoBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdottoYGO(prodotto);
					return saveConfezionato((ConfezionatoBean)prodotto);
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
		if(prodotto instanceof ConfezionatoBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdotto(prodotto);
					return saveConfezionato((ConfezionatoBean)prodotto);
				}
				finally {
					connection.setAutoCommit(true);
				}
			}
		}
		else return false;
	}
	
	@Override
	public synchronized boolean saveConfezionato(ConfezionatoBean confezionato) throws SQLException {
		ProdottoYGOBean prod = super.retrieveByKey(confezionato.getId());
		if(prod == null) return false;
		String sql = "INSERT INTO "+TABLE_NAME+"(id,id_set) VALUES (?,?);";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, confezionato.getId());
			ps.setInt(2, confezionato.getIdSet());
		}
		return false;
	}

	@Override
	public synchronized boolean deleteConfezionato(int id) throws SQLException {
		String sql = "DELETE FROM "+TABLE_NAME+" WHERE id = ?;";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0;
		}
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
					ConfezionatoBean temp = new ConfezionatoBean();
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
					ConfezionatoBean temp = new ConfezionatoBean();
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
					ConfezionatoBean temp = new ConfezionatoBean();
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
					ConfezionatoBean temp = new ConfezionatoBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<ConfezionatoBean> retrieveFiltered(ConfezionatoBean confezionato) throws SQLException {
		List<ConfezionatoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, confezionato);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ConfezionatoBean temp = new ConfezionatoBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<ConfezionatoBean> retrieveFiltered(ConfezionatoBean confezionato, int page, int limit)
			throws SQLException {
		List<ConfezionatoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, confezionato);
		sql.append(" LIMIT "+ limit+ " OFFSET "+page*limit);
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ConfezionatoBean temp = new ConfezionatoBean();
					fillBean(temp,rs);
					list.add(temp);
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
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ConfezionatoBean prod = new ConfezionatoBean();
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
		String sql = "SELECT * FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id LIMIT "+ limit+ " OFFSET "+page*limit;
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ConfezionatoBean prod = new ConfezionatoBean();
					fillBean(prod,rs);
					list.add(prod);
				}
			}
		}
		return list;
	}
	
	public synchronized ConfezionatoBean retrieveByKey(int id) throws SQLException{
		ConfezionatoBean conf = new ConfezionatoBean();
		String sql = "SELECT * FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id WHERE "
						+ " id = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					fillBean(conf,rs);
				}
			}
		} return conf;
	}
	
	@Override
	public synchronized boolean changeIdSet(ConfezionatoBean confezionato) throws SQLException {
		String sql = "UPDATE "+ TABLE_NAME + " SET id_set = ? where id=?";
		try(Connection connection = ds.getConnection();
				 PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, confezionato.getIdSet());
			ps.setInt(2, confezionato.getId());
			int rowChanged = ps.executeUpdate();
			return rowChanged != 0;
		}
	}

	protected  synchronized void fillBean(ConfezionatoBean confezionato, ResultSet rs) throws SQLException {
		super.fillBean(confezionato, rs);
		confezionato.setIdSet(rs.getInt("id_set"));
	}
	
	protected synchronized boolean buildProdottoFilter(StringBuilder sql, ArrayList<String> attribute, ConfezionatoBean confezionato) {
		boolean primo = super.buildProdottoFilter(sql, attribute, confezionato);
		if(confezionato.getIdSet() != 0) {
			if(primo) sql.append(" WHERE ");
			else sql.append(" AND ");
			sql.append(" id_set = ? ");
			attribute.add(Integer.toString(confezionato.getIdSet()));
		}return primo;
	}
	
	
}
