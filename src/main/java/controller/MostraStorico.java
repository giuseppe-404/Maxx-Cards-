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
import model.OrdineBean;
import model.ProdottoBean;
import model.ProdottoCompratoBean;
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.InfoSpedDao;
import dao.InfoSpedDaoImpl;
import dao.MetodoPagamentoDao;
import dao.MetodoPagamentoDaoImpl;
import dao.OrdineDao;
import dao.OrdineDaoImpl;
import dao.ProdottoCompratoDao;
import dao.ProdottoCompratoDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

/**
 * Servlet implementation class MostraStorico
 */
@WebServlet("/common/mostraStorico")
public class MostraStorico extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDao prodottoDao = null;
    private ProdottoCompratoDao prodottoCDao = null;
    private OrdineDao ordineDao = null;
    private InfoSpedDao infoDao = null;
    private MetodoPagamentoDao metodoDao = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        prodottoDao = new ProdottoDaoImpl(ds);
        prodottoCDao = new ProdottoCompratoDaoImpl(ds);
        ordineDao = new OrdineDaoImpl(ds);
        infoDao = new InfoSpedDaoImpl(ds);
        metodoDao = new MetodoPagamentoDaoImpl(ds);
    }
    
    public MostraStorico() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		int id = utente.getId();
		try {
			List<OrdineBean> ordini = ordineDao.retrieveByIdUtente(id);
			request.setAttribute("ordini", ordini);
			List<ProdottoBean> prodotti = new ArrayList<>();
			List<InfoSpedBean> infos = new ArrayList<>();
			List<MetodoPagamentoBean> metodi = new ArrayList<>();
			for(OrdineBean o : ordini) {
				List<ProdottoCompratoBean> prodottiC = prodottoCDao.retrieveByIdOrdine(o.getIdOrdine());
				for(ProdottoCompratoBean c : prodottiC) {
					ProdottoBean bean = prodottoDao.retrieveByKey(c.getIdOriginale());
					prodotti.add(bean);
				}
				infos.add(infoDao.retrieveByKey(o.getIdInfoSped(), utente.getId()));
				metodi.add(metodoDao.retrieveByKey(o.getIdMetodo(),utente.getId()));
			}
			request.setAttribute("prodotti", prodotti);
			request.setAttribute("infos", infos);
			request.setAttribute("metodi",metodi);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/common/storico.jsp");
			dispatcher.forward(request,response);
		} catch(SQLException e) {
			request.setAttribute("msg","Errore nell'ottenimento degli ordini!");
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
