package dao;

import java.sql.SQLException;

import model.UtenteBean;

public interface UtenteDao {
	public void createUtente(UtenteBean utente) throws SQLException;
	
	public UtenteBean retrieveUtente(String email) throws SQLException;
	
	public boolean setTheme(int id, boolean darkTheme) throws SQLException;
	
	public boolean deleteAccount(int id) throws SQLException;
	
	public boolean changePassword(int id, String pwd) throws SQLException;
	
	public boolean makeAdmin(String email) throws SQLException;
	
}
