package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrdineBean;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.OrdineDao;
import dao.OrdineDaoImpl;

/**
 * Servlet implementation class ServletSetStatoOrdine
 */
@WebServlet("/admin/servletSetStatoOrdine")
public class ServletSetStatoOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdineDao ordineDao = null;
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        ordineDao = new OrdineDaoImpl(ds);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSetStatoOrdine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("id") == null || request.getParameter("stato") == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    		return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		String stato = request.getParameter("stato");
		try {
			OrdineBean ordine = ordineDao.retrieveByKey(id);
			ordine.setStato(stato);
			ordineDao.changeStato(ordine);
			if(request.getParameter("data") == null) {
				Date date = Date.valueOf(request.getParameter("data"));
				ordine.setDataConsegna(date);
				ordineDao.changeDataConsegna(ordine);
			}
		} catch(SQLException e) {
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
