package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;
import model.WantsBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.WantsDao;
import dao.WantsDaoImpl;

/**
 * Servlet implementation class ServletModificaWish
 */
@WebServlet("/ServletModificaWish")
public class ServletModificaWish extends HttpServlet {
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
    public ServletModificaWish() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtenteBean utente = (UtenteBean)session.getAttribute("utente");
		try {
			if(request.getParameter("id") == null) {
				int id = Integer.parseInt(request.getParameter("id"));
				if(request.getParameter("aggiungi").equals("on")) {
					WantsBean wants = new WantsBean();
					wants.setIdUtente(utente.getId());
					wants.setIdProdotto(id);
					wantsDao.saveWants(wants);
				} else {
					wantsDao.deleteWants(utente.getId(), id);
				}
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}catch(SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
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
