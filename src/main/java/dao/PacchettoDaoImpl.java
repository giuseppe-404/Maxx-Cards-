package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.ConfezionatoBean;
import model.PacchettoBean;
import model.ProdottoBean;
import model.ProdottoYGOBean;

public class PacchettoDaoImpl extends ConfezionatoDaoImpl implements PacchettoDao {
	private static final String BEAN_NAME = "pacchetto";
	private static final String TABLE_NAME = "confezionato";
	private static final String MIDDLE_NAME = "prodottoYGO";
	private static final String SUPER_NAME = "prodotto";
	private DataSource ds = null;
	
	public PacchettoDaoImpl(DataSource ds) {
		super(ds);
		this.ds = ds;
	}
	
	@Override
	public synchronized boolean savePacchetto(PacchettoBean bean) throws SQLException {
		ConfezionatoBean conf = super.retrieveByKey(bean.getId());
		if(conf == null) return false;
		String sql = "INSERT INTO "+BEAN_NAME+ "(id) VALUES(?)";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, bean.getId());
			int rowUpdated = ps.executeUpdate();
			return rowUpdated != 0;
		}
	}
	
	@Override
	public synchronized boolean saveProdotto(ProdottoBean prodotto) throws SQLException {
		if(prodotto instanceof ConfezionatoBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdotto(prodotto);
					return savePacchetto((PacchettoBean)prodotto);
				}
				finally {
					connection.setAutoCommit(true);
				}
			}
		}
		else return false;
	}

	@Override
	public synchronized boolean saveProdottoYGO(ProdottoYGOBean prodotto) throws SQLException {
		if(prodotto instanceof PacchettoBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveProdottoYGO(prodotto);
					return savePacchetto((PacchettoBean)prodotto);
				}
				finally {
					connection.setAutoCommit(true);
				}
			}
		}
		else return false;
	}
	
	@Override
	public synchronized boolean saveConfezionato(ConfezionatoBean prodotto) throws SQLException{
		if(prodotto instanceof PacchettoBean) {
			try(Connection connection = ds.getConnection()){
				connection.setAutoCommit(false);
				try {
					super.saveConfezionato(prodotto);
					return savePacchetto((PacchettoBean)prodotto);
				}
				finally {
					connection.setAutoCommit(true);
				}
			}
		}
		else return false;
	}
	
	@Override
	public synchronized boolean deletePacchetto(int id) throws SQLException {
		String sql = "DELETE FROM "+BEAN_NAME+ " WHERE id=?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1,id);
			int rowDeleted = ps.executeUpdate();
			return rowDeleted != 0;
		}
	}

	public synchronized List<PacchettoBean> retrieveFiltered(PacchettoBean prod) throws SQLException{
		List<PacchettoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, prod);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					PacchettoBean temp = new PacchettoBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	public synchronized List<PacchettoBean> retrieveFiltered(PacchettoBean prod, int page, int limit) throws SQLException{
		List<PacchettoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id");
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
					PacchettoBean temp = new PacchettoBean();
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
		String sql = "SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id ";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					PacchettoBean prod = new PacchettoBean();
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
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id LIMIT "+ limit+ " OFFSET "+page*limit;
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					PacchettoBean prod = new PacchettoBean();
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
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, prod);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					PacchettoBean temp = new PacchettoBean();
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
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id");
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
					PacchettoBean temp = new PacchettoBean();
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
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, confezionato);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					PacchettoBean temp = new PacchettoBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	public synchronized List<ProdottoBean> retrieveFiltered ( ProdottoBean prodotto) throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id");
		ArrayList<String> attribute = new ArrayList<>();
		buildProdottoFilter(sql, attribute, prodotto);
		
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql.toString())){
			for(int i = 0; i < attribute.size(); i++) {
				ps.setString(i+1,attribute.get(i));
			}
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					PacchettoBean temp = new PacchettoBean();
					fillBean(temp,rs);
					list.add(temp);
				}
			}
		}
		return list;
	}
	
	public synchronized List<ProdottoBean> retrieveFiltered ( ProdottoBean prodotto, int page, int limit) throws SQLException {
		List<ProdottoBean> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id");
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
					PacchettoBean temp = new PacchettoBean();
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
					PacchettoBean temp = new PacchettoBean();
					fillBean(temp,rs);
					list.add(temp);
				}
				return list;
			}
		}
	}

	public synchronized PacchettoBean retrieveByKey(int id) throws SQLException{
		PacchettoBean bean = new PacchettoBean();
		String sql = "SELECT "+SUPER_NAME+".*, lingua, id_set FROM "
				+ SUPER_NAME + " JOIN " + MIDDLE_NAME + " ON " + SUPER_NAME + ".id = " + MIDDLE_NAME + 
				".id JOIN " + TABLE_NAME + " ON " + SUPER_NAME + ".id = " + TABLE_NAME + ".id JOIN "+BEAN_NAME+" ON "+SUPER_NAME+" .id = "+
				BEAN_NAME+".id WHERE "
						+ " id = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				fillBean(bean,rs);
			}
			return bean;
		}
	}
	
	
	protected synchronized void fillBean(PacchettoBean bean, ResultSet rs) throws SQLException {
		super.fillBean(bean,rs);
		bean.setId(rs.getInt("id"));
		
	}
	
	protected synchronized boolean buildProdottoFilter(StringBuilder sql, ArrayList<String> attribute, PacchettoBean bean) {
		return super.buildProdottoFilter(sql, attribute, bean);
	}

}
