package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import model.OrdineBean;

public interface OrdineDao {
	
	public boolean createOrdine(OrdineBean ordine) throws SQLException;
	
	public boolean changeStato(int idOrdine, String stato) throws SQLException;
	
	public boolean changeDataAcquisto(int idOrdine, Date acquisto) throws SQLException;
	
	public boolean changeDataConsegna(int idOrdine, Date consegna) throws SQLException;
	
	public boolean changeMetodoPagamento(int idOrdine, int idMetodo) throws SQLException;
	
	public boolean changeInfoSped(int idOrdine, int idInfoSped) throws SQLException;
	
	public boolean deleteOrdine(int idOrdine) throws SQLException;
	
	public OrdineBean retrieveByKey(int idOrdine) throws SQLException;
	
	public List<OrdineBean> retrieveByIdUtente(int idUtente) throws SQLException;
	
	public List<OrdineBean> retrieveAll() throws SQLException;
	
	public List<OrdineBean> retrieveAll(int page, int limit) throws SQLException;
}
