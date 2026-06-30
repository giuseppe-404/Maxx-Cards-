package dao;

import java.sql.SQLException;
import java.util.List;

import model.UtenteBean;

public interface UtenteDao {
	public boolean createUtente(UtenteBean utente) throws SQLException;
	
	public UtenteBean retrieveByEmail(String email) throws SQLException;
	
	public boolean changeTheme(UtenteBean utente) throws SQLException;
	
	public boolean deleteAccount(int id) throws SQLException;
	
	public boolean changePassword(UtenteBean utente) throws SQLException;
	
	public boolean makeAdmin(String email, boolean isAdmin) throws SQLException;
	
	public List<UtenteBean> retrieveAll() throws SQLException;
	
	public List<UtenteBean> retrieveAll(int page, int limit) throws SQLException;
	
	public List<UtenteBean> retrieveAmministratore() throws SQLException;
	
	public List<UtenteBean> retrieveAmministratore(int page, int limit) throws SQLException;
	
}
