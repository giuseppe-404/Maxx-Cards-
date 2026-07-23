package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.InfoSpedBean;
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.InfoSpedDao;
import dao.InfoSpedDaoImpl;

/**
 * Servlet implementation class GestioneInfoSped
 */
@WebServlet("/gestioneInfoSped")
public class GestioneInfoSped extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private InfoSpedDao infoSpedDao = null;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        infoSpedDao = new InfoSpedDaoImpl(ds);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneInfoSped() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteBean utente = (UtenteBean)request.getSession().getAttribute("utente");
		try {
			List<InfoSpedBean> infoSped = infoSpedDao.retrieveByIdUtente(utente.getId());
			request.setAttribute("info", infoSped);
			RequestDispatcher dispatcher = request.getRequestDispatcher("");
			dispatcher.forward(request, response);
		} catch(SQLException e) {
			request.setAttribute("msg", "Errore nell'ottenimento delle informazioni di spedizione dal database!");
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
