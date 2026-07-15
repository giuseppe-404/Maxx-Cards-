package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartaBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;

import dao.CartaDao;
import dao.CartaDaoImpl;

/**
 * Servlet implementation class ServletCercaCartaJson
 */
@WebServlet("/ServletCercaCartaJson")
public class ServletCercaCartaJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CartaDao cartaDao = null;
	
	public void init(ServletConfig config) throws ServletException {
		 super.init(config);
	     System.out.println(getServletContext().getAttributeNames());
	     DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	     if (ds == null) {
	    	 throw new ServletException("DataSource non disponibile nel contesto applicativo.");
	     }
	     cartaDao = new CartaDaoImpl(ds);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCercaCartaJson() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	String ln = null;
    	if(request.getParameter("lingua")==null || (request.getParameter("lingua") != "en" && request.getParameter("lingua")!= "jp")) 
    		ln = "it";
    	else ln = request.getParameter("lingua");
    	if(request.getParameter("nomeCarta") == null) {
    		try {
    			List<CartaBean> list = cartaDao.retrieveAll();
    			JSONArray array = new JSONArray();
        		switch(ln) {
        			case "it" : {
        				for(CartaBean c : list) {
        	    			array.put(c.getNomeIt());
        	    		} break;
        			}
        			case "en" : {
        				for(CartaBean c : list) {
        	    			array.put(c.getNomeEn());
        	    		} break;
        			}
        			case "jp" : {
        				for(CartaBean c : list) {
        	    			array.put(c.getNomeJp());
        	    		} break;
        			}
        		}
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    		
    		
    	}
    	String nome = request.getParameter("nomeCarta");
    	CartaBean filter = new CartaBean();
    	filter.setNomeIt(nome);
    	
    	try {
    		List<CartaBean> list = cartaDao.retrieveFiltered(filter);
    		JSONArray array = new JSONArray();
    		switch(ln) {
    			case "it" : {
    				for(CartaBean c : list) {
    	    			array.put(c.getNomeIt());
    	    		} break;
    			}
    			case "en" : {
    				for(CartaBean c : list) {
    	    			array.put(c.getNomeEn());
    	    		} break;
    			}
    			case "jp" : {
    				for(CartaBean c : list) {
    	    			array.put(c.getNomeJp());
    	    		} break;
    			}
    		}out.print(array.toString());
    	}catch(SQLException e) {
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
