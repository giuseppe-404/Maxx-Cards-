package dao;

import java.sql.SQLException;
import java.util.List;

import model.MetodoPagamentoBean;

public interface MetodoPagamentoDao {
	
	public boolean saveMetodoPagamento(MetodoPagamentoBean met) throws SQLException;
	
	public MetodoPagamentoBean retrieveByKey(int id, int idUtente) throws SQLException;
	
	public List<MetodoPagamentoBean> retrieveByIdUtente(int idUtente) throws SQLException;
	
	public boolean deleteByKey(int id, int idUtente) throws SQLException;
	
	public boolean deleteByIdUtente(int idUtente) throws SQLException;
	
	public boolean updateMetodoPagamento(int id, int idUtente, String met) throws SQLException;
	
}