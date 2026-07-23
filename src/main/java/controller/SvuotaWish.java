package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.WantsDao;
import dao.WantsDaoImpl;

/**
 * Servlet implementation class SvuotaWish
 */
@WebServlet("/svuotaWish")
public class SvuotaWish extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WantsDao wantsDao = null;
	
	public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	        System.out.println(getServletContext().getAttributeNames());
	        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	        if (ds == null) {
	            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
	        }
	        wantsDao = new WantsDaoImpl(ds);
	    }
	    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SvuotaWish() {
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
			wantsDao.deleteByIdUser(utente.getId());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
			dispatcher.forward(request, response);
		}catch(SQLException e) {
			request.setAttribute("msg","Errore nello svuotamento della wishlist!");	
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
