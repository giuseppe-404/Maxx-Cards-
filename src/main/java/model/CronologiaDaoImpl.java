package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.CronologiaDao;

public class CronologiaDaoImpl implements CronologiaDao {
	private static final String TABLE_NAME_PRODOTTO= "cronologia_prodotto";
	private static final String TABLE_NAME_CARTA= "cronologia_carta";
	private DataSource ds = null;
	
	public CronologiaDaoImpl(DataSource ds) {
		this.ds=ds;
	}
	
	@Override
	public synchronized boolean saveCronologia(CronologiaBean bean) throws SQLException {
		if(bean.isProdotto()) {
			String sql1 = "INSERT INTO "+TABLE_NAME_PRODOTTO+"(idUtente,idTarget) VALUES(?,?)";
			try(Connection connection = ds.getConnection();
					PreparedStatement ps = connection.prepareStatement(sql1)){
				ps.setInt(1, bean.getIdUtente());
				ps.setInt(2, bean.getIdTarget());
				int rowUpdated = ps.executeUpdate();
				return rowUpdated != 0;
			}
		}
		else {
			String sql1 = "INSERT INTO "+TABLE_NAME_CARTA+"(idUtente,idTarget) VALUES (?,?)";
			try(Connection connection = ds.getConnection();
					PreparedStatement ps = connection.prepareStatement(sql1)){
				ps.setInt(1, bean.getIdUtente());
				ps.setInt(2, bean.getIdTarget());
				int rowUpdated = ps.executeUpdate();
				return rowUpdated != 0;
			}
		}
	}

	@Override
	public synchronized List<CronologiaBean> retrieveByIdUtente(int idUtente) throws SQLException {
		String sql = "select idUtente, idTarget,  (true) as isProdotto FROM cronologia_prodotto where idUtente = ? UNION select idUtente, idTarget,  (false) as isProdotto FROM cronologia_carta"
				+ " where idUtente = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setInt(1, idUtente);
			ps.setInt(2, idUtente);
			ResultSet rs = ps.executeQuery();
			List<CronologiaBean> list = new ArrayList<>();
			while(rs.next()) {
				CronologiaBean bean = new CronologiaBean();
				fillBean(bean,rs);
				list.add(bean);
			}
			return list;
		}
	}

	@Override
	public synchronized void deleteByIdUtente(int idUtente) throws SQLException {
		String sql1 = "DELETE FROM "+TABLE_NAME_CARTA+" WHERE idUtente = ?";
		String sql2 = "DELETE FROM "+TABLE_NAME_PRODOTTO+" WHERE idUtente = ?";
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql1)){
			ps.setInt(1, idUtente);
			ps.executeUpdate();
		}
		try(Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql2)){
			ps.setInt(1, idUtente);
			ps.executeUpdate();
		}
	}

	protected void fillBean(CronologiaBean bean, ResultSet rs) throws SQLException{
		bean.setIdTarget(rs.getInt("idTarget"));
		bean.setIdUtente(rs.getInt("idUtente"));
		bean.setProdotto(rs.getBoolean("isProdotto"));
	}
	
}
