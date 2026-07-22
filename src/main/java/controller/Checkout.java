package controller;

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
	private UtenteDao utenteDao = null;
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
        utenteDao = new UtenteDaoImpl(ds);
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
				int id = Integer.parseInt(request.getParameter("infoSped_id"));
				InfoSpedBean bean = infoSpedDao.retrieveByKey(id, utente.getId());
				InfoSpedBean filter = new InfoSpedBean();
				filter.setId(id);
				filter.setIdUtente(utente.getId());
				if(request.getParameter("infoSped_nome") != null) {
					String nome = request.getParameter("infoSped_nome");
					if(!nome.equals(bean.getNome())) {
						filter.setNome(nome);
						infoSpedDao.changeNome(filter);
					}
				}
				if(request.getParameter("infoSped_cognome") != null) {
					String cognome = request.getParameter("infoSped_cognome");
					if(!cognome.equals(bean.getCognome())) {
						filter.setCognome(cognome);
						infoSpedDao.changeCognome(filter);
					}
				}
				if(request.getParameter("infoSped_via") != null) {
					String via = request.getParameter("infoSped_via");
					if(!via.equals(bean.getVia())) {
						filter.setVia(via);
						infoSpedDao.changeVia(filter);
					}
				}
				if(request.getParameter("infoSped_civico") != null) {
					int civico = Integer.parseInt(request.getParameter("infoSped_civico"));
					if(civico != bean.getCivico()) {
						filter.setCivico(civico);
						infoSpedDao.changeCivico(filter);
					}
				}
				if(request.getParameter("infoSped_cap") != null) {
					int cap = Integer.parseInt(request.getParameter("infoSped_cap"));
					if(cap != bean.getCap()) {
						filter.setCap(cap);
						infoSpedDao.changeCAP(filter);
					}
				}
				indirizzo = infoSpedDao.retrieveByKey(id,utente.getId());
			} else {
				int id = infoSpedDao.retrieveByIdUtente(utente.getId()).size();
				InfoSpedBean nuovo = new InfoSpedBean();
				if(request.getParameter("infoSped_nome") != null) {
					String nome = request.getParameter("infoSped_nome");
					nuovo.setNome(nome);
				}
				if(request.getParameter("infoSped_cognome") != null) {
					String cognome = request.getParameter("infoSped_cognome");
					nuovo.setCognome(cognome);
				}
				if(request.getParameter("infoSped_via") != null) {
					String via = request.getParameter("infoSped_via");
					nuovo.setVia(via);
				}
				if(request.getParameter("infoSped_civico") != null) {
					int civico = Integer.parseInt(request.getParameter("infoSped_civico"));
					nuovo.setCivico(civico);
				}
				if(request.getParameter("infoSped_cap") != null) {
					int cap = Integer.parseInt(request.getParameter("infoSped_cap"));
					nuovo.setCap(cap);
				}
				nuovo.setIdUtente(utente.getId());
				nuovo.setId(id+1);
				infoSpedDao.saveInfoSped(nuovo);
				indirizzo = nuovo;
			}
			if(request.getParameter("metodoPagamento_id") != null) {
				int id = Integer.parseInt(request.getParameter("metodoPagamento_id"));
				MetodoPagamentoBean bean = metodoDao.retrieveByKey(id, utente.getId());
				MetodoPagamentoBean filter = new MetodoPagamentoBean();
				filter.setId(id);
				filter.setIdUtente(utente.getId());
				if(request.getParameter("metodoPagamento_metodo") != null) {
					String nome = request.getParameter("metodoPagamento_metodo");
					if(!nome.equals(bean.getMetodo())) {
						filter.setMetodo(nome);
						metodoDao.changeMetodoPagamento(filter);
					}
				}metodo = bean;
			} else {
				int id = metodoDao.retrieveByIdUtente(utente.getId()).size();
				MetodoPagamentoBean bean = metodoDao.retrieveByKey(id, utente.getId());
				MetodoPagamentoBean filter = new MetodoPagamentoBean();
				filter.setId(id+1);
				filter.setIdUtente(utente.getId());
				if(request.getParameter("metodoPagamento_metodo") != null) {
					String met = request.getParameter("metodoPagamento_metodo");
					filter.setMetodo(met);
				}
				metodoDao.saveMetodoPagamento(filter);
				metodo = metodoDao.retrieveByKey(id+1,utente.getId());
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
		} catch(SQLException e) {
				e.printStackTrace();
		}
	}

}
