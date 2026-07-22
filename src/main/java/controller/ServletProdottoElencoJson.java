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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

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
 * Servlet implementation class ServletProdottoNomeJson
 */
@WebServlet("/servletProdottoElencoJson")
public class ServletProdottoElencoJson extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ProdottoDao prodottoDAO = null;
	private ProdottoYGODao prodottoYGODAO = null;
	private ConfezionatoDao confezionatoDAO = null;
	private CartaSingolaDao cartasingolaDAO = null;
	private DeckDao deckDAO = null;
	private TinDao tinDAO = null;
	private StructureDeckDao structureDAO = null;
	private PacchettoDao pacchettoDAO = null;
	private BoxDao boxDAO = null;
	private CartaDao cartaDAO = null;
	private ContieneDeckDao contieneDAO = null;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        prodottoDAO = new ProdottoDaoImpl(ds);
        prodottoYGODAO = new ProdottoYGODaoImpl(ds);
        cartasingolaDAO = new CartaSingolaDaoImpl(ds);
        confezionatoDAO = new ConfezionatoDaoImpl(ds);
        deckDAO = new DeckDaoImpl(ds);
        structureDAO = new StructureDeckDaoImpl(ds);
        tinDAO = new TinDaoImpl(ds);
        boxDAO = new BoxDaoImpl(ds);
        pacchettoDAO = new PacchettoDaoImpl(ds);
        cartaDAO = new CartaDaoImpl(ds);
        contieneDAO = new ContieneDeckDaoImpl(ds);		
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProdottoElencoJson() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	String nome = request.getParameter("nome");
    	try {
    		ProdottoBean p = prodottoDAO.retrieveByNome(nome);
    		int type = prodottoDAO.prodottoType(p.getId());
    		if (type == 0) {
    			JSONObject obj = new JSONObject();
    			obj.put("tipo", "prodotto");
				obj.put("id", p.getId());
				obj.put("nome", p.getNome());
				obj.put("qnt", p.getQnt());
				obj.put("prezzo", p.getPrezzo());
				obj.put("descrizione", p.getDescrizione());
				obj.put("sconto", p.getSconto());
				obj.put("pathImg", p.getPathImg());
				obj.put("mimeType",p.getMimeType());
				out.print(obj.toString());
    		} else if (type == 1) {
    			CartaSingolaBean c = cartasingolaDAO.retrieveByKey(p.getId());
    			JSONObject obj = new JSONObject();
    			obj.put("tipo", "carta");
				obj.put("id", c.getId());
				obj.put("nome", c.getNome());
				obj.put("qnt", c.getQnt());
				obj.put("prezzo", c.getPrezzo());
				obj.put("descrizione", c.getDescrizione());
				obj.put("sconto", c.getSconto());
				obj.put("pathImg", c.getPathImg());
				obj.put("mimeType",c.getMimeType());
				obj.put("lingua", c.getLingua());
				obj.put("quality", c.getQuality());
				obj.put("idSet", c.getIdSet());
				obj.put("idCarta", c.getIdCarta());
				out.print(obj.toString());    			
    		} else if (type == 2) {
    			ProdottoYGOBean y = prodottoYGODAO.retrieveByKey(p.getId());
    			JSONObject obj = new JSONObject();
				obj.put("id", y.getId());
				obj.put("nome", y.getNome());
				obj.put("qnt", y.getQnt());
				obj.put("prezzo", y.getPrezzo());
				obj.put("descrizione", y.getDescrizione());
				obj.put("sconto", y.getSconto());
				obj.put("pathImg", y.getPathImg());
				obj.put("mimeType",y.getMimeType());
				obj.put("lingua", y.getLingua());    	
				out.print(obj.toString());
    		} else if (type == 3) {
    			ConfezionatoBean conf = confezionatoDAO.retrieveByKey(p.getId());
    			JSONObject obj = new JSONObject();
				obj.put("id", conf.getId());
				obj.put("nome", conf.getNome());
				obj.put("qnt", conf.getQnt());
				obj.put("prezzo", conf.getPrezzo());
				obj.put("descrizione", conf.getDescrizione());
				obj.put("sconto", conf.getSconto());
				obj.put("pathImg", conf.getPathImg());
				obj.put("mimeType",conf.getMimeType());
				obj.put("lingua", conf.getLingua());
				obj.put("idSet", conf.getIdSet());
				out.print(obj.toString());
    		} else if (type == 4) {
    			PacchettoBean conf = pacchettoDAO.retrieveByKey(p.getId());
    			JSONObject obj = new JSONObject();
    			obj.put("tipo", "pacchetto");
				obj.put("id", conf.getId());
				obj.put("nome", conf.getNome());
				obj.put("qnt", conf.getQnt());
				obj.put("prezzo", conf.getPrezzo());
				obj.put("descrizione", conf.getDescrizione());
				obj.put("sconto", conf.getSconto());
				obj.put("pathImg", conf.getPathImg());
				obj.put("mimeType",conf.getMimeType());
				obj.put("lingua", conf.getLingua());
				obj.put("idSet", conf.getIdSet());
				out.print(obj.toString());
    		} else if (type == 5) {
    			TinBean conf = tinDAO.retrieveByKey(p.getId());
    			JSONObject obj = new JSONObject();
    			obj.put("tipo", "tin");
				obj.put("id", conf.getId());
				obj.put("nome", conf.getNome());
				obj.put("qnt", conf.getQnt());
				obj.put("prezzo", conf.getPrezzo());
				obj.put("descrizione", conf.getDescrizione());
				obj.put("sconto", conf.getSconto());
				obj.put("pathImg", conf.getPathImg());
				obj.put("mimeType",conf.getMimeType());
				obj.put("lingua", conf.getLingua());
				obj.put("idSet", conf.getIdSet());
				out.print(obj.toString());
    		} else if( type == 6) {
    			BoxBean conf = boxDAO.retrieveByKey(p.getId());
    			JSONObject obj = new JSONObject();
    			obj.put("tipo", "box");
				obj.put("id", conf.getId());
				obj.put("nome", conf.getNome());
				obj.put("qnt", conf.getQnt());
				obj.put("prezzo", conf.getPrezzo());
				obj.put("descrizione", conf.getDescrizione());
				obj.put("sconto", conf.getSconto());
				obj.put("pathImg", conf.getPathImg());
				obj.put("mimeType",conf.getMimeType());
				obj.put("lingua", conf.getLingua());
				obj.put("idSet", conf.getIdSet());
				out.print(obj.toString());
    		} else if (type == 7) {
    			StructureDeckBean conf = structureDAO.retrieveByKey(p.getId());
    			JSONObject obj = new JSONObject();
    			obj.put("tipo", "structure");
				obj.put("id", conf.getId());
				obj.put("nome", conf.getNome());
				obj.put("qnt", conf.getQnt());
				obj.put("prezzo", conf.getPrezzo());
				obj.put("descrizione", conf.getDescrizione());
				obj.put("sconto", conf.getSconto());
				obj.put("pathImg", conf.getPathImg());
				obj.put("mimeType",conf.getMimeType());
				obj.put("lingua", conf.getLingua());
				obj.put("idSet", conf.getIdSet());
				out.print(obj.toString());
    		} else if (type == 8) {
    			DeckBean y = deckDAO.retrieveByKey(p.getId());
    			JSONObject obj = new JSONObject();
    			JSONArray cards = new JSONArray();
    			List<ContieneDeckBean> carte = contieneDAO.retrieveByIdDeck(p.getId());
    			for(ContieneDeckBean c : carte) {
    				JSONObject oggetto = new JSONObject();
    				String nomeC = cartaDAO.retrieveByKey(c.getIdCarta()).getNomeIt();
    				oggetto.put("nome",nomeC);
    				oggetto.put("id",c.getIdCarta());
    				oggetto.put("qnt",c.getQnt());
    				cards.put(nomeC);
    			}
    			obj.put("tipo", "deck");
				obj.put("id", y.getId());
				obj.put("nome", y.getNome());
				obj.put("qnt", y.getQnt());
				obj.put("prezzo", y.getPrezzo());
				obj.put("descrizione", y.getDescrizione());
				obj.put("sconto", y.getSconto());
				obj.put("pathImg", y.getPathImg());
				obj.put("mimeType",y.getMimeType());
				obj.put("lingua", y.getLingua());    
				obj.put("carte", cards);
				out.print(obj.toString());
    		}
    	} catch(SQLException e) {
    		
    	}
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
