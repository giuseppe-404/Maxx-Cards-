package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import javax.sql.DataSource;

import model.CartaBean;
import model.MostroBean;

public class MostroDaoImpl extends CartaDaoImpl implements MostroDao {

	private static final String CARTA_NAME = "carta";
	private static final String MOSTRO_NAME = "mostro";
	private static final int BITSET_SIZE = 8;
	private DataSource ds;
	private ArrayList<String> listaTipi;
	
	public MostroDaoImpl(DataSource ds) {
		super(ds);
		this.ds=ds;
	}

	@Override
	//Consentito solo se carta è un'istanza di MostroBean, altrimenti return false
	public synchronized boolean saveCarta(CartaBean carta) throws SQLException {
		if(carta instanceof MostroBean) {
			try (Connection conn = ds.getConnection()){
				conn.setAutoCommit(false);
				try {
					super.saveCarta(carta);
					return saveMostro((MostroBean) carta);
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
	public synchronized boolean saveMostro(MostroBean mostro) throws SQLException {
		CartaBean carta = super.retrieveByKey(mostro.getId());
		if (carta.getId() == 0)
			return false;
		
		String sql = "INSERT INTO " + MOSTRO_NAME + " (id, tipologia, livello, attributo, tipo, ATK, DEF, categoria, tuner, frecce_link, scale_pendulum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, mostro.getId());
			ps.setString(2, mostro.getTipologia());
			ps.setInt(3, mostro.getLivello());
			ps.setString(4, mostro.getAttributo());
			ps.setString(5, mostro.getTipo());
			ps.setInt(6, mostro.getAtk());
			ps.setInt(7, mostro.getDef());
			ps.setString(8, mostro.getCategoria());
			ps.setBoolean(9, mostro.getTuner() > 0);
			ps.setInt(10, bitSetToInt(mostro.getFrecceLink()));
			ps.setInt(11, mostro.getScalaPendulum());
			int result = ps.executeUpdate();
			return result != 0;
		}
	}

	@Override
	public synchronized boolean deleteMostro(int id) throws SQLException {
		String sql = "DELETE FROM " + MOSTRO_NAME + " WHERE id = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			return result != 0;
		}
	}
	
	public synchronized MostroBean retrieveByKey(int id) throws SQLException {
		MostroBean mostro = new MostroBean();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia, livello, attributo, tipo, ATK, DEF, categoria, tuner, frecce_link, scala_pendulum FROM " + 
					CARTA_NAME + " JOIN " + MOSTRO_NAME + " ON " + CARTA_NAME + ".id = " + MOSTRO_NAME + ".id WHERE id = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery(sql)){
				if (rs.next()) {
					fillBean(mostro, rs);
				}
			}
		}
		return mostro;
	}
	
	public synchronized List<CartaBean> retrieveAll() throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia, livello, attributo, tipo, ATK, DEF, categoria, tuner, frecce_link, scala_pendulum FROM " + 
				CARTA_NAME + " JOIN " + MOSTRO_NAME + " ON " + CARTA_NAME + ".id = " + MOSTRO_NAME + ".id";
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MostroBean carta = new MostroBean();
					fillBean(carta, rs);
					list.add(carta);
				}
			}
		}
		return list;
	}
	
	public synchronized List<CartaBean> retrieveAll(int limit, int page) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		String sql = "SELECT " + CARTA_NAME + ".*, tipologia, livello, attributo, tipo, ATK, DEF, categoria, tuner, frecce_link, scala_pendulum FROM " + 
				CARTA_NAME + " JOIN " + MOSTRO_NAME + " ON " + CARTA_NAME + ".id = " + MOSTRO_NAME + ".id LIMIT " + limit + " OFFSET " + page*limit;
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MostroBean carta = new MostroBean();
					fillBean(carta, rs);
					list.add(carta);
				}
			}
		}
		return list;
	}
	
	@Override
	public synchronized List<CartaBean> retrieveFiltered(CartaBean carta) throws SQLException {
		ArrayList<CartaBean> lista = new ArrayList<CartaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia, livello, attributo, tipo, ATK, DEF, categoria, tuner, frecce_link, scala_pendulum FROM " + 
				CARTA_NAME + " JOIN " + MOSTRO_NAME + " ON " + CARTA_NAME + ".id = " + MOSTRO_NAME + ".id ");
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
					MostroBean mostro = new MostroBean();
					fillBean(mostro, rs);
					lista.add(mostro);
				}
			}
		}
		return lista;
	}

	@Override
	public synchronized List<MostroBean> retrieveFiltered(MostroBean mostro, int minAtk, int minDef) throws SQLException {
		ArrayList<MostroBean> lista = new ArrayList<MostroBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia, livello, attributo, tipo, ATK, DEF, categoria, tuner, frecce_link, scala_pendulum FROM " + 
				CARTA_NAME + " JOIN " + MOSTRO_NAME + " ON " + CARTA_NAME + ".id = " + MOSTRO_NAME + ".id ");
		ArrayList<String> params = new ArrayList<String>();
		
		buildCartaFilter(sql, params, mostro, minAtk, minDef);
		
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString())
				){
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i+1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					MostroBean bean = new MostroBean();
					fillBean(bean, rs);
					lista.add(bean);
				}
			}
		}
		return lista;
	}

	@Override
	public synchronized List<CartaBean> retrieveFiltered(CartaBean carta, int limit, int page) throws SQLException {
		ArrayList<CartaBean> lista = new ArrayList<CartaBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia, livello, attributo, tipo, ATK, DEF, categoria, tuner, frecce_link, scala_pendulum FROM " + 
				CARTA_NAME + " JOIN " + MOSTRO_NAME + " ON " + CARTA_NAME + ".id = " + MOSTRO_NAME + ".id ");
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
					MostroBean mostro = new MostroBean();
					fillBean(mostro, rs);
					lista.add(mostro);
				}
			}
		}
		return lista;
	}
	
	@Override
	public synchronized List<MostroBean> retrieveFiltered(MostroBean mostro, int minAtk, int minDef, int limit, int page) throws SQLException {
		ArrayList<MostroBean> lista = new ArrayList<MostroBean>();
		StringBuilder sql = new StringBuilder("SELECT " + CARTA_NAME + ".*, tipologia, livello, attributo, tipo, ATK, DEF, categoria, tuner, frecce_link, scala_pendulum FROM " + 
				CARTA_NAME + " JOIN " + MOSTRO_NAME + " ON " + CARTA_NAME + ".id = " + MOSTRO_NAME + ".id ");
		ArrayList<String> params = new ArrayList<String>();
		
		buildCartaFilter(sql, params, mostro, minAtk, minDef);
		
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
					MostroBean bean = new MostroBean();
					fillBean(bean, rs);
					lista.add(bean);
				}
			}
		}
		return lista;
	}

	@Override
	public synchronized boolean changeTipologia(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET tipologia = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, mostro.getTipologia());
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public synchronized boolean changeLivello(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET livello = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setInt(1, mostro.getLivello());
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }	
    }

	@Override
	public synchronized boolean changeAttributo(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET attributo = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, mostro.getAttributo());
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}
	
	@Override
	public synchronized boolean changeTipo(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET tipo = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, mostro.getTipo());
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public synchronized boolean changeAtk(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET ATK = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setInt(1, mostro.getAtk());
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }	
	}

	@Override
	public synchronized boolean changeDef(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET DEF = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setInt(1, mostro.getDef());
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }	
	}

	@Override
	public synchronized boolean changeCategoria(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET categoria = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, mostro.getCategoria());
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public synchronized boolean changeTuner(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET tuner = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setBoolean(1, mostro.getTuner() > 0);
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public synchronized boolean changeFrecceLink(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET frecce_link = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setInt(1, bitSetToInt(mostro.getFrecceLink()));
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public synchronized boolean changeScalaPendulum(MostroBean mostro) throws SQLException {
		String sql = "UPDATE " + MOSTRO_NAME + " SET scala_pendulum = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setInt(1, mostro.getScalaPendulum());
        	ps.setInt(2, mostro.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	private static int bitSetToInt(BitSet bs) {
		int x = 0;
		for (int i = BITSET_SIZE - 1; i >= 0; i--) {
			x <<= 1;
			if (bs.get(i))
				x += 1;
		}
		return x;
	}
	
	private static BitSet intToBitSet(int x) {
		BitSet bs = new BitSet(BITSET_SIZE);
		for (int i = 0; i < BITSET_SIZE; i++)
			bs.set(i, ((x>>i) & 1) == 1 );;
		return bs;
	}

	private void fillBean(MostroBean mostro, ResultSet rs) throws SQLException {
		super.fillBean(mostro, rs);
		mostro.setTipologia(rs.getString("tipologia"));
		mostro.setLivello(rs.getInt("livello"));
		mostro.setAttributo(rs.getString("attributo"));
		mostro.setTipo(rs.getString("tipo"));
		mostro.setAtk(rs.getInt("ATK"));
		mostro.setDef(rs.getInt("DEF"));
		mostro.setCategoria(rs.getString("categoria"));
		mostro.setTuner(rs.getInt("tuner"));
		mostro.setFrecceLink(intToBitSet(rs.getInt("frecce_link")));
		mostro.setScalaPendulum(rs.getInt("scala_pendulum"));
	}

	protected boolean buildCartaFilter(StringBuilder filter, ArrayList<String> params, MostroBean mostro, int minAtk, int minDef) {
		boolean first = super.buildCartaFilter(filter, params, mostro);
		if (!mostro.getTipologia().equals("")) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" tipologia LIKE ? ");
			params.add("%"+mostro.getTipologia()+"%");
		}
		if (!(mostro.getLivello() <= 0)) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" livello = ? ");
			params.add(Integer.toString(mostro.getLivello()));
		}
		if (!mostro.getAttributo().equals("")) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" attributo LIKE ? ");
			params.add("%"+mostro.getAttributo()+"%");
		}
		if (!mostro.getTipo().equals("")) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" tipo LIKE ? ");
			params.add("%"+mostro.getTipo()+"%");
		}
		if (!(mostro.getAtk() <= -2)) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" atk <= ? ");
			params.add(Integer.toString(mostro.getAtk()));
		}
		if (!(minAtk <= -2)) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" atk >= ? ");
			params.add(Integer.toString(minAtk));
		}
		if (!(mostro.getDef() <= -2)) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" def <= ? ");
			params.add(Integer.toString(mostro.getDef()));
		}
		if (!(minDef <= -2)) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" def >= ? ");
			params.add(Integer.toString(minDef));
		}
		if (!mostro.getCategoria().equals("")) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" categoria LIKE ? ");
			params.add("%"+mostro.getCategoria()+"%");
		}
		if (!(mostro.getTuner() <= -1)) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else
				filter.append(" AND ");
			filter.append(" tuner = ? ");
			params.add(Boolean.toString(mostro.getTuner() > 0));
		}
		if (!(mostro.getFrecceLink() == null)) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" frecce_link = ? ");
			params.add(Integer.toString(bitSetToInt(mostro.getFrecceLink())));
		}
		if (!(mostro.getScalaPendulum() <= -2)) {
			if (first) {
				first = false;
				filter.append(" WHERE ");
			}
			else 
				filter.append(" AND ");
			filter.append(" scala_pendulum = ? ");
			params.add(Integer.toString(mostro.getScalaPendulum()));
		}
		return first;
	}
}
