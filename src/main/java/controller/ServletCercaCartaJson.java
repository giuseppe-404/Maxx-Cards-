package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartaBean;
import model.MagiaBean;
import model.MostroBean;
import model.TrappolaBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.CartaDao;
import dao.CartaDaoImpl;
import dao.MagiaDao;
import dao.MagiaDaoImpl;
import dao.MostroDao;
import dao.MostroDaoImpl;
import dao.TrappolaDao;
import dao.TrappolaDaoImpl;

/**
 * Servlet implementation class ServletCercaCartaJson
 */
@WebServlet("/ServletCercaCartaJson")
public class ServletCercaCartaJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CartaDao cartaDao = null;
    private MostroDao mostroDao = null;
    private MagiaDao magiaDao = null;
    private TrappolaDao trappolaDao = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCercaCartaJson() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
		 super.init(config);
	     System.out.println(getServletContext().getAttributeNames());
	     DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	     if (ds == null) {
	    	 throw new ServletException("DataSource non disponibile nel contesto applicativo.");
	     }
	     cartaDao = new CartaDaoImpl(ds);
	     mostroDao = new MostroDaoImpl(ds);
	     magiaDao = new MagiaDaoImpl(ds);
	     trappolaDao = new TrappolaDaoImpl(ds);
	}

    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	JSONArray result = new JSONArray();
    	if(request.getParameter("page") != null && request.getParameter("limit") != null) {
    		int page = Integer.parseInt(request.getParameter("page"));
    		int limit = Integer.parseInt(request.getParameter("limit"));
    		if(request.getParameter("classe") == null) {
    			CartaBean filter = new CartaBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNomeIt(request.getParameter("nome"));
    			}
    			if(request.getParameter("testo") != null) {
    				filter.setTesto(request.getParameter("test"));
    			}
    			try {
    				List<CartaBean> list = cartaDao.retrieveFiltered(filter,limit,page);
    				for(CartaBean c : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("classe", "");
    					obj.put("id", c.getId());
    					obj.put("punteggio", c.getPunteggio());
    					obj.put("nomeIt", c.getNomeIt());
    					obj.put("nomeEn", c.getNomeEn());
    					obj.put("nomeJp", c.getNomeJp());
    					obj.put("testo", c.getTesto());
    					obj.put("pathImg", c.getPathImg());
    					obj.put("mimeType", c.getMimeType());
    					result.put(obj);
    				}
    			} catch(SQLException e) {
    				e.printStackTrace();
    			}
    		} else if(request.getParameter("classe").equals("mostro")) {
    			MostroBean filter = new MostroBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNomeIt(request.getParameter("nome"));
    			}
    			if(request.getParameter("testo") != null) {
    				filter.setTesto(request.getParameter("test"));
    			}
    			if(request.getParameter("tipologia") != null) {
    				String tipol = request.getParameter("tipologia").toLowerCase();
    				if(tipol.equals("normale")) tipol = "none";
    				filter.setTipologia(tipol);
    			}
    			if(request.getParameter("livello") != null) {
    				filter.setLivello(Integer.parseInt(request.getParameter("livello")));
    			}
    			if(request.getParameter("attributo") != null) {
    				filter.setAttributo(request.getParameter("attributo"));
    			}
    			if(request.getParameter("tipo") != null) {
    				filter.setTipo(request.getParameter("tipo"));
    			}
    			if(request.getParameter("atk") != null) {
    				filter.setAtk(Integer.parseInt(request.getParameter("atk")));
    			}
    			if(request.getParameter("def") != null) {
    				filter.setDef(Integer.parseInt(request.getParameter("def")));
    			}
    			if(request.getParameter("categoria") != null) {
    				filter.setCategoria(request.getParameter("categoria"));
    			}
    			if(request.getParameter("tuner") != null) {
    				int tuner;
    				if(request.getParameter("tuner") == "true") {
    					tuner = 1;
    				} else tuner = 0;
    				filter.setTuner(tuner);
    			}
    			boolean changed = false;
    			BitSet bs = new BitSet();
    			for(int i = 1; i < 9; i++) {
    				if(!changed) changed = true;
    				String par = "freccia" + i;
    				if(request.getParameter(par) != null) {
    					bs.set(i);
    				}
    			}
    			if(changed) filter.setFrecceLink(bs);
    			
    			if(request.getParameter("scalaPendulum") != null) {
    				filter.setScalaPendulum(Integer.parseInt(request.getParameter("scalaPendulum")));
    			}
    			int minAtk = -2;
    			int minDef = -2;
    			if(request.getParameter("minAtk") != null) {
    				minAtk = Integer.parseInt(request.getParameter("minAtk"));
    			}
    			if(request.getParameter("minDef") != null) {
    				minDef = Integer.parseInt(request.getParameter("minDef"));
    			}
    			try {
    				List<MostroBean> list = mostroDao.retrieveFiltered(filter, minAtk, minDef, limit, page);
    				for(MostroBean c : list) {
    					List<Integer> frecce = new ArrayList<>();
    					for(int i = 1; i < 9; i++) {
    						if(c.getFrecceLink().get(i-1)) {
    							frecce.add(i);
    						}
    					}
    					JSONObject obj = new JSONObject();
    					obj.put("classe", "mostro");
    					obj.put("id", c.getId());
    					obj.put("punteggio", c.getPunteggio());
    					obj.put("nomeIt", c.getNomeIt());
    					obj.put("nomeEn", c.getNomeEn());
    					obj.put("nomeJp", c.getNomeJp());
    					obj.put("testo", c.getTesto());
    					obj.put("pathImg", c.getPathImg());
    					obj.put("mimeType", c.getMimeType());
    					obj.put("tipologia", c.getTipologia());
    					obj.put("livello", c.getLivello());
    					obj.put("attributo", c.getAttributo());
    					obj.put("tipo", c.getTipo());
    					obj.put("atk", c.getAtk());
    					obj.put("def", c.getDef());
    					obj.put("categoria", c.getCategoria());
    					obj.put("tuner", c.getTuner());
    					obj.put("frecceLink", frecce);
    					obj.put("scalaPendulum", c.getScalaPendulum());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				e.printStackTrace();
    			}
    		} else if(request.getParameter("classe").equals("magia")) {
    			MagiaBean filter = new MagiaBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNomeIt(request.getParameter("nome"));
    			}
    			if(request.getParameter("testo") != null) {
    				filter.setTesto(request.getParameter("testo"));
    			}
    			if(request.getParameter("tipologia") != null) {
    				String tipol = request.getParameter("tipologia").toLowerCase();
    				if(tipol.equals("normale")) tipol = "none";
    				filter.setTipologia(tipol);    			}
    			try {
    				List<MagiaBean> list = magiaDao.retrieveFiltered(filter, limit, page);
    				for(MagiaBean c : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("classe", "magia");
    					obj.put("id", c.getId());
    					obj.put("punteggio", c.getPunteggio());
    					obj.put("nomeIt", c.getNomeIt());
    					obj.put("nomeEn", c.getNomeEn());
    					obj.put("nomeJp", c.getNomeJp());
    					obj.put("testo", c.getTesto());
    					obj.put("pathImg", c.getPathImg());
    					obj.put("mimeType", c.getMimeType());
    					obj.put("tipologia", c.getTipologia());
    					result.put(obj);
    				}
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		} else if(request.getParameter("classe").equals("trappola")) {
    			TrappolaBean filter = new TrappolaBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNomeIt(request.getParameter("nome"));
    			}
    			if(request.getParameter("testo") != null) {
    				filter.setTesto(request.getParameter("testo"));
    			}
    			if(request.getParameter("tipologia") != null) {
    				String tipol = request.getParameter("tipologia").toLowerCase();
    				if(tipol.equals("normale")) tipol = "none";
    				filter.setTipologia(tipol);    			}
    			try {
    				List<TrappolaBean> list = trappolaDao.retrieveFiltered(filter, limit, page);
    				for(TrappolaBean c : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("classe", "trappola");
    					obj.put("id", c.getId());
    					obj.put("punteggio", c.getPunteggio());
    					obj.put("nomeIt", c.getNomeIt());
    					obj.put("nomeEn", c.getNomeEn());
    					obj.put("nomeJp", c.getNomeJp());
    					obj.put("testo", c.getTesto());
    					obj.put("pathImg", c.getPathImg());
    					obj.put("mimeType", c.getMimeType());
    					obj.put("tipologia", c.getTipologia());
    					result.put(obj);
    				}
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
    	}else {   	
    		if(request.getParameter("classe") == null) {
    			CartaBean filter = new CartaBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNomeIt(request.getParameter("nome"));
    			}
    			if(request.getParameter("testo") != null) {
    				filter.setTesto(request.getParameter("test"));
    			}
    			try {
    				List<CartaBean> list = cartaDao.retrieveFiltered(filter);
    				for(CartaBean c : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", c.getId());
    					obj.put("punteggio", c.getPunteggio());
    					obj.put("nomeIt", c.getNomeIt());
    					obj.put("nomeEn", c.getNomeEn());
    					obj.put("nomeJp", c.getNomeJp());
    					obj.put("testo", c.getTesto());
    					obj.put("pathImg", c.getPathImg());
    					obj.put("mimeType", c.getMimeType());
    					result.put(obj);
    				}
    			} catch(SQLException e) {
    				e.printStackTrace();
    			}
    		} else if(request.getParameter("classe").equals("mostro")) {
    			MostroBean filter = new MostroBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNomeIt(request.getParameter("nome"));
    			}
    			if(request.getParameter("testo") != null) {
    				filter.setTesto(request.getParameter("test"));
    			}
    			if(request.getParameter("tipologia") != null) {
    				String tipol = request.getParameter("tipologia").toLowerCase();
    				if(tipol.equals("normale")) tipol = "none";
    				filter.setTipologia(tipol);
    			}
    			if(request.getParameter("livello") != null) {
    				filter.setLivello(Integer.parseInt(request.getParameter("livello")));
    			}
    			if(request.getParameter("attributo") != null) {
    				filter.setAttributo(request.getParameter("attributo"));
    			}
    			if(request.getParameter("tipo") != null) {
    				filter.setTipo(request.getParameter("tipo"));
    			}
    			if(request.getParameter("atk") != null) {
    				filter.setAtk(Integer.parseInt(request.getParameter("atk")));
    			}
    			if(request.getParameter("def") != null) {
    				filter.setDef(Integer.parseInt(request.getParameter("def")));
    			}
    			if(request.getParameter("categoria") != null) {
    				filter.setCategoria(request.getParameter("categoria"));
    			}
    			if(request.getParameter("tuner") != null) {
    				int tuner;
    				if(request.getParameter("tuner") == "true") {
    					tuner = 1;
    				} else tuner = 0;
    				filter.setTuner(tuner);
    			}
    			boolean changed = false;
    			BitSet bs = new BitSet();
    			for(int i = 1; i < 9; i++) {
    				if(!changed) changed = true;
    				String par = "freccia" + i;
    				if(request.getParameter(par) != null) {
    					bs.set(i);
    				}
    			}
    			if(changed) filter.setFrecceLink(bs);
    			
    			if(request.getParameter("scalaPendulum") != null) {
    				filter.setScalaPendulum(Integer.parseInt(request.getParameter("scalaPendulum")));
    			}
    			int minAtk = -2;
    			int minDef = -2;
    			if(request.getParameter("minAtk") != null) {
    				minAtk = Integer.parseInt(request.getParameter("minAtk"));
    			}
    			if(request.getParameter("minDef") != null) {
    				minDef = Integer.parseInt(request.getParameter("minDef"));
    			}
    			try {
    				List<MostroBean> list = mostroDao.retrieveFiltered(filter, minAtk, minDef);
    				for(MostroBean c : list) {
    					List<Integer> frecce = new ArrayList<>();
    					for(int i = 1; i < 9; i++) {
    						if(c.getFrecceLink().get(i-1)) {
    							frecce.add(i);
    						}
    					}
    					JSONObject obj = new JSONObject();
    					obj.put("id", c.getId());
    					obj.put("punteggio", c.getPunteggio());
    					obj.put("nomeIt", c.getNomeIt());
    					obj.put("nomeEn", c.getNomeEn());
    					obj.put("nomeJp", c.getNomeJp());
    					obj.put("testo", c.getTesto());
    					obj.put("pathImg", c.getPathImg());
    					obj.put("mimeType", c.getMimeType());
    					obj.put("tipologia", c.getTipologia());
    					obj.put("livello", c.getLivello());
    					obj.put("attributo", c.getAttributo());
    					obj.put("tipo", c.getTipo());
    					obj.put("atk", c.getAtk());
    					obj.put("def", c.getDef());
    					obj.put("categoria", c.getCategoria());
    					obj.put("tuner", c.getTuner());
    					obj.put("frecceLink", frecce);
    					obj.put("scalaPendulum", c.getScalaPendulum());
    					result.put(obj);
    				}
    			}catch(SQLException e) {
    				e.printStackTrace();
    			}
    		} else if(request.getParameter("classe").equals("magia")) {
    			MagiaBean filter = new MagiaBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNomeIt(request.getParameter("nome"));
    			}
    			if(request.getParameter("testo") != null) {
    				filter.setTesto(request.getParameter("testo"));
    			}
    			if(request.getParameter("tipologia") != null) {
    				String tipol = request.getParameter("tipologia").toLowerCase();
    				if(tipol.equals("normale")) tipol = "none";
    				filter.setTipologia(tipol);    			}
    			try {
    				List<MagiaBean> list = magiaDao.retrieveFiltered(filter);
    				for(MagiaBean c : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", c.getId());
    					obj.put("punteggio", c.getPunteggio());
    					obj.put("nomeIt", c.getNomeIt());
    					obj.put("nomeEn", c.getNomeEn());
    					obj.put("nomeJp", c.getNomeJp());
    					obj.put("testo", c.getTesto());
    					obj.put("pathImg", c.getPathImg());
    					obj.put("mimeType", c.getMimeType());
    					obj.put("tipologia", c.getTipologia());
    					result.put(obj);
    				}
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		} else if(request.getParameter("classe").equals("trappola")) {
    			TrappolaBean filter = new TrappolaBean();
    			if(request.getParameter("nome") != null) {
    				filter.setNomeIt(request.getParameter("nome"));
    			}
    			if(request.getParameter("testo") != null) {
    				filter.setTesto(request.getParameter("testo"));
    			}
    			if(request.getParameter("tipologia") != null) {
    				String tipol = request.getParameter("tipologia").toLowerCase();
    				if(tipol.equals("normale")) tipol = "none";
    				filter.setTipologia(tipol);    			}
    			try {
    				List<TrappolaBean> list = trappolaDao.retrieveFiltered(filter);
    				for(TrappolaBean c : list) {
    					JSONObject obj = new JSONObject();
    					obj.put("id", c.getId());
    					obj.put("punteggio", c.getPunteggio());
    					obj.put("nomeIt", c.getNomeIt());
    					obj.put("nomeEn", c.getNomeEn());
    					obj.put("nomeJp", c.getNomeJp());
    					obj.put("testo", c.getTesto());
    					obj.put("pathImg", c.getPathImg());
    					obj.put("mimeType", c.getMimeType());
    					obj.put("tipologia", c.getTipologia());
    					result.put(obj);
    				}
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
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
