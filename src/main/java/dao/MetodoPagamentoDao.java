package dao;

import java.sql.SQLException;
import java.util.List;

import model.MetodoPagamentoBean;

public interface MetodoPagamentoDao {
	
	public void saveMetodoPagamento(MetodoPagamentoBean met) throws SQLException;
	
	public MetodoPagamentoBean retrieveMetodoPagamentodByKey(int id, int idUtente) throws SQLException;
	
	public List<MetodoPagamentoBean> retrieveMetodoPagamentoByIdUtente(int idUtente) throws SQLException;
	
	public boolean deleteMetodoPagamentoByKey(int id, int idUtente) throws SQLException;
	
	public boolean deleteMetodoPagamentoByIdUtente(int idUtente) throws SQLException;
	
	public boolean updateMetodoPagamento(int id, int idUtente, String met) throws SQLException;
	
}
