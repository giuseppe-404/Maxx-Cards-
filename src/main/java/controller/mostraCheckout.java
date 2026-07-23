package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.InfoSpedBean;
import model.MetodoPagamentoBean;
import model.ProdottoCompratoBean;
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.InfoSpedDao;
import dao.InfoSpedDaoImpl;
import dao.MetodoPagamentoDao;
import dao.MetodoPagamentoDaoImpl;
import dao.ProdottoCompratoDao;
import dao.ProdottoCompratoDaoImpl;

/**
 * Servlet implementation class mostraCheckout
 */
@WebServlet("/mostraCheckout")
public class mostraCheckout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InfoSpedDao infoDao = null;
	private MetodoPagamentoDao metodoDao = null;
	private ProdottoCompratoDao prodottoCDao = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	 public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	        System.out.println(getServletContext().getAttributeNames());
	        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	        if (ds == null) {
	            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
	        }
	        infoDao = new InfoSpedDaoImpl(ds);
	        metodoDao = new MetodoPagamentoDaoImpl(ds);
	        prodottoCDao = new ProdottoCompratoDaoImpl(ds);
	 }
	
    public mostraCheckout() {
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
			List<MetodoPagamentoBean> metodi = metodoDao.retrieveByIdUtente(utente.getId());
			List<InfoSpedBean> infos = infoDao.retrieveByIdUtente(utente.getId());
			List<ProdottoCompratoBean> prodCarrello = (List<ProdottoCompratoBean>) session.getAttribute("prodCarrello");
			request.setAttribute("metodi", metodi);
			request.setAttribute("infos", infos);
			request.setAttribute("prodC",prodCarrello);
		}catch(SQLException e) {
			request.setAttribute("msg", "Errore nella ricerca dei parametri dal database!");
			response.sendError(500);
		}
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/recap.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
