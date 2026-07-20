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
import java.util.List;

import javax.sql.DataSource;

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
 * Servlet implementation class ServletCartaNomeJson
 */
@WebServlet("/ServletCartaNomeJson")
public class ServletCartaNomeJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CartaDao cartaDao = null;
    private MostroDao mostroDao = null;
    private MagiaDao magiaDao = null;
    private TrappolaDao trappolaDao = null;
    
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
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCartaNomeJson() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	String nome = request.getParameter("nome");
    	try {
    		CartaBean carta = cartaDao.retrieveByNome(nome);
    		int temp = cartaDao.cartaType(carta.getId());
    		switch(temp) {
    			case 0:{
    				JSONObject obj = new JSONObject();
					obj.put("classe", "");
					obj.put("id", carta.getId());
					obj.put("punteggio", carta.getPunteggio());
					obj.put("nomeIt", carta.getNomeIt());
					obj.put("nomeEn", carta.getNomeEn());
					obj.put("nomeJp", carta.getNomeJp());
					obj.put("testo", carta.getTesto());
					obj.put("pathImg", carta.getPathImg());
					obj.put("mimeType", carta.getMimeType());
					out.print(obj.toString());
					break;
    			}
    			case 1:{
    				MostroBean mostro = mostroDao.retrieveByKey(carta.getId());
    				List<Integer> frecce = new ArrayList<>();
					for(int i = 1; i < 9; i++) {
						if(mostro.getFrecceLink().get(i-1)) {
							frecce.add(i);
						}
					}
    				JSONObject obj2 = new JSONObject();
					obj2.put("classe", "mostro");
					obj2.put("id", mostro.getId());
					obj2.put("punteggio", mostro.getPunteggio());
					obj2.put("nomeIt", mostro.getNomeIt());
					obj2.put("nomeEn", mostro.getNomeEn());
					obj2.put("nomeJp", mostro.getNomeJp());
					obj2.put("testo", mostro.getTesto());
					obj2.put("pathImg", mostro.getPathImg());
					obj2.put("mimeType", mostro.getMimeType());
					obj2.put("tipologia", mostro.getTipologia());
					obj2.put("livello", mostro.getLivello());
					obj2.put("attributo", mostro.getAttributo());
					obj2.put("tipo", mostro.getTipo());
					obj2.put("atk", mostro.getAtk());
					obj2.put("def", mostro.getDef());
					obj2.put("categoria", mostro.getCategoria());
					obj2.put("tuner", mostro.getTuner());
					obj2.put("frecceLink", frecce);
					obj2.put("scalaPendulum", mostro.getScalaPendulum());
					out.print(obj2.toString());
					break;}
    			case 2:{
    				MagiaBean magia = magiaDao.retrieveByKey(carta.getId());
    				JSONObject obj3 = new JSONObject();
					obj3.put("classe", "magia");
					obj3.put("id", magia.getId());
					obj3.put("punteggio", magia.getPunteggio());
					obj3.put("nomeIt", magia.getNomeIt());
					obj3.put("nomeEn", magia.getNomeEn());
					obj3.put("nomeJp", magia.getNomeJp());
					obj3.put("testo", magia.getTesto());
					obj3.put("pathImg", magia.getPathImg());
					obj3.put("mimeType", magia.getMimeType());
					obj3.put("tipologia", magia.getTipologia());
					out.print(obj3.toString());
					break;
    			}
    			case 3: {
    				TrappolaBean trappola = trappolaDao.retrieveByKey(carta.getId());
    				JSONObject obj4 = new JSONObject();
					obj4.put("classe", "trappola");
					obj4.put("id", trappola.getId());
					obj4.put("punteggio", trappola.getPunteggio());
					obj4.put("nomeIt", trappola.getNomeIt());
					obj4.put("nomeEn", trappola.getNomeEn());
					obj4.put("nomeJp", trappola.getNomeJp());
					obj4.put("testo", trappola.getTesto());
					obj4.put("pathImg", trappola.getPathImg());
					obj4.put("mimeType", trappola.getMimeType());
					obj4.put("tipologia", trappola.getTipologia());
					out.print(obj4.toString());
					break;
    			} default : {
    				out.print("Carta non trovata");
    			}
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
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
		doGet(request, response);
	}

}
