package dao;

import java.sql.SQLException;
import java.util.List;

import model.TrappolaBean;

public interface TrappolaDao extends CartaDao{

	public void SaveTrappola(TrappolaBean trappola) throws SQLException;
	
	public boolean DeleteTrappola(int id) throws SQLException;
	
	public TrappolaBean RetriveByKey(int code) throws SQLException;
	
	public List<TrappolaBean> RetriveFiltered(String nome, String testo, int punteggio, String tipologia) throws SQLException;
		
	public List<TrappolaBean> RetriveFiltered(String nome, String testo, int punteggio, String tipologia, int length, int limit, int page) throws SQLException;
		
	public boolean ChangeTipologia(TrappolaBean trappola) throws SQLException;
}
