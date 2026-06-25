package dao;

import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;

import model.MostroBean;

public interface MostroDao extends CartaDao{

	public void saveMostro(MostroBean mostro) throws SQLException;
	
	public boolean deleteMostro(int id) throws SQLException;
	
	public MostroBean retrieveByKey(int id) throws SQLException;
	
	public List<MostroBean> retrieveFiltered(String nome, String testo, int punteggio, 
			String tipologia, int livello, String attributo, int atk, int def, String categoria,
			boolean tuner, BitSet frecce_link, int scala_pendulum) throws SQLException;
	
	public List<MostroBean> retrieveFiltered(String nome, String testo, int punteggio, 
			String tipologia, int livello, String attributo, int atk, int def, String categoria,
			boolean tuner, BitSet frecceLink, int scalaPendulum, int length, int limit, int page) throws SQLException;
	
	public boolean changeTipologia(MostroBean mostro) throws SQLException;
	
	public boolean changeLivello(MostroBean mostro) throws SQLException;
	
	public boolean changeAttributo(MostroBean mostro) throws SQLException;
	
	public boolean changeAtk(MostroBean mostro) throws SQLException;
	
	public boolean changeDef(MostroBean mostro) throws SQLException;
	
	public boolean changeCategoria(MostroBean mostro) throws SQLException;
	
	public boolean changeTuner(MostroBean mostro) throws SQLException;
	
	public boolean changeFrecceLink(MostroBean mostro) throws SQLException;
	
	public boolean changeScalaPendulum(MostroBean mostro) throws SQLException;
}
