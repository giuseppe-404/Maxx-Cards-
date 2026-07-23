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
import java.sql.Date;
import java.sql.SQLException;
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
import dao.UtenteDao;
import dao.UtenteDaoImpl;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InfoSpedDao infoSpedDao = null;
	private MetodoPagamentoDao metodoDao = null;
	private OrdineDao ordineDao = null;
	private ProdottoCompratoDao prodottoCDao = null;
	private ProdottoDao prodottoDao = null;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        infoSpedDao = new InfoSpedDaoImpl(ds);
        metodoDao = new MetodoPagamentoDaoImpl(ds);
        ordineDao = new OrdineDaoImpl(ds);
        prodottoCDao = new ProdottoCompratoDaoImpl(ds);
        prodottoDao = new ProdottoDaoImpl(ds);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		OrdineBean carrello = (OrdineBean) session.getAttribute("carrello");
		List<ProdottoCompratoBean> prodCarrello = (List<ProdottoCompratoBean>) session.getAttribute("prodCarrello");
		try {
			InfoSpedBean indirizzo = null;
			MetodoPagamentoBean metodo = null;
			if(request.getParameter("infoSped_id") != null) {
				int id = Integer.parseInt(request.getParameter("info_id"));
				InfoSpedBean bean = infoSpedDao.retrieveByKey(id, utente.getId());
				InfoSpedBean filter = new InfoSpedBean();
				filter.setId(id);
				filter.setIdUtente(utente.getId());
				if(request.getParameter("info_nome") != null) {
					String nome = request.getParameter("info_nome");
					if(!nome.equals(bean.getNome())) {
						filter.setNome(nome);
						infoSpedDao.changeNome(filter);
					}
				}
				if(request.getParameter("info_cognome") != null) {
					String cognome = request.getParameter("info_cognome");
					if(!cognome.equals(bean.getCognome())) {
						filter.setCognome(cognome);
						infoSpedDao.changeCognome(filter);
					}
				}
				if(request.getParameter("info_via") != null) {
					String via = request.getParameter("info_via");
					if(!via.equals(bean.getVia())) {
						filter.setVia(via);
						infoSpedDao.changeVia(filter);
					}
				}
				if(request.getParameter("info_civico") != null) {
					int civico = Integer.parseInt(request.getParameter("info_civico"));
					if(civico != bean.getCivico()) {
						filter.setCivico(civico);
						infoSpedDao.changeCivico(filter);
					}
				}
				if(request.getParameter("info_cap") != null) {
					int cap = Integer.parseInt(request.getParameter("info_cap"));
					if(cap != bean.getCap()) {
						filter.setCap(cap);
						infoSpedDao.changeCAP(filter);
					}
				}
				indirizzo = infoSpedDao.retrieveByKey(id,utente.getId());
			} else {
				int id = infoSpedDao.retrieveByIdUtente(utente.getId()).size();
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
				infoSpedDao.saveInfoSped(nuovo);
				indirizzo = nuovo;
			}
			if(request.getParameter("metodo_scelto") != null) {
				if(!request.getParameter("metodo_scelto").equals("nuovo")) {
					int id = Integer.parseInt(request.getParameter("metodo_scelto"));
					MetodoPagamentoBean filter = new MetodoPagamentoBean();
					filter.setId(id);
					filter.setIdUtente(utente.getId());
					MetodoPagamentoBean bean = metodoDao.retrieveByKey(id, utente.getId());
					if(request.getParameter("metodo") != null) {
						String nome = request.getParameter("metodoPagamento_metodo");
						if(!nome.equals(bean.getMetodo())) {
							filter.setMetodo(nome);
							metodoDao.changeMetodoPagamento(filter);
							metodo = filter;
						}
					} else {
						metodo = bean;
					}	
				} else {
					int id = metodoDao.retrieveByIdUtente(utente.getId()).size() + 1;
					MetodoPagamentoBean filter = new MetodoPagamentoBean();
					filter.setId(id);
					filter.setIdUtente(utente.getId());
					if(request.getParameter("metodo") != null) {
						filter.setMetodo(request.getParameter("metodo"));
					} else {
						response.sendError(400,"Metodo mancante!");
					}
					metodoDao.saveMetodoPagamento(filter);
					metodo = filter;
				}
				
			} else {
				response.sendError(400,"Errore nella scelta del metodo di pagamento!");
			}
			for(ProdottoCompratoBean p : prodCarrello) {
				ProdottoBean prodotto = prodottoDao.retrieveByKey(p.getIdOriginale());
				p.setInfo(prodotto.getDescrizione());
				p.setNome(prodotto.getNome());
				p.setPrezzo(prodotto.getPrezzo());
				prodotto.setQnt(prodotto.getQnt() - p.getQnt());
				prodottoDao.changeQnt(prodotto);
				prodottoCDao.saveProdottoComprato(p);
			}
			carrello.setStato("Acquistato");
			carrello.setDataAcquisto(new Date(System.currentTimeMillis()));
			carrello.setIdMetodo(metodo.getId());
			carrello.setIdInfoSped(indirizzo.getId());
			ordineDao.changeDataAcquisto(carrello);
			ordineDao.changeStato(carrello);
			ordineDao.changeInfoSped(carrello);
			ordineDao.changeMetodoPagamento(carrello);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/");		
			dispatcher.forward(request, response);
			} catch(SQLException e) {
				response.sendError(500,"Errore nel caricamento delle informazioni nel database!");
		}
	}

}
