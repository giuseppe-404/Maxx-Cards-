package dao;

import java.sql.SQLException;
import java.util.List;

import model.MagiaBean;

public interface MagiaDao extends CartaDao{

	public void SaveMagia(MagiaBean magia) throws SQLException;
	
	public boolean DeleteMagia(int id) throws SQLException;
	
	public MagiaBean RetriveByKey(int code) throws SQLException;
	
	public List<MagiaBean> RetriveFiltered(String nome, String testo, int punteggio, String tipologia) throws SQLException;
		
	public List<MagiaBean> RetriveFiltered(String nome, String testo, int punteggio, String tipologia, int length, int limit, int page) throws SQLException;
		
	public boolean ChangeTipologia(MagiaBean magia) throws SQLException;
}
