package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BoxBean;
import model.CartaSingolaBean;
import model.ConfezionatoBean;
import model.ContieneDeckBean;
import model.DeckBean;
import model.PacchettoBean;
import model.ProdottoBean;
import model.ProdottoYGOBean;
import model.StructureDeckBean;
import model.TinBean;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.BoxDao;
import dao.BoxDaoImpl;
import dao.CartaDao;
import dao.CartaDaoImpl;
import dao.CartaSingolaDao;
import dao.CartaSingolaDaoImpl;
import dao.ConfezionatoDao;
import dao.ConfezionatoDaoImpl;
import dao.ContieneDeckDao;
import dao.ContieneDeckDaoImpl;
import dao.DeckDao;
import dao.DeckDaoImpl;
import dao.PacchettoDao;
import dao.PacchettoDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import dao.ProdottoYGODao;
import dao.ProdottoYGODaoImpl;
import dao.StructureDeckDao;
import dao.StructureDeckDaoImpl;
import dao.TinDao;
import dao.TinDaoImpl;

/**
 * Servlet implementation class ServletCercaProdottoJson
 */
@WebServlet("/servletCercaProdottoJson")
public class ServletCercaProdottoJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDao prodottoDao = null;
	private ProdottoYGODao prodottoYgoDao = null;
	private ConfezionatoDao confezionatoDao = null;
	private CartaSingolaDao cartaSingolaDao = null;
	private DeckDao deckDao = null;
	private StructureDeckDao structureDao = null;
	private TinDao tinDao = null;
	private BoxDao boxDao = null;
	private PacchettoDao pacchettoDao = null;
	private ContieneDeckDao contieneDao = null;
	private CartaDao cartaDao = null;
	
	public void init(ServletConfig config) throws ServletException {
		 super.init(config);
	     System.out.println(getServletContext().getAttributeNames());
	     DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	     if (ds == null) {
	    	 throw new ServletException("DataSource non disponibile nel contesto applicativo.");
	     }
	     prodottoDao = new ProdottoDaoImpl(ds);
	     prodottoYgoDao = new ProdottoYGODaoImpl(ds);
	     confezionatoDao = new ConfezionatoDaoImpl(ds);
	     cartaSingolaDao = new CartaSingolaDaoImpl(ds);
	     deckDao = new DeckDaoImpl(ds);
	     structureDao = new StructureDeckDaoImpl(ds);
	     tinDao = new TinDaoImpl(ds);
	     boxDao = new BoxDaoImpl(ds);
	     pacchettoDao = new PacchettoDaoImpl(ds);
	     contieneDao = new ContieneDeckDaoImpl(ds);
	     cartaDao = new CartaDaoImpl(ds);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCercaProdottoJson() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	JSONArray result = new JSONArray();
    	if(request.getParameter("idProdotto") == null || Integer.parseInt(request.getParameter("idProdotto")) == 0 ) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			ProdottoBean filter = new ProdottoBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			}
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			}
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			}
    			try {
    				List<ProdottoBean> list = prodottoDao.retrieveFiltered(filter,limit,page);
    				for(ProdottoBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		} else {
    			ProdottoBean filter = new ProdottoBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			try {
    				List<ProdottoBean> list = prodottoDao.retrieveFiltered(filter);
    				for(ProdottoBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		}
    	} else if(Integer.parseInt(request.getParameter("idProdotto")) == 2) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			ProdottoYGOBean filter = new ProdottoYGOBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			try {
    				List<ProdottoYGOBean> list = prodottoYgoDao.retrieveFiltered(filter,limit,page);
    				for(ProdottoYGOBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    			}
    		} else {
    			ProdottoYGOBean filter = new ProdottoYGOBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			try {
    				List<ProdottoYGOBean> list = prodottoYgoDao.retrieveFiltered(filter);
    				for(ProdottoYGOBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		}
    	} else if(Integer.parseInt(request.getParameter("idProdotto")) == 1) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			CartaSingolaBean filter = new CartaSingolaBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("quality") != null) {
    				filter.setQuality(request.getParameter("quality"));
    			} else filter.setQuality("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			if(request.getParameter("idCarta") != null) {
    				filter.setIdCarta(Integer.parseInt(request.getParameter("idCarta")));
    			} else filter.setIdCarta(0);
    			try {
    				List<CartaSingolaBean> list = cartaSingolaDao.retrieveFiltered(filter,limit,page);
    				for(CartaSingolaBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("quality", p.getQuality());
    					obj.put("idSet", p.getIdSet());
    					obj.put("idCarta", p.getIdCarta());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		} else {
    			CartaSingolaBean filter = new CartaSingolaBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("quality") != null) {
    				filter.setQuality(request.getParameter("quality"));
    			} else filter.setQuality("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			if(request.getParameter("idCarta") != null) {
    				filter.setIdCarta(Integer.parseInt(request.getParameter("idCarta")));
    			} else filter.setIdCarta(0);
    			try {
    				List<CartaSingolaBean> list = cartaSingolaDao.retrieveFiltered(filter);
    				for(CartaSingolaBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("quality", p.getQuality());
    					obj.put("idSet", p.getIdSet());
    					obj.put("idCarta", p.getIdCarta());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    				return;
    			} 
    		}
    	}else if(Integer.parseInt(request.getParameter("idProdotto")) == 8) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			DeckBean filter = new DeckBean();
    			
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			try {
    				List<DeckBean> list = deckDao.retrieveFiltered(filter,limit,page);
    				for(DeckBean p : list) {
    					JSONArray cards = new JSONArray();
    	    			List<ContieneDeckBean> carte = contieneDao.retrieveByIdDeck(p.getId());
    	    			for(ContieneDeckBean c : carte) {
    	    				JSONObject oggetto = new JSONObject();
    	    				String nomeC = cartaDao.retrieveByKey(c.getIdCarta()).getNomeIt();
    	    				oggetto.put("nome",nomeC);
    	    				oggetto.put("id",c.getIdCarta());
    	    				oggetto.put("qnt",c.getQnt());
    	    				cards.put(nomeC);
    	    			}
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		} else {
    			DeckBean filter = new DeckBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			try {
    				List<DeckBean> list = deckDao.retrieveFiltered(filter);
    				for(DeckBean p : list) {
    					JSONObject obj = new JSONObject();
    					JSONArray cards = new JSONArray();
    	    			List<ContieneDeckBean> carte = contieneDao.retrieveByIdDeck(p.getId());
    	    			for(ContieneDeckBean c : carte) {
    	    				JSONObject oggetto = new JSONObject();
    	    				String nomeC = cartaDao.retrieveByKey(c.getIdCarta()).getNomeIt();
    	    				oggetto.put("nome",nomeC);
    	    				oggetto.put("id",c.getIdCarta());
    	    				oggetto.put("qnt",c.getQnt());
    	    				cards.put(nomeC);
    	    			}
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    				return;
    			} }
    	} else if(Integer.parseInt(request.getParameter("idProdotto")) == 3) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			ConfezionatoBean filter = new ConfezionatoBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<ConfezionatoBean> list = confezionatoDao.retrieveFiltered(filter,limit,page);
    				for(ConfezionatoBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		} else {
    			ConfezionatoBean filter = new ConfezionatoBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<ConfezionatoBean> list = confezionatoDao.retrieveFiltered(filter);
    				for(ConfezionatoBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			} }
    	} else if(Integer.parseInt(request.getParameter("idProdotto")) == 4) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			PacchettoBean filter = new PacchettoBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<PacchettoBean> list = pacchettoDao.retrieveFiltered(filter,limit,page);
    				for(PacchettoBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		} else {
    			PacchettoBean filter = new PacchettoBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<PacchettoBean> list = pacchettoDao.retrieveFiltered(filter);
    				for(PacchettoBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			} }
    	} else if(Integer.parseInt(request.getParameter("idProdotto")) == 5) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			TinBean filter = new TinBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<TinBean> list = tinDao.retrieveFiltered(filter,limit,page);
    				for(TinBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		} else {
    			TinBean filter = new TinBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<TinBean> list = tinDao.retrieveFiltered(filter);
    				for(TinBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		}
    	} else if(Integer.parseInt(request.getParameter("idProdotto")) == 6) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			BoxBean filter = new BoxBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<BoxBean> list = boxDao.retrieveFiltered(filter,limit,page);
    				for(BoxBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		} else {
    			BoxBean filter = new BoxBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<BoxBean> list = boxDao.retrieveFiltered(filter);
    				for(BoxBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			}
    		}
    	} else if(Integer.parseInt(request.getParameter("idProdotto")) == 7) {
    		if(request.getParameter("limit") != null && request.getParameter("page") != null) {
    			int page = Integer.parseInt(request.getParameter("page"));
    			int limit = Integer.parseInt(request.getParameter("limit"));
    			StructureDeckBean filter = new StructureDeckBean();
    			if(request.getParameter("nome") != null) {
            		filter.setNome(request.getParameter("nome"));
            	} else filter.setNome("");
            	if(request.getParameter("descrizione") != null) {
            		filter.setDescrizione(request.getParameter("descrizione"));
            	} else filter.setDescrizione("");
            	if(request.getParameter("prezzo") != null) {
            		filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
            	} else filter.setPrezzo(-1);
            	filter.setQnt(-1);
            	if(request.getParameter("lingua") != null) {
            		filter.setLingua(request.getParameter("lingua"));
            	} else filter.setLingua("");
            	if(request.getParameter("idSet") != null) {
            		filter.setIdSet(request.getParameter("idSet"));
            	} else filter.setIdSet("");
            	try {
            		List<StructureDeckBean> list = structureDao.retrieveFiltered(filter,limit,page);
            		for(StructureDeckBean p : list) {
            			JSONObject obj = new JSONObject();
            			obj.put("id", p.getId());
            			obj.put("nome", p.getNome());
            			obj.put("qnt", p.getQnt());
            			obj.put("prezzo", p.getPrezzo());
            			obj.put("descrizione", p.getDescrizione());
            			obj.put("sconto", p.getSconto());
            			obj.put("pathImg", p.getPathImg());
            			obj.put("mimeType",p.getMimeType());
            			obj.put("lingua", p.getLingua());
            			obj.put("idSet", p.getIdSet());
            			result.put(obj);
            		}
            	}catch(SQLException e) {
            		JSONObject obj = new JSONObject();
            		obj.put("Status", false);
            		out.print(obj.toString());
            		return;
            	}
    		} else {
    			StructureDeckBean filter = new StructureDeckBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNome(request.getParameter("nome"));
    			} else filter.setNome("");
    			if(request.getParameter("descrizione") != null) {
    				filter.setDescrizione(request.getParameter("descrizione"));
    			} else filter.setDescrizione("");
    			if(request.getParameter("prezzo") != null) {
    				filter.setPrezzo(Integer.parseInt(request.getParameter("prezzo"))*100);
    			} else filter.setPrezzo(-1);
    			filter.setQnt(-1);
    			if(request.getParameter("lingua") != null) {
    				filter.setLingua(request.getParameter("lingua"));
    			} else filter.setLingua("");
    			if(request.getParameter("idSet") != null) {
    				filter.setIdSet(request.getParameter("idSet"));
    			} else filter.setIdSet("");
    			try {
    				List<StructureDeckBean> list = structureDao.retrieveFiltered(filter);
    				for(StructureDeckBean p : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", p.getId());
    					obj.put("nome", p.getNome());
    					obj.put("qnt", p.getQnt());
    					obj.put("prezzo", p.getPrezzo());
    					obj.put("descrizione", p.getDescrizione());
    					obj.put("sconto", p.getSconto());
    					obj.put("pathImg", p.getPathImg());
    					obj.put("mimeType",p.getMimeType());
    					obj.put("lingua", p.getLingua());
    					obj.put("idSet", p.getIdSet());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				JSONObject obj = new JSONObject();
    	    		obj.put("Status", false);
    	    		out.print(obj.toString());
    	    		return;
    			} 
    			System.out.println(result.isEmpty());
    		}
        } else {
        	result.put("Nessun risultato!");
        } out.print(result.toString());
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
