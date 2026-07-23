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
import model.ProdottoCompratoBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.ProdottoCompratoDao;
import dao.ProdottoCompratoDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

/**
 * Servlet implementation class mostraCarrello
 */
@WebServlet("/mostraCarrello")
public class MostraCarrello extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDao prodottoDao = null;
    private ProdottoCompratoDao prodottoCDao = null;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        prodottoDao = new ProdottoDaoImpl(ds);
        prodottoCDao = new ProdottoCompratoDaoImpl(ds);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostraCarrello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<ProdottoCompratoBean> prodCarrello = (List<ProdottoCompratoBean>)session.getAttribute("prodCarrello");
		if(prodCarrello == null) {
			prodCarrello = new ArrayList<ProdottoCompratoBean>();
			session.setAttribute("prodCarrello", prodCarrello);
		}
		List<ProdottoBean> prodotti = new ArrayList<>();
		try {
			for(ProdottoCompratoBean prod: prodCarrello) {
				prodotti.add(prodottoDao.retrieveByKey(prod.getIdOriginale()));
			}
			request.setAttribute("prodotti", prodotti);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/carrello.jsp");
			dispatcher.forward(request, response);
		} catch(SQLException e) {
			request.setAttribute("msg", "Errore nell'ottenimento del carrello!");
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
