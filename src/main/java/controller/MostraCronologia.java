package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartaBean;
import model.CronologiaBean;
import model.ProdottoBean;
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.CartaDao;
import dao.CartaDaoImpl;
import dao.CronologiaDao;
import dao.CronologiaDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

/**
 * Servlet implementation class MostraCronologia
 */
@WebServlet("/mostraCronologia")
public class MostraCronologia extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CronologiaDao cronologiaDao = null;
    private ProdottoDao prodottoDao = null;
	private CartaDao cartaDao = null;
    
	public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	        System.out.println(getServletContext().getAttributeNames());
	        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	        if (ds == null) {
	            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
	        }
	        cronologiaDao = new CronologiaDaoImpl(ds);
	        prodottoDao = new ProdottoDaoImpl(ds);
	        cartaDao = new CartaDaoImpl(ds);
	    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostraCronologia() {
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
			List<CronologiaBean> list = cronologiaDao.retrieveByIdUtente(utente.getId(),1,10);
			List<ProdottoBean> prodotti = new ArrayList<>();
			List<CartaBean> carte = new ArrayList<>();
			try {
				for(CronologiaBean c : list) {
					if(c.isProdotto()) {
						prodotti.add(prodottoDao.retrieveByKey(c.getIdTarget()));
					}
					else {
						carte.add(cartaDao.retrieveByKey(c.getIdTarget()));
					}
				}
			}catch(SQLException e) {
				request.setAttribute("msg","Errore nell'ottenimento delle informazioni sulla cronologia!");
				response.sendError(500);
			}
			request.setAttribute("ricerche", list);
			request.setAttribute("carte", carte);
			request.setAttribute("prodotti", prodotti);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("");
			dispatcher.forward(request, response);
		} catch(SQLException e) {
			response.sendError(500,"Errore nell'ottenimento della cronologia!");
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
