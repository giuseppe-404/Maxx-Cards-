package dao;

import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;

import model.MostroBean;

public interface MostroDao extends CartaDao{

	public void SaveMostro(MostroBean mostro) throws SQLException;
	
	public boolean DeleteMostro(int id) throws SQLException;
	
	public MostroBean RetrieveByKey(int id) throws SQLException;
	
	public List<MostroBean> RetrieveFiltered(String nome, String testo, int punteggio, 
			String tipologia, int livello, String attributo, int atk, int def, String categoria,
			boolean tuner, BitSet frecce_link, int scala_pendulum) throws SQLException;
	
	public List<MostroBean> RetrieveFiltered(String nome, String testo, int punteggio, 
			String tipologia, int livello, String attributo, int atk, int def, String categoria,
			boolean tuner, BitSet frecceLink, int scalaPendulum, int length, int limit, int page) throws SQLException;
	
	public boolean ChangeTipologia(MostroBean mostro) throws SQLException;
	
	public boolean ChangeLivello(MostroBean mostro) throws SQLException;
	
	public boolean ChangeAttributo(MostroBean mostro) throws SQLException;
	
	public boolean ChangeAtk(MostroBean mostro) throws SQLException;
	
	public boolean ChangeDef(MostroBean mostro) throws SQLException;
	
	public boolean ChangeCategoria(MostroBean mostro) throws SQLException;
	
	public boolean ChangeTuner(MostroBean mostro) throws SQLException;
	
	public boolean ChangeFrecceLink(MostroBean mostro) throws SQLException;
	
	public boolean ChangeScalaPendulum(MostroBean mostro) throws SQLException;
}
