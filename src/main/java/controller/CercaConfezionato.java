package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CSetBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.CSetDao;
import dao.CSetDaoImpl;

/**
 * Servlet implementation class CercaConfezionato
 */
@WebServlet("/cercaConfezionato")
public class CercaConfezionato extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CSetDao setDao = null;
    
    
    public void init(ServletConfig config) throws ServletException{
		super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        setDao = new CSetDaoImpl(ds);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CercaConfezionato() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			List<CSetBean> sets = setDao.retrieveAll();
			request.setAttribute("sets", sets);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/cercaConfezionato.jsp");
			dispatcher.forward(request, response);
		} catch(SQLException e) {
			request.setAttribute("msg", "Errore nell'ottenimento dei set!");
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
