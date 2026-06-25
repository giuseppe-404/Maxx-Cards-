package dao;

import java.sql.SQLException;
import java.util.List;

import model.TrappolaBean;

public interface TrappolaDao extends CartaDao{

	public void SaveTrappola(TrappolaBean trappola);
	
	public void DeleteTrappola(int id);
	
	public TrappolaBean RetriveByKey(int code) throws SQLException;
	
	public List<TrappolaBean> RetriveFiltered(String nome, String testo, int punteggio, String tipologia) throws SQLException;
		
	public List<TrappolaBean> RetriveFiltered(String nome, String testo, int punteggio, String tipologia, int length, int limit, int page) throws SQLException;
		
	public TrappolaBean UpdateTipologia(TrappolaBean trappola) throws SQLException;
}
