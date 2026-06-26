package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

import model.CartaBean;

public class CartaDaoImpl implements CartaDao{

	private static final String TABLE_NAME = "carta";
	private DataSource ds;
	
	public CartaDaoImpl(DataSource ds) {
		this.ds=ds;
	}
	
	@Override
	public synchronized boolean saveCarta(CartaBean carta) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " (id, nome_it, nome_en, nome_jp, testo, path_img, mime_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, carta.getId());
			ps.setString(2, carta.getNomeIt());
			ps.setString(3, carta.getNomeEn());
			ps.setString(4, carta.getNomeJp());
			ps.setString(5, carta.getTesto());
			ps.setString(6, carta.getPathImg());
			ps.setString(7, carta.getMimeType());
			int result = ps.executeUpdate();
			return result != 0;
		}		
	}

	@Override
	public synchronized boolean deleteCarta(int id) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
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
	public synchronized CartaBean retriveByKey(int id) throws SQLException {
		CartaBean carta = new CartaBean();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
		try( 
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				){
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery(sql)){
				if (rs.next()) {
					carta=fillBean(rs);
				}
			}
		}
		return carta;
	}

	@Override
	public synchronized List<CartaBean> retrieveAll() throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		String sql = "SELECT * FROM " + TABLE_NAME;
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					CartaBean carta = fillBean(rs);
					list.add(carta);
				}
			}
		}
		return list;
	}

	@Override
	public List<CartaBean> retrieveAll(int limit, int page) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		String sql = "SELECT * FROM " + TABLE_NAME + " LIMIT " + limit + " OFFSET " + page*limit;
		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)
				){
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					CartaBean carta = fillBean(rs);
					list.add(carta);
				}
			}
		}
		return list;
	}

	@Override
	//Non utilizza nomeEn e nomeJp per la ricerca, ma solo nomeIt, che viene usato come campo nome generico e cercato in tutti e tre
	public List<CartaBean> retriveFiltered(CartaBean carta) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME);
		ArrayList<String> params = new ArrayList<String>();
		boolean first = true;
		if (!carta.getNomeIt().equals("")) {
			if (first) {
				first = false;
				sql.append(" WHERE ");
			}
			else 
				sql.append(" AND ");
			sql.append(" ( nome_it LIKE ? || nome_en LIKE ? || nome_jp LIKE ? )");
			params.add("%"+carta.getNomeIt()+"%");
			params.add("%"+carta.getNomeIt()+"%");
			params.add("%"+carta.getNomeIt()+"%");
		}
		if (!(carta.getPunteggio() == -1)) {
			if (first) {
				first = false;
				sql.append(" WHERE ");
			}
			else 
				sql.append(" AND ");
			sql.append(" punteggio <= ? ");
			params.add(Integer.toString(carta.getPunteggio()));
		}
		if (!carta.getTesto().equals("")) {
			if (first) {
				first = false;
				sql.append(" WHERE ");
			}
			else 
				sql.append(" AND ");
			sql.append(" testo LIKE ? ");
			params.add("%"+carta.getTesto()+"%");
		}

		try(
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString())
				){
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i+1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					CartaBean bean = fillBean(rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}	

	@Override
	public List<CartaBean> retriveFiltered(CartaBean carta, int limit, int page) throws SQLException {
		ArrayList<CartaBean> list = new ArrayList<CartaBean>();
		StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME);
		ArrayList<String> params = new ArrayList<String>();
		boolean first = true;
		if (!carta.getNomeIt().equals("")) {
			if (first) {
				first = false;
				sql.append(" WHERE ");
			}
			else 
				sql.append(" AND ");
			sql.append(" ( nome_it LIKE ? || nome_en LIKE ? || nome_jp LIKE ? )");
			params.add("%"+carta.getNomeIt()+"%");
			params.add("%"+carta.getNomeIt()+"%");
			params.add("%"+carta.getNomeIt()+"%");
		}
		if (!(carta.getPunteggio() == -1)) {
			if (first) {
				first = false;
				sql.append(" WHERE ");
			}
			else 
				sql.append(" AND ");
			sql.append(" punteggio <= ? ");
			params.add(Integer.toString(carta.getPunteggio()));
		}
		if (!carta.getTesto().equals("")) {
			if (first) {
				first = false;
				sql.append(" WHERE ");
			}
			else 
				sql.append(" AND ");
			sql.append(" testo LIKE ? ");
			params.add("%"+carta.getTesto()+"%");
		}
		
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
					CartaBean bean = fillBean(rs);
					list.add(bean);
				}
			}
		}
		
		return list;
	}

	@Override
	public boolean changeId(CartaBean carta, int originalId) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET id = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setInt(1, carta.getId());
        	ps.setInt(2, originalId);
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public boolean changePunteggio(CartaBean carta) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET punteggio = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setInt(1, carta.getPunteggio());
        	ps.setInt(2, carta.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public boolean changeNomeIt(CartaBean carta) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET nome_it = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, carta.getNomeIt());
        	ps.setInt(2, carta.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public boolean changeNomeEn(CartaBean carta) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET nome_en = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, carta.getNomeEn());
        	ps.setInt(2, carta.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public boolean changeNomeJp(CartaBean carta) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET nome_jp = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, carta.getNomeJp());
        	ps.setInt(2, carta.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public boolean changeTesto(CartaBean carta) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET testo = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, carta.getTesto());
        	ps.setInt(2, carta.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}

	@Override
	public boolean changeImage(CartaBean carta) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET path_img = ? , mime_type = ? WHERE id = ? ";
        try (
        		Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)
        		) {
        	ps.setString(1, carta.getNomeIt());
        	ps.setInt(2, carta.getId());
			
			int result = ps.executeUpdate();
			return result != 0;
        }
	}


	private CartaBean fillBean(ResultSet rs) throws SQLException {
		CartaBean carta = new CartaBean();
		carta.setId(rs.getInt("id"));
		carta.setNomeIt(rs.getString("nome_it"));
		carta.setNomeEn(rs.getString("nome_en"));
		carta.setNomeJp(rs.getString("nome_jp"));
		carta.setTesto(rs.getString("testo"));
		carta.setPathImg(rs.getString("path_img"));
		carta.setMimeType(rs.getString("mime_type"));
		return carta;
	}
}