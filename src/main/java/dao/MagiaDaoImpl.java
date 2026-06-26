package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.CartaBean;
import model.MagiaBean;

public class MagiaDaoImpl extends CartaDaoImpl implements MagiaDao{

	private static final String CARTA_NAME = "carta";
	private static final String MAGIA_NAME = "magia";
	private DataSource ds;
	
	public MagiaDaoImpl(DataSource ds) {
		super(ds);
		this.ds=ds;
	}

	@Override
	//Consentito solo se carta è un'istanza di MagiaBean, altrimenti return false
	public synchronized boolean saveCarta(CartaBean carta) throws SQLException {
		if(carta instanceof MagiaBean) {
			try (Connection conn = ds.getConnection()){
				conn.setAutoCommit(false);
				try {
					super.saveCarta(carta);
					return saveMagia((MagiaBean) carta);
				}
				finally {
					conn.setAutoCommit(true);
				}
			}
		}
		else
			return false;
	}
	
	@Override
	//Salva solo l'entità magia associata ad un carta, se la carta non esiste return false
	public synchronized boolean saveMagia(MagiaBean magia) throws SQLException {
		CartaBean carta = super.retrieveByKey(magia.getId());
		if (carta.getId() == 0)
			return false;
		String sql = "INSERT INTO " + MAGIA_NAME + " (id, tipologia) VALUES (?, ?)";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, magia.getId());
			ps.setString(2, magia.getTipologia());
			int result = ps.executeUpdate();
			return result != 0;
		}
	}

	//deleteCarta
	//il DB cancella anche magia a cascata
	
	@Override
	//cancella solo l'entità magia
	public synchronized boolean deleteMagia(int id) throws SQLException {
		String sql = "DELETE FROM " + MAGIA_NAME + " WHERE id = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			return result != 0;
		}
	}
	
	@Override
	public synchronized MagiaBean retrieveByKey(int id) throws SQLException {
		MagiaBean magia = new MagiaBean();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + MAGIA_NAME + " ON " + CARTA_NAME + ".id = " + MAGIA_NAME + ".id WHERE id = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery(sql)){
				if (rs.next()) {
					fillBean(magia, rs);
				}
			}
		}
		return magia;
	}
	
	@Override
	//per avere list<MagiaBean> fare casting
	public synchronized List<CartaBean> retrieveAll() throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + MAGIA_NAME + " ON " + CARTA_NAME + ".id = " + MAGIA_NAME + ".id ";
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MagiaBean carta = new MagiaBean();
					fillBean(carta, rs);
					list.add(carta);
				}
			}
		}
		return list;
	}
	
	@Override
	//per avere list<MagiaBean> fare casting
	public synchronized List<CartaBean> retrieveAll(int limit, int page) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + MAGIA_NAME + " ON " + CARTA_NAME + ".id = " + MAGIA_NAME + ".id LIMIT " + limit + " OFFSET " + page*limit;
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MagiaBean carta = new MagiaBean();
					fillBean(carta, rs);
					list.add(carta);
				}
			}
		}
		return list;
	}

	@Override
	//per avere list<MagiaBean> fare casting
	public synchronized List<CartaBean> retrieveFiltered(CartaBean carta) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + MAGIA_NAME + " ON " + CARTA_NAME + ".id = " + MAGIA_NAME + ".id ");
		ArrayList<String> params = new ArrayList<String>();
		
		super.buildCartaFilter(sql, params, carta);
				
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString())
				){
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i+1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MagiaBean bean = new MagiaBean();
					fillBean(bean, rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}
	
	@Override
	public synchronized List<MagiaBean> retrieveFiltered(MagiaBean magia) throws SQLException {
		ArrayList<MagiaBean> list = new ArrayList<MagiaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + MAGIA_NAME + " ON " + CARTA_NAME + ".id = " + MAGIA_NAME + ".id ");
		ArrayList<String> params = new ArrayList<String>();
		
		buildCartaFilter(sql, params, magia);
				
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString())
				){
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i+1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MagiaBean bean = new MagiaBean();
					fillBean(bean, rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}
	
	@Override
	//per avere list<MagiaBean> fare casting
	public synchronized List<CartaBean> retrieveFiltered(CartaBean carta, int limit, int page) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + MAGIA_NAME + " ON " + CARTA_NAME + ".id = " + MAGIA_NAME + ".id ");
		ArrayList<String> params = new ArrayList<String>();
		
		super.buildCartaFilter(sql, params, carta);
		
		sql.append(" LIMIT " + limit + " OFFSET " + page*limit);
		
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString())
				){
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i+1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MagiaBean bean = new MagiaBean();
					fillBean(bean, rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}

	@Override
	public synchronized List<MagiaBean> retrieveFiltered(MagiaBean magia, int limit, int page) throws SQLException {
		ArrayList<MagiaBean> list = new ArrayList<MagiaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + MAGIA_NAME + " ON " + CARTA_NAME + ".id = " + MAGIA_NAME + ".id ");
		ArrayList<String> params = new ArrayList<String>();
		
		buildCartaFilter(sql, params, magia);
		
		sql.append(" LIMIT " + limit + " OFFSET " + page*limit);
		
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString())
				){
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i+1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MagiaBean bean = new MagiaBean();
					fillBean(bean, rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}

	@Override
	public synchronized boolean changeTipologia(MagiaBean magia) throws SQLException {
		String sql = "UPDATE " + MAGIA_NAME + " SET tipologia = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, magia.getTipologia());
        	ps.setInt(1, magia.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	private void fillBean(MagiaBean magia, ResultSet rs) throws SQLException {
		super.fillBean(magia, rs);
		magia.setTipologia(rs.getString("tipologia"));
	}
	
	protected boolean buildCartaFilter(StringBuilder filter, ArrayList<String> params, MagiaBean magia) {
		boolean first = super.buildCartaFilter(filter, params, magia);
		if (!magia.getTipologia().equals("")) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" tipologia LIKE ? ");
			params.add("%"+magia.getTipologia()+"%");
		}
		return first;
	}
	
}
