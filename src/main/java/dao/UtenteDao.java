package dao;

import java.sql.SQLException;

import model.UtenteBean;

public interface UtenteDao {
	public boolean createUtente(UtenteBean utente) throws SQLException;
	
	public UtenteBean retrieveByEmail(String email) throws SQLException;
	
	public boolean changeTheme(int id, boolean darkTheme) throws SQLException;
	
	public boolean deleteAccount(int id) throws SQLException;
	
	public boolean changePassword(int id, String pwd) throws SQLException;
	
	public boolean makeAdmin(String email, boolean isAdmin) throws SQLException;
	
}
