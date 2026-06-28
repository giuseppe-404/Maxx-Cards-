package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import model.OrdineBean;

public interface OrdineDao {
	
	public boolean createOrdine(OrdineBean ordine) throws SQLException;
	
	public boolean changeStato(OrdineBean ordine) throws SQLException;
	
	public boolean changeDataAcquisto(OrdineBean ordine) throws SQLException;
	
	public boolean changeDataConsegna(OrdineBean ordine) throws SQLException;
	
	public boolean changeMetodoPagamento(OrdineBean ordine) throws SQLException;
	
	public boolean changeInfoSped(OrdineBean ordine) throws SQLException;
	
	public boolean deleteOrdine(int idOrdine) throws SQLException;
	
	public OrdineBean retrieveByKey(int idOrdine) throws SQLException;
	
	public List<OrdineBean> retrieveByIdUtente(int idUtente) throws SQLException;
	
	public List<OrdineBean> retrieveAll() throws SQLException;
	
	public List<OrdineBean> retrieveAll(int page, int limit) throws SQLException;
}
