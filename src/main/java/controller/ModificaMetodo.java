package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MetodoPagamentoBean;
import model.UtenteBean;
import model.WantsBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.MetodoPagamentoDao;
import dao.MetodoPagamentoDaoImpl;

/**
 * Servlet implementation class ServletModificaMetodo
 */
@WebServlet("/common/modificaMetodo")
public class ModificaMetodo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MetodoPagamentoDao metodoDao = null;
    
    public void init(ServletConfig config) throws ServletException{
		super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        metodoDao = new MetodoPagamentoDaoImpl(ds);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaMetodo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtenteBean utente = (UtenteBean)session.getAttribute("utente");
		try{
			if(request.getParameter("metodo_scelto") != null) {
				if(!request.getParameter("metodo_scelto").equals("nuovo")) {
					int id = Integer.parseInt(request.getParameter("metodo_scelto"));
					MetodoPagamentoBean filter = new MetodoPagamentoBean();
					filter.setId(id);
					filter.setIdUtente(utente.getId());
					MetodoPagamentoBean bean = metodoDao.retrieveByKey(id, utente.getId());
					if(request.getParameter("metodo") != null) {
						String nome = request.getParameter("metodoPagamento_metodo");
						if(!nome.equals(bean.getMetodo())) {
							filter.setMetodo(nome);
							metodoDao.changeMetodoPagamento(filter);
						}
					} else {
						request.setAttribute("msg", "Metodo mancante!");
						response.sendError(400);
						return;
					}
				} else {
					int id = metodoDao.retrieveByIdUtente(utente.getId()).size() + 1;
					MetodoPagamentoBean filter = new MetodoPagamentoBean();
					filter.setId(id);
					filter.setIdUtente(utente.getId());
					if(request.getParameter("metodo") != null) {
						filter.setMetodo(request.getParameter("metodo"));
					} else {
						request.setAttribute("msg", "Metodo mancante!");
						response.sendError(400);
						return;
					}
					metodoDao.saveMetodoPagamento(filter);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/common/personalPage");
					dispatcher.forward(request, response);
				} } }catch(SQLException e) {
					request.setAttribute("msg", "Errore nel salvataggio del metodo nel database!");
					response.sendError(500);
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
