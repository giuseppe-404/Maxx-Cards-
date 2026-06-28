package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.CartaBean;
import model.TrappolaBean;

public class TrappolaDaoImpl extends CartaDaoImpl implements TrappolaDao{

	private static final String CARTA_NAME = "carta";
	private static final String TRAPPOLA_NAME = "Trappola";
	private DataSource ds;
	
	public TrappolaDaoImpl(DataSource ds) {
		super(ds);
		this.ds=ds;
	}

	@Override
	//Consentito solo se carta è un'istanza di TrappolaBean, altrimenti return false
	public synchronized boolean saveCarta(CartaBean carta) throws SQLException {
		if(carta instanceof TrappolaBean) {
			try (Connection conn = ds.getConnection()){
				conn.setAutoCommit(false);
				try {
					super.saveCarta(carta);
					return saveTrappola((TrappolaBean) carta);
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
	//Salva solo l'entità Trappola associata ad un carta, se la carta non esiste return false
	public synchronized boolean saveTrappola(TrappolaBean Trappola) throws SQLException {
		CartaBean carta = super.retrieveByKey(Trappola.getId());
		if (carta.getId() == 0)
			return false;
		String sql = "INSERT INTO " + TRAPPOLA_NAME + " (id, tipologia) VALUES (?, ?)";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, Trappola.getId());
			ps.setString(2, Trappola.getTipologia());
			int result = ps.executeUpdate();
			return result != 0;
		}
	}

	//deleteCarta
	//il DB cancella anche Trappola a cascata
	
	@Override
	//cancella solo l'entità Trappola
	public synchronized boolean deleteTrappola(int id) throws SQLException {
		String sql = "DELETE FROM " + TRAPPOLA_NAME + " WHERE id = ?";
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
	public synchronized TrappolaBean retrieveByKey(int id) throws SQLException {
		TrappolaBean Trappola = new TrappolaBean();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + TRAPPOLA_NAME + " ON " + CARTA_NAME + ".id = " + TRAPPOLA_NAME + ".id WHERE id = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery(sql)){
				if (rs.next()) {
					fillBean(Trappola, rs);
				}
			}
		}
		return Trappola;
	}
	
	@Override
	//per avere list<TrappolaBean> fare casting
	public synchronized List<CartaBean> retrieveAll() throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + TRAPPOLA_NAME + " ON " + CARTA_NAME + ".id = " + TRAPPOLA_NAME + ".id ";
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					TrappolaBean carta = new TrappolaBean();
					fillBean(carta, rs);
					list.add(carta);
				}
			}
		}
		return list;
	}
	
	@Override
	//per avere list<TrappolaBean> fare casting
	public synchronized List<CartaBean> retrieveAll(int limit, int page) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + TRAPPOLA_NAME + " ON " + CARTA_NAME + ".id = " + TRAPPOLA_NAME + ".id LIMIT " + limit + " OFFSET " + page*limit;
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					TrappolaBean carta = new TrappolaBean();
					fillBean(carta, rs);
					list.add(carta);
				}
			}
		}
		return list;
	}

	@Override
	//per avere list<TrappolaBean> fare casting
	public synchronized List<CartaBean> retrieveFiltered(CartaBean carta) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + TRAPPOLA_NAME + " ON " + CARTA_NAME + ".id = " + TRAPPOLA_NAME + ".id ");
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
					TrappolaBean bean = new TrappolaBean();
					fillBean(bean, rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}
	
	@Override
	public synchronized List<TrappolaBean> retrieveFiltered(TrappolaBean Trappola) throws SQLException {
		ArrayList<TrappolaBean> list = new ArrayList<TrappolaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + TRAPPOLA_NAME + " ON " + CARTA_NAME + ".id = " + TRAPPOLA_NAME + ".id ");
		ArrayList<String> params = new ArrayList<String>();
		
		buildCartaFilter(sql, params, Trappola);
				
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString())
				){
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i+1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					TrappolaBean bean = new TrappolaBean();
					fillBean(bean, rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}
	
	@Override
	//per avere list<TrappolaBean> fare casting
	public synchronized List<CartaBean> retrieveFiltered(CartaBean carta, int limit, int page) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + TRAPPOLA_NAME + " ON " + CARTA_NAME + ".id = " + TRAPPOLA_NAME + ".id ");
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
					TrappolaBean bean = new TrappolaBean();
					fillBean(bean, rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}

	@Override
	public synchronized List<TrappolaBean> retrieveFiltered(TrappolaBean Trappola, int limit, int page) throws SQLException {
		ArrayList<TrappolaBean> list = new ArrayList<TrappolaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia FROM " + CARTA_NAME + " JOIN " + TRAPPOLA_NAME + " ON " + CARTA_NAME + ".id = " + TRAPPOLA_NAME + ".id ");
		ArrayList<String> params = new ArrayList<String>();
		
		buildCartaFilter(sql, params, Trappola);
		
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
					TrappolaBean bean = new TrappolaBean();
					fillBean(bean, rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}

	@Override
	public synchronized boolean changeTipologia(TrappolaBean Trappola) throws SQLException {
		String sql = "UPDATE " + TRAPPOLA_NAME + " SET tipologia = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, Trappola.getTipologia());
        	ps.setInt(2, Trappola.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	private void fillBean(TrappolaBean Trappola, ResultSet rs) throws SQLException {
		super.fillBean(Trappola, rs);
		Trappola.setTipologia(rs.getString("tipologia"));
	}
	
	protected boolean buildCartaFilter(StringBuilder filter, ArrayList<String> params, TrappolaBean Trappola) {
		boolean first = super.buildCartaFilter(filter, params, Trappola);
		if (!Trappola.getTipologia().equals("")) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" tipologia LIKE ? ");
			params.add("%"+Trappola.getTipologia()+"%");
		}
		return first;
	}
	
}
