package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrdineBean;
import model.ProdottoCompratoBean;
import model.UtenteBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.OrdineDao;
import dao.OrdineDaoImpl;
import dao.ProdottoCompratoDao;
import dao.ProdottoCompratoDaoImpl;

/**
 * Servlet implementation class ServletGetOrdine
 */
@WebServlet("/servletGetOrdine")
public class ServletGetOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private OrdineDao ordineDao = null;
    private ProdottoCompratoDao prodottoCDao = null;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        ordineDao = new OrdineDaoImpl(ds);
        prodottoCDao = new ProdottoCompratoDaoImpl(ds);
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGetOrdine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		HttpSession session = request.getSession();
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
		List<OrdineBean> ordini = null;
		JSONArray array = new JSONArray();
		try {
			if(email != null) {
				ordini = ordineDao.retrieveByIdUtente(utente.getId());
			}else {
				ordini = ordineDao.retrieveByIdUtenteNull();
			}
			for(OrdineBean o : ordini) {
				JSONObject obj = new JSONObject();
				obj.put("stato", o.getStato());
				obj.put("idOrdine", o.getIdOrdine());
				obj.put("idUtente", o.getIdUtente());
				obj.put("idMetodo", o.getIdMetodo());
				obj.put("idInfoSped",o.getIdInfoSped());
				obj.put("dataAcquisto", o.getDataAcquisto());
				obj.put("dataConsegna", o.getDataConsegna());
				array.put(obj);
				List<ProdottoCompratoBean> list = prodottoCDao.retrieveByIdOrdine(o.getIdOrdine());
				JSONArray prodotti = new JSONArray();
				for(ProdottoCompratoBean prod : list) {
					JSONObject oggetto = new JSONObject();
					oggetto.put("id", prod.getId());
					oggetto.put("idOrdine", prod.getIdOrdine());
					oggetto.put("idOriginale", prod.getIdOriginale());
					oggetto.put("prezzo", prod.getPrezzo());
					oggetto.put("qnt", prod.getQnt());
					oggetto.put("info", prod.getInfo());
					oggetto.put("nome", prod.getNome());
					prodotti.put(oggetto);
				}
				array.put(prodotti);
			}
			out.print(array.toString());
		}catch(SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    		return;
		}
		
		out.print(array.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
