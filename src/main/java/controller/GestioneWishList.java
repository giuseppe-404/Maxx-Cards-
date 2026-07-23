package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ProdottoBean;
import model.UtenteBean;
import model.WantsBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import dao.WantsDao;
import dao.WantsDaoImpl;

/**
 * Servlet implementation class GestioneWishListProdotti
 */
@WebServlet("/common/gestioneWishList")
public class GestioneWishList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WantsDao wantsDao = null;
	private ProdottoDao prodottoDao = null;
	
	public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	        System.out.println(getServletContext().getAttributeNames());
	        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	        if (ds == null) {
	            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
	        }
	        wantsDao = new WantsDaoImpl(ds);
	        prodottoDao = new ProdottoDaoImpl(ds);
	    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneWishList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    	try {
    		List<WantsBean> wants = wantsDao.retrieveByIdUtente(utente.getId());
    		List<ProdottoBean> prodotti = new ArrayList<>();
    		for(WantsBean want : wants) {
    			try {
    				ProdottoBean prod = prodottoDao.retrieveByKey(want.getIdProdotto());
    				prodotti.add(prod);
    			}catch(SQLException e) {
    				response.sendError(500,"Errore nell'ottenimento dei prodotti");
    			}	
    		}
    		request.setAttribute("prodotti", prodotti);
    		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/common/wishList.jsp");
    		dispatcher.forward(request, response);
    	} catch(SQLException ex) {
    		request.setAttribute("msg", "Errore nell'ottenimento della wishlist!");
    		response.sendError(500);
    	}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
