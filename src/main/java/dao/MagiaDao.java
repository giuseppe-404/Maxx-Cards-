package dao;

import java.sql.SQLException;
import java.util.List;

import model.MagiaBean;

public interface MagiaDao extends CartaDao{

	public void SaveMagia(MagiaBean magia);
	
	public void DeleteMagia(int id);
	
	public MagiaBean RetriveByKey(int code) throws SQLException;
	
	public List<MagiaBean> RetriveFiltered(String nome, String testo, int punteggio, String tipologia) throws SQLException;
		
	public List<MagiaBean> RetriveFiltered(String nome, String testo, int punteggio, String tipologia, int lenght, int limit, int page) throws SQLException;
		
	public MagiaBean UpdateTipologia(MagiaBean magia) throws SQLException;
}
