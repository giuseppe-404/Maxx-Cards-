package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.NotiziaBean;
import model.ProdottoBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.NotiziaDao;
import dao.NotiziaDaoImpl;
import dao.ProdottiHomeDao;
import dao.ProdottiHomeDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

/**
 * Servlet implementation class index
 */
@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottiHomeDao daoPH;
	private ProdottoDao daoPR;
    private NotiziaDao daoN;  
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
        	System.out.println("ciao");
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        daoPH = new ProdottiHomeDaoImpl(ds);
        daoPR = new ProdottoDaoImpl(ds);
        daoN = new NotiziaDaoImpl(ds);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ProdottoBean> prod = new ArrayList<>();
		try {
			List<Integer> id = daoPH.retrieveAll();
			for(Integer i : id) {
				ProdottoBean temp = daoPR.retrieveByKey(i);
				prod.add(temp);
			}
			List<NotiziaBean> notizie = daoN.retrieveAll(3,0);
			request.setAttribute("notizie", notizie);
			request.setAttribute("prodotti", prod);
		}catch(SQLException e) {
			request.setAttribute("msg", "Errore nell'ottenimento delle informazioni per l'homePage!");
			response.sendError(500);
			return;
		}
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/homepage.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
