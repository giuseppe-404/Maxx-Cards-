package dao;

import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;

import model.MostroBean;

public interface MostroDao extends CartaDao{

	public boolean saveMostro(MostroBean mostro) throws SQLException;
	
	public boolean deleteMostro(int id) throws SQLException;
	
	public MostroBean retrieveByKey(int id) throws SQLException;
	
	public List<MostroBean> retrieveFiltered(MostroBean mostro) throws SQLException;
	
	public List<MostroBean> retrieveFiltered(MostroBean mostro, int limit, int page) throws SQLException;
	
	public boolean changeTipologia(MostroBean mostro) throws SQLException;
	
	public boolean changeLivello(MostroBean mostro) throws SQLException;
	
	public boolean changeAttributo(MostroBean mostro) throws SQLException;
	
	public boolean changeTipo(MostroBean mostro) throws SQLException;
	
	public boolean changeAtk(MostroBean mostro) throws SQLException;
	
	public boolean changeDef(MostroBean mostro) throws SQLException;
	
	public boolean changeCategoria(MostroBean mostro) throws SQLException;
	
	public boolean changeTuner(MostroBean mostro) throws SQLException;
	
	public boolean changeFrecceLink(MostroBean mostro) throws SQLException;
	
	public boolean changeScalaPendulum(MostroBean mostro) throws SQLException;
}
