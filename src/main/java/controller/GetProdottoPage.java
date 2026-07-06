package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartaSingolaBean;
import model.ConfezionatoBean;
import model.ProdottoBean;
import model.ProdottoYGOBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.CartaSingolaDao;
import dao.CartaSingolaDaoImpl;
import dao.ConfezionatoDao;
import dao.ConfezionatoDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import dao.ProdottoYGODao;
import dao.ProdottoYGODaoImpl;

/**
 * Servlet implementation class getProdottoPage
 */
@WebServlet("/GetProdottoPage")
public class GetProdottoPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDao daoProd;
    private ProdottoYGODao daoPYGO;
    private ConfezionatoDao daoConf;
    private CartaSingolaDao daoCarta;

    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        daoProd = new ProdottoDaoImpl(ds);
        daoPYGO = new ProdottoYGODaoImpl(ds);
        daoConf = new ConfezionatoDaoImpl(ds);
        daoCarta = new CartaSingolaDaoImpl(ds);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProdottoPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = (int)request.getAttribute("id");
		try {
			int tipo = daoProd.prodottoType(id);
			switch(tipo){
				case 1: {
					CartaSingolaBean prodotto = daoCarta.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraCartaSingola.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 2: {
					ProdottoYGOBean prodotto = daoPYGO.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraProdottoYGO.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 3: {
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraConfezionato.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 4:{
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraPacchetto.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 5: {
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraTin.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 6 : {
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraBox.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 7 : {
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraStructureDeck.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 8 : {
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraDeck.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 0 : {
					ProdottoBean prodotto = daoProd.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraProdotto.jsp");
					request.setAttribute("bean", prodotto);
					dispatcher.forward(request, response);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
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
