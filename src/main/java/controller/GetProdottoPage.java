package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartaBean;
import model.CartaSingolaBean;
import model.ConfezionatoBean;
import model.ContieneDeckBean;
import model.ProdottoBean;
import model.ProdottoYGOBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.CartaDao;
import dao.CartaDaoImpl;
import dao.CartaSingolaDao;
import dao.CartaSingolaDaoImpl;
import dao.ConfezionatoDao;
import dao.ConfezionatoDaoImpl;
import dao.ContieneDeckDao;
import dao.ContieneDeckDaoImpl;
import dao.DeckDao;
import dao.DeckDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import dao.ProdottoYGODao;
import dao.ProdottoYGODaoImpl;

/**
 * Servlet implementation class getProdottoPage
 */
@WebServlet("/getProdottoPage")
public class GetProdottoPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDao daoProd;
    private ProdottoYGODao daoPYGO;
    private ConfezionatoDao daoConf;
    private CartaSingolaDao daoCartaSingola;
    private CartaDao daoCarta;
    private DeckDao daoDeck;
    private ContieneDeckDao daoContiene;
    
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
        daoCartaSingola = new CartaSingolaDaoImpl(ds);
        daoDeck = new DeckDaoImpl(ds);
        daoContiene = new ContieneDeckDaoImpl(ds);
        daoCarta = new CartaDaoImpl(ds);
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
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			int tipo = daoProd.prodottoType(id);
			switch(tipo){
				case 1: {
					CartaSingolaBean prodotto = daoCartaSingola.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraCartaSingola.jsp");
					request.setAttribute("carta", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 2: {
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index");
					dispatcher.forward(request, response);
					break;
				}
				case 3: {
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index");
					dispatcher.forward(request, response);
					break;
				}
				case 4:{
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraPacchetto.jsp");
					request.setAttribute("confezionato", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 5: {
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraTin.jsp");
					request.setAttribute("confezionato", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 6 : {
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraBox.jsp");
					request.setAttribute("confezionato", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 7 : {
					ConfezionatoBean prodotto = daoConf.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraStructureDeck.jsp");
					request.setAttribute("confezionato", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 8 : {
					ProdottoYGOBean prodotto = daoPYGO.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraDeck.jsp");
					List<ContieneDeckBean> contiene = daoContiene.retrieveByIdDeck(id);
					List<CartaBean> carte = new ArrayList<>();
					for(ContieneDeckBean c : contiene) {
						CartaBean carta = daoCarta.retrieveByKey(c.getIdCarta());
						carte.add(carta);
					}
					request.setAttribute("contiene", contiene);
					request.setAttribute("carte", carte);
					request.setAttribute("deck", prodotto);
					dispatcher.forward(request, response);
					break;
				}
				case 0 : {
					ProdottoBean prodotto = daoProd.retrieveByKey(id);
					RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/mostraProdotto.jsp");
					request.setAttribute("prodotto", prodotto);
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
