package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TipoBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.TipoDao;
import dao.TipoDaoImpl;

/**
 * Servlet implementation class CercaCarta
 */
@WebServlet("/cercaCarta")
public class CercaCarta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TipoDao tipoDao = null;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        tipoDao = new TipoDaoImpl(ds);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CercaCarta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<TipoBean> tipi = tipoDao.retrieveAll();
			request.setAttribute("tipi", tipi);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/cercaCarta.jsp");
			dispatcher.forward(request, response);
		} catch(SQLException e) {
			request.setAttribute("msg", "Errore nell'ottenimento dei tipi!");
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
