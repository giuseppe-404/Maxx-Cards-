package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrdineBean;
import model.ProdottoCompratoBean;
import model.UtenteBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONObject;

import dao.OrdineDao;
import dao.OrdineDaoImpl;
import dao.ProdottoCompratoDao;
import dao.ProdottoCompratoDaoImpl;

/**
 * Servlet implementation class ServletAggiungiCarrello
 */
@WebServlet("/servletAggiungiCarrello")
public class ServletAggiungiCarrello extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoCompratoDao prodottoCDao = null;
	private OrdineDao ordineDao = null;
       
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        ordineDao = new OrdineDaoImpl(ds);
        prodottoCDao = new ProdottoCompratoDaoImpl(ds);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAggiungiCarrello() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
    	HttpSession session = request.getSession();
    	PrintWriter out = response.getWriter();
    	int id = Integer.parseInt(request.getParameter("idProdotto"));
    	int qnt = Integer.parseInt(request.getParameter("qnt"));
    	ProdottoCompratoBean prod = new ProdottoCompratoBean();
    	prod.setIdOriginale(id);
    	prod.setQnt(qnt);
    	OrdineBean carrello = (OrdineBean)session.getAttribute("carrello");
    	try {
    		if(carrello == null) {
    			UtenteBean utente = (UtenteBean)session.getAttribute("utente");
    			if(utente != null) {
    				carrello = ordineDao.retrieveCarrello(utente.getId());
    				if(carrello == null) {
    					carrello = new OrdineBean();
        				carrello.setStato("Carrello");
        				carrello.setIdUtente(utente.getId());
        				ordineDao.createOrdine(carrello);
        				carrello = ordineDao.retrieveCarrello(utente.getId());
    				}
    			}
    			if(carrello == null) {
    				carrello = new OrdineBean();
    				carrello.setStato("Carrello");
    			}
    			session.setAttribute("carrello", carrello);
    		} 
    		boolean logged = (carrello.getIdOrdine() > 0);
    		if(logged){
    			prod.setIdOrdine(carrello.getIdOrdine());
    		}
    		List<ProdottoCompratoBean> prodScelti = (List<ProdottoCompratoBean>) session.getAttribute("prodCarrello");
    		if (prodScelti == null) {
    			prodScelti = new ArrayList<>();
    		}	
    		
    		ProdottoCompratoBean daRimuovere = null;
    		boolean presente = false;
    		
    		for(ProdottoCompratoBean p : prodScelti) {
    			if(p.getId() == prod.getIdOriginale()) {
    				if(prod.getQnt() == 0) {
    					daRimuovere = p;
    					if(logged) {
    						prodottoCDao.deleteProdottoComprato(p.getId(),carrello.getIdOrdine());
    					}
    				} else {
    					p.setQnt(prod.getQnt());
    				}
    				presente = true;
    				break;
    			}
    		}
    		if(daRimuovere != null) {
    			prodScelti.remove(daRimuovere);
    		}
    		
    		if(!presente && prod.getQnt() > 0) {
    			if(logged) {
    				prodottoCDao.saveProdottoComprato(prod);
    			}
    			prodScelti.add(prod);
    			
    		}
    		session.setAttribute("prodCarrello", prodScelti);
    		JSONObject obj = new JSONObject();
    	    obj.put("Status", true);
    	    out.print(obj.toString());
    	} catch(SQLException e) {
    		JSONObject obj = new JSONObject();
    		obj.put("Status", false);
    		out.print(obj.toString());
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
