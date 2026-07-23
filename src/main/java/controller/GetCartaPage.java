package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartaSingolaBean;
import model.MagiaBean;
import model.MostroBean;
import model.TrappolaBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.CartaDao;
import dao.CartaDaoImpl;
import dao.CartaSingolaDao;
import dao.MagiaDao;
import dao.MagiaDaoImpl;
import dao.MostroDao;
import dao.MostroDaoImpl;
import dao.TrappolaDao;
import dao.TrappolaDaoImpl;

/**
 * Servlet implementation class GetCartaPage
 */
@WebServlet("/getCartaPage")
public class GetCartaPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CartaDao cartaDao = null;
    private MagiaDao magiaDao = null;
    private TrappolaDao trappolaDao = null;
    private MostroDao mostroDao = null;
    private CartaSingolaDao cartaSingolaDao = null;
    
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        cartaDao = new CartaDaoImpl(ds);
        mostroDao = new MostroDaoImpl(ds);
        magiaDao = new MagiaDaoImpl(ds);
        trappolaDao = new TrappolaDaoImpl(ds);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCartaPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("id") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
			dispatcher.forward(request, response);
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			int type = cartaDao.cartaType(id);
			if(type == 1) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mostraMostro.jsp");
				CartaSingolaBean filter = new CartaSingolaBean();
				filter.setIdCarta(id);
				filter.setQnt(0);
				MostroBean mostro = mostroDao.retrieveByKey(id);
				List<CartaSingolaBean> prodotti = cartaSingolaDao.retrieveFiltered(filter,1,16);
				request.setAttribute("mostro", mostro);
				request.setAttribute("prodotti", prodotti);
				dispatcher.forward(request, response);
			} else if(type == 2) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mostraMagia.jsp");
				CartaSingolaBean filter = new CartaSingolaBean();
				filter.setIdCarta(id);
				filter.setQnt(0);
				MagiaBean magia = magiaDao.retrieveByKey(id);
				List<CartaSingolaBean> prodotti = cartaSingolaDao.retrieveFiltered(filter,1,16);
				request.setAttribute("magia",magia);
				request.setAttribute("prodotti", prodotti);
				dispatcher.forward(request, response);
			} else if(type == 3) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mostraTrappola.jsp");
				CartaSingolaBean filter = new CartaSingolaBean();
				filter.setIdCarta(id);
				filter.setQnt(0);
				TrappolaBean trappola = trappolaDao.retrieveByKey(id);
				List<CartaSingolaBean> prodotti = cartaSingolaDao.retrieveFiltered(filter,1,16);
				request.setAttribute("trappola", trappola);
				request.setAttribute("prodotti", prodotti);
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
				dispatcher.forward(request, response);
				return;
			}
		} catch (SQLException e) {
			response.sendError(500,"Errore nell'ottenimento della pagina giusta!");
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
