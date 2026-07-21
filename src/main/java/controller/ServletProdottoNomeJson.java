package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

/**
 * Servlet implementation class ServletProdottoNome
 */
@WebServlet("/ServletProdottoNomeJson")
public class ServletProdottoNomeJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDao prodottoDao = null;
    
    public void init (ServletConfig config) throws ServletException {
    	super.init(config);
    	System.out.println(getServletContext().getAttributeNames());
    	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
    	if (ds == null) {
    		throw new ServletException("DataSource non disponibile nel contesto applicativo.");
    	}
    prodottoDao = new ProdottoDaoImpl(ds);
}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProdottoNomeJson() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	if(request.getParameter("page") != null && request.getParameter("limit") != null) {
    		int page = Integer.parseInt(request.getParameter("page"));
    		int limit = Integer.parseInt(request.getParameter("limit"));
    		
    		if(request.getParameter("nome") == null) {
    			try {
    				List<ProdottoBean> list = prodottoDao.retrieveAll(limit,page);
    				JSONArray array = new JSONArray();
    				for(ProdottoBean b : list) {
    					array.put(b.getNome());
    				} out.print(array.toString());
    			} catch(SQLException e) {
    				e.printStackTrace();
    			}
    		} else {
    			String nome = request.getParameter("nome");
    			ProdottoBean filter = new ProdottoBean();
    			filter.setNome(nome);
    			try {
    				List<ProdottoBean> list = prodottoDao.retrieveFiltered(filter);
    				JSONArray array = new JSONArray();
    				for(ProdottoBean b : list) {
    					array.put(b.getNome());
    				} out.print(array.toString());
    			} catch(SQLException e) {
    				e.printStackTrace();
    			}
    		}
    	} else {
    		if(request.getParameter("nome") == null) {
    			try {
    				List<ProdottoBean> list = prodottoDao.retrieveAll();
    				JSONArray array = new JSONArray();
    				for(ProdottoBean b : list) {
    					array.put(b.getNome());
    				} out.print(array.toString());
    			} catch(SQLException e) {
    				e.printStackTrace();
    			}
    		} else {
    			String nome = request.getParameter("nome");
    			ProdottoBean filter = new ProdottoBean();
    			filter.setNome(nome);
    			try {
    				List<ProdottoBean> list = prodottoDao.retrieveFiltered(filter);
    				JSONArray array = new JSONArray();
    				for(ProdottoBean b : list) {
    					array.put(b.getNome());
    				} out.print(array.toString());
    			} catch(SQLException e) {
    				e.printStackTrace();
    			}
    		}
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
