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
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.InfoSpedDao;
import dao.InfoSpedDaoImpl;

/**
 * Servlet implementation class ModificaInfoSped
 */
@WebServlet("/common/modificaInfoSped")
public class ModificaInfoSped extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private InfoSpedDao infoDao = null;
    
    public void init(ServletConfig config) throws ServletException{
		super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        infoDao = new InfoSpedDaoImpl(ds);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaInfoSped() {
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
			if(request.getParameter("infoSped_id") != null) {
				int id = Integer.parseInt(request.getParameter("info_id"));
				InfoSpedBean bean = infoDao.retrieveByKey(id, utente.getId());
				InfoSpedBean filter = new InfoSpedBean();
				filter.setId(id);
				filter.setIdUtente(utente.getId());
				if(request.getParameter("info_nome") != null) {
					String nome = request.getParameter("info_nome");
					if(!nome.equals(bean.getNome())) {
						filter.setNome(nome);
						infoDao.changeNome(filter);
					}
				}
				if(request.getParameter("info_cognome") != null) {
					String cognome = request.getParameter("info_cognome");
					if(!cognome.equals(bean.getCognome())) {
						filter.setCognome(cognome);
						infoDao.changeCognome(filter);
					}
				}
				if(request.getParameter("info_via") != null) {
					String via = request.getParameter("info_via");
					if(!via.equals(bean.getVia())) {
						filter.setVia(via);
						infoDao.changeVia(filter);
					}
				}
				if(request.getParameter("info_civico") != null) {
					int civico = Integer.parseInt(request.getParameter("info_civico"));
					if(civico != bean.getCivico()) {
						filter.setCivico(civico);
						infoDao.changeCivico(filter);
					}
				}
				if(request.getParameter("info_cap") != null) {
					int cap = Integer.parseInt(request.getParameter("info_cap"));
					if(cap != bean.getCap()) {
						filter.setCap(cap);
						infoDao.changeCAP(filter);
					}
				}
			} else {
				int id = infoDao.retrieveByIdUtente(utente.getId()).size();
				InfoSpedBean nuovo = new InfoSpedBean();
				if(request.getParameter("info_nome") != null) {
					String nome = request.getParameter("info_nome");
					nuovo.setNome(nome);
				}
				if(request.getParameter("info_cognome") != null) {
					String cognome = request.getParameter("info_cognome");
					nuovo.setCognome(cognome);
				}
				if(request.getParameter("info_via") != null) {
					String via = request.getParameter("info_via");
					nuovo.setVia(via);
				}
				if(request.getParameter("info_civico") != null) {
					int civico = Integer.parseInt(request.getParameter("info_civico"));
					nuovo.setCivico(civico);
				}
				if(request.getParameter("info_cap") != null) {
					int cap = Integer.parseInt(request.getParameter("info_cap"));
					nuovo.setCap(cap);
				}
				nuovo.setIdUtente(utente.getId());
				nuovo.setId(id+1);
				infoDao.saveInfoSped(nuovo);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/common/personalPage");
				dispatcher.forward(request, response);
			}}catch(SQLException e) {
				e.printStackTrace();
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
