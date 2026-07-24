package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.BoxBean;
import model.CSetBean;
import model.CartaSingolaBean;
import model.ConfezionatoBean;
import model.ContieneDeckBean;
import model.DeckBean;
import model.PacchettoBean;
import model.ProdottoBean;
import model.ProdottoYGOBean;
import model.StructureDeckBean;
import model.TinBean;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import dao.BoxDao;
import dao.BoxDaoImpl;
import dao.CSetDao;
import dao.CSetDaoImpl;
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
import dao.PacchettoDao;
import dao.PacchettoDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import dao.ProdottoYGODao;
import dao.ProdottoYGODaoImpl;
import dao.StructureDeckDao;
import dao.StructureDeckDaoImpl;
import dao.TinDao;
import dao.TinDaoImpl;

/**
 * Servlet implementation class nuovoProdotto
 */
@WebServlet("/admin/nuovoProdotto")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 2* 1024 * 1024)
public class nuovoProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> quality = null;
	private ProdottoDao prodottoDAO = null;
	private ProdottoYGODao prodottoYGODAO = null;
	private ConfezionatoDao confezionatoDAO = null;
	private CartaSingolaDao cartasingolaDAO = null;
	private DeckDao deckDAO = null;
	private TinDao tinDAO = null;
	private StructureDeckDao structureDAO = null;
	private PacchettoDao pacchettoDAO = null;
	private BoxDao boxDAO = null;
	private CartaDao cartaDAO = null;
	private ContieneDeckDao contieneDAO = null;
	private CSetDao setDAO = null;
	private List<CSetBean> set = null; 
	private static final String IMAGE_DIR = "images";
	private static final String UPLOAD_DIR = "uploads";
	private static final String PRODUCT_DIR = "prodotti";
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        prodottoDAO = new ProdottoDaoImpl(ds);
        prodottoYGODAO = new ProdottoYGODaoImpl(ds);
        cartasingolaDAO = new CartaSingolaDaoImpl(ds);
        confezionatoDAO = new ConfezionatoDaoImpl(ds);
        deckDAO = new DeckDaoImpl(ds);
        structureDAO = new StructureDeckDaoImpl(ds);
        tinDAO = new TinDaoImpl(ds);
        boxDAO = new BoxDaoImpl(ds);
        pacchettoDAO = new PacchettoDaoImpl(ds);
        cartaDAO = new CartaDaoImpl(ds);
        contieneDAO = new ContieneDeckDaoImpl(ds);
        setDAO = new CSetDaoImpl(ds);
        quality = new ArrayList<>();
        quality.add("poor");
        quality.add("played");
        quality.add("light played");
        quality.add("good");
        quality.add("excellent");
        quality.add("near mint");
        quality.add("mint");
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public nuovoProdotto() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<CSetBean> set = setDAO.retrieveAll();
		}catch (SQLException e){
			request.setAttribute("msg","Errore nell'ottenimento dei set dal database!");
			response.sendError(500);
		}
		HttpSession session = request.getSession();
		session.setAttribute("isProdotto", "true");
		String action_prodotto= request.getParameter("action");
		String errore = "";
		String option = request.getParameter("tipo");
		boolean img = false;
		if(action_prodotto != null) {
			if(action_prodotto.equals("delete")) {
				if(request.getParameter("old_id") != null) {
					System.out.println(request.getParameter("old_id").trim());
					int oldId = Integer.parseInt(request.getParameter("old_id").trim());
					try {
						prodottoDAO.deleteProdotto(oldId);
					} catch(SQLException e) {
						response.sendError(500,"Errore nell'eliminazione del prodotto dal Database!");
					}
				} else errore = "Errore";
			} else if (action_prodotto.equals("add")) {
				session.setAttribute("action", "upload");
				switch(option) {
				case "prodotto": {
					System.out.println("Classe scelta...");
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					boolean valido = true;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
							img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
						
					}
					ProdottoBean temp = new ProdottoBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) >= 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					System.out.println(valido);
					temp.setPathImg(uploadPath);
					temp.setMimeType(mimeType);
					if(valido) {
						try{
							prodottoDAO.saveProdotto(temp);
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nell'aggiunta del prodotto nel database!");
							response.sendError(500);
						}break;
					} else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("/index");
						dispatcher.forward(request, response);
						return;
					} 
				}
				case "prodottoYGO":{
					boolean valido = true;
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
								img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
						
					}
					
					ProdottoYGOBean temp = new ProdottoYGOBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("lingua") != null) {
						String lingua = request.getParameter("lingua").trim().toLowerCase();
						if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
							temp.setLingua(lingua);
						} else valido = false;
					} else valido = false;
					if(uploadPath != null && mimeType != null) {
						temp.setPathImg(uploadPath);
						temp.setMimeType(mimeType);
					} else valido = false;
					if(valido) {
						try{
							prodottoYGODAO.saveProdotto(temp);
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nel salvataggio del prodotto nel database!");
							response.sendError(500);
						}break;
					} else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("");
						dispatcher.forward(request, response);
						return;
					} 
				}
				case "confezionato":{
					boolean valido = true;
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
							img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
						
					}
					
					ConfezionatoBean temp = new ConfezionatoBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("lingua") != null) {
						String lingua = request.getParameter("lingua").trim().toLowerCase();
						if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
							temp.setLingua(lingua);
						} else valido = false;
					} else valido = false;
					if(request.getParameter("idSet") != null) {
						if(!request.getParameter("idSet").equals("")) {
							temp.setIdSet(request.getParameter("idSet").trim());
						} else valido = false;
					} else valido = false;
					if(uploadPath != null && mimeType != null) {
						temp.setPathImg(uploadPath);
						temp.setMimeType(mimeType);
					} else valido = false;
					if(valido) {
						try{
							confezionatoDAO.saveProdotto(temp);
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nel salvataggio del prodotto nel database!");
							response.sendError(500);
						}break;
					} else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("");
						dispatcher.forward(request, response);
						return;
					}
				}
				case "cartaSingola":{
					boolean valido = true;
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
							img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
						
					}
					
					CartaSingolaBean temp = new CartaSingolaBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("lingua") != null) {
						String lingua = request.getParameter("lingua").trim().toLowerCase();
						if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
							temp.setLingua(lingua);
						} else valido = false;
					} else valido = false;
					if(request.getParameter("idSet") != null) {
						if(!request.getParameter("idSet").equals("")) {
							temp.setIdSet(request.getParameter("idSet").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qlt") != null) {
						String qlt = request.getParameter("qlt").trim().toLowerCase();
						if(quality.contains((String)(qlt))) {
							temp.setQuality(qlt);
						} else valido = false;
					} else valido = false;
					if(uploadPath != null && mimeType != null) {
						temp.setPathImg(uploadPath);
						temp.setMimeType(mimeType);
					} else valido = false;
					if(valido) {
						try {
							temp.setIdCarta(cartaDAO.retrieveByNome(request.getParameter("nome")).getId());
							cartasingolaDAO.saveCartaSingola(temp);
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nel salvataggio del prodotto nel database!");
							response.sendError(500);
						}
					} else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("");
						dispatcher.forward(request, response);
						return;
					} break;
				}
				case "box":{
					boolean valido = true;
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
							img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
						
					}
					
					BoxBean temp = new BoxBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("lingua") != null) {
						String lingua = request.getParameter("lingua").trim().toLowerCase();
						if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
							temp.setLingua(lingua);
						} else valido = false;
					} else valido = false;
					if(request.getParameter("idSet") != null) {
						if(!request.getParameter("idSet").equals("")) {
							temp.setIdSet(request.getParameter("idSet").trim());
						} else valido = false;
					} else valido = false;
					if(uploadPath != null && mimeType != null) {
						temp.setPathImg(uploadPath);
						temp.setMimeType(mimeType);
					} else valido = false;
					if(valido) {
						try{
							boxDAO.saveProdotto(temp);
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nel salvataggio del prodotto nel database!");
							response.sendError(500);
						}break;
					} else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("");
						dispatcher.forward(request, response);
						return;
					}
				} 
				case "pacchetto":{
					boolean valido = true;
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
							img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
						
					}
					
					PacchettoBean temp = new PacchettoBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("lingua") != null) {
						String lingua = request.getParameter("lingua").trim().toLowerCase();
						if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
							temp.setLingua(lingua);
						} else valido = false;
					} else valido = false;
					if(request.getParameter("idSet") != null) {
						if(!request.getParameter("idSet").equals("")) {
							temp.setIdSet(request.getParameter("idSet").trim());
						} else valido = false;
					} else valido = false;
					if(uploadPath != null && mimeType != null) {
						temp.setPathImg(uploadPath);
						temp.setMimeType(mimeType);
					} else valido = false;
					if(valido) {
						try{
							pacchettoDAO.saveProdotto(temp);
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nel salvataggio del prodotto nel database!");
							response.sendError(500);
						}
					} else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("");
						dispatcher.forward(request, response);
						return;
					} break;
				}
				case "structure":{
					boolean valido = true;
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
							img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
						
					}
					
					StructureDeckBean temp = new StructureDeckBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("lingua") != null) {
						String lingua = request.getParameter("lingua").trim().toLowerCase();
						if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
							temp.setLingua(lingua);
						} else valido = false;
					} else valido = false;
					if(request.getParameter("idSet") != null) {
						if(!request.getParameter("idSet").equals("")) {
							temp.setIdSet(request.getParameter("idSet").trim());
						} else valido = false;
					} else valido = false;
					if(uploadPath != null && mimeType != null) {
						temp.setPathImg(uploadPath);
						temp.setMimeType(mimeType);
					} else valido = false;
					if(valido) {
						try{
							structureDAO.saveProdotto(temp);
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nel salvataggio del prodotto nel database!");
							response.sendError(500);
						}
					} else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("");
						dispatcher.forward(request, response);
						return;
					} break;
				}
				case "tin":{
					boolean valido = true;
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
							img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
					}
					
					TinBean temp = new TinBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("lingua") != null) {
						String lingua = request.getParameter("lingua").trim().toLowerCase();
						if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
							temp.setLingua(lingua);
						} else valido = false;
					} else valido = false;
					if(request.getParameter("idSet") != null) {
						if(!request.getParameter("idSet").equals("")) {
							temp.setIdSet(request.getParameter("idSet").trim());
						} else valido = false;
					} else valido = false;
					if(uploadPath != null && mimeType != null) {
						temp.setPathImg(uploadPath);
						temp.setMimeType(mimeType);
					} else valido = false;
					if(valido) {
						try{
							tinDAO.saveProdotto(temp);
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nel salvataggio del prodotto nel database!");
							response.sendError(500);
						}
					} else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("");
						dispatcher.forward(request, response);
						return;
					} break;
				}
				case "deck":{
					boolean valido = true;
					Part part = request.getPart("image");
					String mimeType = null;
					String uploadPath = null;
					if(part != null) {
						String originalFileName = part.getSubmittedFileName();
						if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
							mimeType = part.getContentType();
							String uniqueFileName = buildUniqueFileName(part);
							uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
							img = true;
						}
						session.setAttribute("pathImg", uploadPath);
						session.setAttribute("part",part);
						
					}
					DeckBean temp = new DeckBean();
					if(request.getParameter("descr") != null) {
						if(!request.getParameter("descr").equals("")) {
							temp.setDescrizione(request.getParameter("descr").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("qnt") != null) {
						if(Integer.parseInt(request.getParameter("qnt")) > 0) {
							temp.setQnt(Integer.parseInt(request.getParameter("qnt").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("nome") != null) {
						if(!request.getParameter("nome").equals("")) {
							temp.setNome(request.getParameter("nome").trim());
						} else valido = false;
					} else valido = false;
					if(request.getParameter("prezzo") != null) {
						if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
							temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo").trim())*100));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("sconto") != null) {
						if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
							temp.setSconto(Integer.parseInt(request.getParameter("sconto").trim()));
						} else valido = false;
					} else valido = false;
					if(request.getParameter("lingua") != null) {
						String lingua = request.getParameter("lingua").trim().toLowerCase();
						if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
							temp.setLingua(lingua);
						} else valido = false;
					} else valido = false;
					if(uploadPath != null && mimeType != null) {
						temp.setPathImg(uploadPath);
						temp.setMimeType(mimeType);
					} else valido = false;
					if(valido) {
						try{
							deckDAO.saveDeck(temp);
							int idDeck = deckDAO.retrieveByNome(request.getParameter("nome").trim()).getId();
							int i = 0;
							String[] qnt = request.getParameterValues("qnt");
							for(String c : request.getParameterValues("nomeCarta")) {
								int idCarta = cartaDAO.retrieveByNome(c).getId();
								ContieneDeckBean cont = new ContieneDeckBean(idDeck,idCarta, Integer.parseInt(qnt[i]));
								contieneDAO.saveContieneDeck(cont);
								i++;
							}
						}catch(SQLException e) {
							request.setAttribute("msg","Errore nel salvataggio delle carte contenute nel deck!");
							response.sendError(500);
						}break;
					}	else {
						RequestDispatcher dispatcher =  request.getRequestDispatcher("/index");
						dispatcher.forward(request, response);	
						return;
					}
				}
			}
			} else if (action_prodotto.equals("alter")) {
				session.setAttribute("action", "change");
				if(request.getParameter("old_id") != null) {
					int oldId = Integer.parseInt(request.getParameter("old_id").trim());
					try {
						int prec = prodottoDAO.prodottoType(oldId);
						System.out.println(prec);
						if(prec == 0) {
							ProdottoBean prod = prodottoDAO.retrieveByKey(oldId);
							ProdottoBean bean = new ProdottoBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									prodottoDAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									prodottoDAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								bean.setQnt(Integer.parseInt(request.getParameter("prodotto_qnt").trim()));
								if(prod.getQnt() != bean.getQnt()) {
									prodottoDAO.changeQnt(bean);
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								bean.setPrezzo(Integer.parseInt(request.getParameter("prodotto_prezzo").trim())*100);
								if(prod.getPrezzo() != bean.getPrezzo()) {
									prodottoDAO.changePrezzo(bean);
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									prodottoDAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								bean.setSconto(Integer.parseInt(request.getParameter("prodotto_sconto").trim()));
								if(prod.getSconto() != bean.getSconto()) {
									prodottoDAO.changeSconto(bean);
								}
							}
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									prodottoDAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							} //immagine
						} else if(prec == 1) {
							CartaSingolaBean prod = cartasingolaDAO.retrieveByKey(oldId);
							CartaSingolaBean bean = new CartaSingolaBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									cartasingolaDAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									cartasingolaDAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								int qnt = Integer.parseInt(request.getParameter("prodotto_qnt").trim());
								if(qnt >= 0) {
									bean.setQnt(qnt);
									if(prod.getQnt() != bean.getQnt()) {
									cartasingolaDAO.changeQnt(bean);
									}
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								int prezzo = Integer.parseInt(request.getParameter("prodotto_prezzo").trim());
								if(prezzo >= 0) {
									bean.setPrezzo(prezzo*100);
									if(prod.getPrezzo() != bean.getPrezzo()) {
										cartasingolaDAO.changePrezzo(bean);
									}
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									cartasingolaDAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								int sconto = Integer.parseInt(request.getParameter("prodotto_sconto").trim());
								if(sconto >= 0 && sconto < 100) {
									bean.setSconto(sconto);
									if(prod.getSconto() != bean.getSconto()) {
										cartasingolaDAO.changeSconto(bean);
									}
								}
							}
							if(request.getParameter("prodotto_lingua") != null) {
								String lingua = request.getParameter("lingua").trim().toLowerCase();
								if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
									bean.setLingua(lingua);
									if(!prod.getLingua().equals(bean.getLingua())) {
										cartasingolaDAO.changeLingua(bean);
									}
								}
								
							}
							if(request.getParameter("idSet") != null) {
								String set = request.getParameter("idSet").trim();
								if(set.equals("nuovo")) {
									String nuovoSet = request.getParameter("nuovo_idSet").trim();
									String release = request.getParameter("nuovo_data").trim();
									
									CSetBean nuovo = new CSetBean();
									nuovo.setNome(nuovoSet);
									nuovo.setReleaseDate(Date.valueOf(release));
									setDAO.saveCSet(nuovo);
									bean.setIdSet(nuovoSet);
									cartasingolaDAO.changeIdSet(bean);
								} else {
									bean.setIdSet(set);
									CSetBean temp = setDAO.retrieveByKey(set);
									cartasingolaDAO.changeIdSet(bean);
								}
							}
							if(request.getParameter("qlt") != null) {
								String qlt = request.getParameter("prodotto_quality").trim().toLowerCase();
								bean.setQuality(qlt);
								if(quality.contains((String)(qlt))) {
									if(!prod.getQuality().equals(bean.getQuality())) {
										cartasingolaDAO.changeQuality(bean);
									}
								}
							}
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									cartasingolaDAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							}
						} else if(prec == 2) {
							ProdottoYGOBean prod = prodottoYGODAO.retrieveByKey(oldId);
							ProdottoYGOBean bean = new ProdottoYGOBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									prodottoYGODAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									prodottoYGODAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								int qnt = Integer.parseInt(request.getParameter("prodotto_qnt").trim());
								if(qnt >= 0) {
									bean.setQnt(qnt);
									if(prod.getQnt() != bean.getQnt()) {
									prodottoYGODAO.changeQnt(bean);
									}
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								int prezzo = Integer.parseInt(request.getParameter("prodotto_prezzo").trim());
								if(prezzo >= 0) {
									bean.setPrezzo(prezzo*100);
									if(prod.getPrezzo() != bean.getPrezzo()) {
										prodottoYGODAO.changePrezzo(bean);
									}
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									prodottoYGODAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								int sconto = Integer.parseInt(request.getParameter("prodotto_sconto").trim());
								if(sconto >= 0 && sconto < 100) {
									bean.setSconto(sconto);
									if(prod.getSconto() != bean.getSconto()) {
										prodottoYGODAO.changeSconto(bean);
									}
								}
							}
							if(request.getParameter("prodotto_lingua") != null) {
								String lingua = request.getParameter("lingua").trim().toLowerCase();
								if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
									bean.setLingua(lingua);
									if(!prod.getLingua().equals(bean.getLingua())) {
										prodottoYGODAO.changeLingua(bean);
									}
								}
							}
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									prodottoYGODAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							}
						} else if (prec == 3) {
							ConfezionatoBean prod = confezionatoDAO.retrieveByKey(oldId);
							ConfezionatoBean bean = new ConfezionatoBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									confezionatoDAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									confezionatoDAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								int qnt = Integer.parseInt(request.getParameter("prodotto_qnt").trim());
								if(qnt >= 0) {
									bean.setQnt(qnt);
									if(prod.getQnt() != bean.getQnt()) {
										confezionatoDAO.changeQnt(bean);
									}
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								int prezzo = Integer.parseInt(request.getParameter("prodotto_prezzo").trim());
								if(prezzo >= 0) {
									bean.setPrezzo(prezzo*100);
									if(prod.getPrezzo() != bean.getPrezzo()) {
										confezionatoDAO.changePrezzo(bean);
									}
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									confezionatoDAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								int sconto = Integer.parseInt(request.getParameter("prodotto_sconto").trim());
								if(sconto >= 0 && sconto < 100) {
									bean.setSconto(sconto);
									if(prod.getSconto() != bean.getSconto()) {
										confezionatoDAO.changeSconto(bean);
									}
								}
							}
							if(request.getParameter("prodotto_lingua") != null) {
								String lingua = request.getParameter("prodotto_lingua").trim().toLowerCase();
								if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
									bean.setLingua(lingua);
									if(!prod.getLingua().equals(bean.getLingua())) {
										confezionatoDAO.changeLingua(bean);
									}
								}
							}
							if(request.getParameter("idSet") != null) {
								String set = request.getParameter("idSet").trim();
								if(set.equals("nuovo")) {
									String nuovoSet = request.getParameter("nuovo_idSet").trim();
									String release = request.getParameter("nuovo_data").trim();
									
									CSetBean nuovo = new CSetBean();
									nuovo.setNome(nuovoSet);
									nuovo.setReleaseDate(Date.valueOf(release));
									setDAO.saveCSet(nuovo);
									bean.setIdSet(nuovoSet);
									confezionatoDAO.changeIdSet(bean);
								} else {
									bean.setIdSet(set);
									CSetBean temp = setDAO.retrieveByKey(set);
									confezionatoDAO.changeIdSet(bean);
								}
							} 
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									confezionatoDAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							}
						} else if(prec == 4) {
							PacchettoBean prod = pacchettoDAO.retrieveByKey(oldId);
							PacchettoBean bean = new PacchettoBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									pacchettoDAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									pacchettoDAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								int qnt = Integer.parseInt(request.getParameter("prodotto_qnt").trim());
								if(qnt >= 0) {
									bean.setQnt(qnt);
									if(prod.getQnt() != bean.getQnt()) {
										pacchettoDAO.changeQnt(bean);
									}
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								int prezzo = Integer.parseInt(request.getParameter("prodotto_prezzo").trim());
								if(prezzo >= 0) {
									bean.setPrezzo(prezzo*100);
									if(prod.getPrezzo() != bean.getPrezzo()) {
										pacchettoDAO.changePrezzo(bean);
									}
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									pacchettoDAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								int sconto = Integer.parseInt(request.getParameter("prodotto_sconto").trim());
								if(sconto >= 0 && sconto < 100) {
									bean.setSconto(sconto);
									if(prod.getSconto() != bean.getSconto()) {
										pacchettoDAO.changeSconto(bean);
									}
								}
							}
							if(request.getParameter("prodotto_lingua") != null) {
								String lingua = request.getParameter("prodotto_lingua").trim().toLowerCase();
								if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
									bean.setLingua(lingua);
									if(!prod.getLingua().equals(bean.getLingua())) {
										pacchettoDAO.changeLingua(bean);
									}
								}
							}
							if(request.getParameter("idSet") != null) {
								String set = request.getParameter("idSet").trim();
								if(set.equals("nuovo")) {
									String nuovoSet = request.getParameter("nuovo_idSet").trim();
									String release = request.getParameter("nuovo_data").trim();
									
									CSetBean nuovo = new CSetBean();
									nuovo.setNome(nuovoSet);
									nuovo.setReleaseDate(Date.valueOf(release));
									setDAO.saveCSet(nuovo);
									bean.setIdSet(nuovoSet);
									pacchettoDAO.changeIdSet(bean);
								} else {
									bean.setIdSet(set);
									CSetBean temp = setDAO.retrieveByKey(set);
									pacchettoDAO.changeIdSet(bean);
								}
							}
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									pacchettoDAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							}
						} else if (prec == 5) {
							TinBean prod = tinDAO.retrieveByKey(oldId);
							TinBean bean = new TinBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									tinDAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									tinDAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								int qnt = Integer.parseInt(request.getParameter("prodotto_qnt").trim());
								if(qnt >= 0) {
									bean.setQnt(qnt);
									if(prod.getQnt() != bean.getQnt()) {
										tinDAO.changeQnt(bean);
									}
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								int prezzo = Integer.parseInt(request.getParameter("prodotto_prezzo").trim());
								if(prezzo >= 0) {
									bean.setPrezzo(prezzo*100);
									if(prod.getPrezzo() != bean.getPrezzo()) {
										tinDAO.changePrezzo(bean);
									}
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									tinDAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								int sconto = Integer.parseInt(request.getParameter("prodotto_sconto").trim());
								if(sconto >= 0 && sconto < 100) {
									bean.setSconto(sconto);
									if(prod.getSconto() != bean.getSconto()) {
										tinDAO.changeSconto(bean);
									}
								}
							}
							if(request.getParameter("prodotto_lingua") != null) {
								String lingua = request.getParameter("prodotto_lingua").trim().toLowerCase();
								if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
									bean.setLingua(lingua);
									if(!prod.getLingua().equals(bean.getLingua())) {
										tinDAO.changeLingua(bean);
									}
								}
							}
							if(request.getParameter("idSet") != null) {
								String set = request.getParameter("idSet").trim();
								if(set.equals("nuovo")) {
									String nuovoSet = request.getParameter("nuovo_idSet").trim();
									String release = request.getParameter("nuovo_data").trim();
									
									CSetBean nuovo = new CSetBean();
									nuovo.setNome(nuovoSet);
									nuovo.setReleaseDate(Date.valueOf(release));
									setDAO.saveCSet(nuovo);
									bean.setIdSet(nuovoSet);
									tinDAO.changeIdSet(bean);
								} else {
									bean.setIdSet(set);
									CSetBean temp = setDAO.retrieveByKey(set);
									tinDAO.changeIdSet(bean);
								}
							}
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									tinDAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							}
						} else if(prec == 6) {
							BoxBean prod = boxDAO.retrieveByKey(oldId);
							BoxBean bean = new BoxBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									boxDAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									boxDAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								int qnt = Integer.parseInt(request.getParameter("prodotto_qnt").trim());
								if(qnt >= 0) {
									bean.setQnt(qnt);
									if(prod.getQnt() != bean.getQnt()) {
										boxDAO.changeQnt(bean);
									}
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								int prezzo = Integer.parseInt(request.getParameter("prodotto_prezzo").trim());
								if(prezzo >= 0) {
									bean.setPrezzo(prezzo*100);
									if(prod.getPrezzo() != bean.getPrezzo()) {
										boxDAO.changePrezzo(bean);
									}
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									boxDAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								int sconto = Integer.parseInt(request.getParameter("prodotto_sconto").trim());
								if(sconto >= 0 && sconto < 100) {
									bean.setSconto(sconto);
									if(prod.getSconto() != bean.getSconto()) {
										boxDAO.changeSconto(bean);
									}
								}
							}
							if(request.getParameter("prodotto_lingua") != null) {
								String lingua = request.getParameter("prodotto_lingua").trim().toLowerCase();
								if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
									bean.setLingua(lingua);
									if(!prod.getLingua().equals(bean.getLingua())) {
										boxDAO.changeLingua(bean);
									}
								}
							}
							if(request.getParameter("idSet") != null) {
								String set = request.getParameter("idSet").trim();
								if(set.equals("nuovo")) {
									String nuovoSet = request.getParameter("nuovo_idSet").trim();
									String release = request.getParameter("nuovo_data").trim();
									
									CSetBean nuovo = new CSetBean();
									nuovo.setNome(nuovoSet);
									nuovo.setReleaseDate(Date.valueOf(release));
									setDAO.saveCSet(nuovo);
									bean.setIdSet(nuovoSet);
									boxDAO.changeIdSet(bean);
								} else {
									bean.setIdSet(set);
									setDAO.retrieveByKey(set);
									boxDAO.changeIdSet(bean);
								}
							}
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									boxDAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							}
						} else if(prec == 7) {
							StructureDeckBean prod = structureDAO.retrieveByKey(oldId);
							StructureDeckBean bean = new StructureDeckBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									structureDAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									structureDAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								int qnt = Integer.parseInt(request.getParameter("prodotto_qnt").trim());
								if(qnt >= 0) {
									bean.setQnt(qnt);
									if(prod.getQnt() != bean.getQnt()) {
										structureDAO.changeQnt(bean);
									}
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								int prezzo = Integer.parseInt(request.getParameter("prodotto_prezzo").trim());
								if(prezzo >= 0) {
									bean.setPrezzo(prezzo*100);
									if(prod.getPrezzo() != bean.getPrezzo()) {
										structureDAO.changePrezzo(bean);
									}
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									structureDAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								int sconto = Integer.parseInt(request.getParameter("prodotto_sconto").trim());
								if(sconto >= 0 && sconto < 100) {
									bean.setSconto(sconto);
									if(prod.getSconto() != bean.getSconto()) {
										structureDAO.changeSconto(bean);
									}
								}
							}
							if(request.getParameter("prodotto_lingua") != null) {
								String lingua = request.getParameter("prodotto_lingua").trim().toLowerCase();
								if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
									bean.setLingua(lingua);
									if(!prod.getLingua().equals(bean.getLingua())) {
										structureDAO.changeLingua(bean);
									}
								}
							}
							if(request.getParameter("idSet") != null) {
								String set = request.getParameter("idSet").trim();
								if(set.equals("nuovo")) {
									String nuovoSet = request.getParameter("nuovo_set").trim();
									String release = request.getParameter("data_set").trim();
									
									CSetBean nuovo = new CSetBean();
									nuovo.setNome(nuovoSet);
									nuovo.setReleaseDate(Date.valueOf(release));
									setDAO.saveCSet(nuovo);
									bean.setIdSet(nuovoSet);
									structureDAO.changeIdSet(bean);
								} else {
									bean.setIdSet(set);
									CSetBean temp = setDAO.retrieveByKey(set);
									structureDAO.changeIdSet(bean);
								}
							}
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									structureDAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							}
						} else if (prec == 8) {
							DeckBean prod = deckDAO.retrieveByKey(oldId);
							DeckBean bean = new DeckBean();
							bean.setId(oldId);
							if(request.getParameter("prodotto_id") != null) {
								bean.setId(Integer.parseInt(request.getParameter("prodotto_id").trim()));
								if(prod.getId() != bean.getId()) {
									deckDAO.changeId(bean, oldId);
								}
							}
							if(request.getParameter("prodotto_nome") != null) {
								bean.setNome(request.getParameter("prodotto_nome").trim());
								if(!prod.getNome().equals(bean.getNome())) {
									deckDAO.changeNome(bean);
								}
							}
							if(request.getParameter("prodotto_qnt") != null) {
								int qnt = Integer.parseInt(request.getParameter("prodotto_qnt").trim());
								if(qnt >= 0) {
									bean.setQnt(qnt);
									if(prod.getQnt() != bean.getQnt()) {
										deckDAO.changeQnt(bean);
									}
								}
							}
							if(request.getParameter("prodotto_prezzo") != null) {
								int prezzo = Integer.parseInt(request.getParameter("prodotto_prezzo").trim());
								if(prezzo >= 0) {
									bean.setPrezzo(prezzo*100);
									if(prod.getPrezzo() != bean.getPrezzo()) {
										deckDAO.changePrezzo(bean);
									}
								}
							}
							if(request.getParameter("prodotto_descrizione") != null) {
								bean.setDescrizione(request.getParameter("prodotto_descrizione").trim());
								if(!prod.getDescrizione().equals(bean.getDescrizione())) {
									deckDAO.changeDescrizione(bean);
								}
							}
							if(request.getParameter("prodotto_sconto") != null) {
								int sconto = Integer.parseInt(request.getParameter("prodotto_sconto").trim());
								if(sconto >= 0 && sconto < 100) {
									bean.setSconto(sconto);
									if(prod.getSconto() != bean.getSconto()) {
										deckDAO.changeSconto(bean);
									}
								}
							}
							if(request.getParameter("prodotto_lingua") != null) {
								String lingua = request.getParameter("prodotto_lingua").trim().toLowerCase();
								if(lingua.equals("ita") || lingua.equals("eng") || lingua.equals("jap")) {
									bean.setLingua(lingua);
									if(!prod.getLingua().equals(bean.getLingua())) {
										deckDAO.changeLingua(bean);
									}
								}
							}
							if(request.getParameter("carte") != null) {
								String[] carte = request.getParameterValues("carte");
								String[] qnts = request.getParameterValues("qnt");
								contieneDAO.deleteContieneDeckByIdDeck(oldId);
								for(int i = 0 ; i < carte.length; i++) {
									if(carte[i] == null) continue;
									else {
										ContieneDeckBean contiene = new ContieneDeckBean();
										contiene.setIdDeck(oldId);
										contiene.setIdCarta(cartaDAO.retrieveByNome(carte[i].trim()).getId());
										contiene.setQnt(Integer.parseInt(qnts[i].trim()));
										contieneDAO.saveContieneDeck(contiene);
									}
								}
							}
							Part part = request.getPart("image");
							String mimeType = null;
							String uploadPath = null;
							if(part != null) {
								String originalFileName = part.getSubmittedFileName();
								if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
									mimeType = part.getContentType();
									String uniqueFileName = buildUniqueFileName(part);
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",prod.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									deckDAO.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadImmagine");
									dispatcher.forward(request, response);
								}
							}
						}
					} catch(SQLException e) {
						request.setAttribute("msg","Errore nella modifica del prodotto nel database!");
						response.sendError(500);
					}
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
		dispatcher.forward(request, response);
	}

	private String buildUniqueFileName(Part part) {
		String originalName = part.getSubmittedFileName();
		String extension;
		if(originalName.contains(".")) {
			extension = originalName.substring(originalName.lastIndexOf("."));
		} else {
			extension = "";
		} return UUID.randomUUID() + extension;
	}
	
}
