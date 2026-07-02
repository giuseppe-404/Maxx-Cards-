package dao;

import java.sql.SQLException;
import java.util.List;

import model.StructureDeckBean;

public interface StructureDeckDao extends ConfezionatoDao {
	
	public boolean saveProdotto(StructureDeckBean sdeck) throws SQLException;
	
	public boolean saveProdottoYGO(StructureDeckBean sdeck) throws SQLException;
	
	public boolean saveConfezionato(StructureDeckBean sdeck) throws SQLException;
	
	public boolean saveStructureDeck(StructureDeckBean sdeck) throws SQLException;
	
	public boolean deleteStructureDeck(int id) throws SQLException;
	
	public StructureDeckBean retrieveByKey(int id) throws SQLException;
	
	public List<StructureDeckBean> retrieveFiltered(StructureDeckBean sdeck) throws SQLException;
	
	public List<StructureDeckBean> retrieveFiltered(StructureDeckBean sdeck, int page, int limit) throws SQLException;
}
