package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.BoxBean;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.sql.DataSource;

import dao.BoxDao;
import dao.BoxDaoImpl;
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
@WebServlet("/nuovoProdotto")
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
		request.getSession().setAttribute("isProdotto", "true");
		request.getSession().setAttribute("action", "upload");
		String option = request.getParameter("tipo");
		boolean img = false;
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
					
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
						e.printStackTrace();
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
					
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
					String lingua = request.getParameter("lingua").toLowerCase();
					if(lingua.equals("italiano") || lingua.equals("inglese") || lingua.equals("giapponese")) {
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
						e.printStackTrace();
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
					
				}
				
				ConfezionatoBean temp = new ConfezionatoBean();
				if(request.getParameter("descr") != null) {
					if(!request.getParameter("descr").equals("")) {
						temp.setDescrizione(request.getParameter("descr"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("qnt") != null) {
					if(Integer.parseInt(request.getParameter("qnt")) > 0) {
						temp.setQnt(Integer.parseInt(request.getParameter("qnt")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("nome") != null) {
					if(!request.getParameter("nome").equals("")) {
						temp.setNome(request.getParameter("nome"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("prezzo") != null) {
					if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
						temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("sconto") != null) {
					if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
						temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("lingua") != null) {
					String lingua = request.getParameter("lingua").toLowerCase();
					if(lingua.equals("italiano") || lingua.equals("inglese") || lingua.equals("giapponese")) {
						temp.setLingua(lingua);
					} else valido = false;
				} else valido = false;
				if(request.getParameter("idSet") != null) {
					if(!request.getParameter("idSet").equals("")) {
						temp.setIdSet(request.getParameter("idSet"));
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
						e.printStackTrace();
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
					
				}
				
				CartaSingolaBean temp = new CartaSingolaBean();
				if(request.getParameter("descr") != null) {
					if(!request.getParameter("descr").equals("")) {
						temp.setDescrizione(request.getParameter("descr"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("qnt") != null) {
					if(Integer.parseInt(request.getParameter("qnt")) > 0) {
						temp.setQnt(Integer.parseInt(request.getParameter("qnt")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("nome") != null) {
					if(!request.getParameter("nome").equals("")) {
						temp.setNome(request.getParameter("nome"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("prezzo") != null) {
					if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
						temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("sconto") != null) {
					if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
						temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("lingua") != null) {
					String lingua = request.getParameter("lingua").toLowerCase();
					if(lingua.equals("italiano") || lingua.equals("inglese") || lingua.equals("giapponese")) {
						temp.setLingua(lingua);
					} else valido = false;
				} else valido = false;
				if(request.getParameter("idSet") != null) {
					if(!request.getParameter("idSet").equals("")) {
						temp.setIdSet(request.getParameter("idSet"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("qlt") != null) {
					String qlt = request.getParameter("qlt").toLowerCase();
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
						e.printStackTrace();
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
					
				}
				
				BoxBean temp = new BoxBean();
				if(request.getParameter("descr") != null) {
					if(!request.getParameter("descr").equals("")) {
						temp.setDescrizione(request.getParameter("descr"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("qnt") != null) {
					if(Integer.parseInt(request.getParameter("qnt")) > 0) {
						temp.setQnt(Integer.parseInt(request.getParameter("qnt")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("nome") != null) {
					if(!request.getParameter("nome").equals("")) {
						temp.setNome(request.getParameter("nome"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("prezzo") != null) {
					if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
						temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("sconto") != null) {
					if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
						temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("lingua") != null) {
					String lingua = request.getParameter("lingua").toLowerCase();
					if(lingua.equals("italiano") || lingua.equals("inglese") || lingua.equals("giapponese")) {
						temp.setLingua(lingua);
					} else valido = false;
				} else valido = false;
				if(request.getParameter("idSet") != null) {
					if(!request.getParameter("idSet").equals("")) {
						temp.setIdSet(request.getParameter("idSet"));
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
						e.printStackTrace();
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
					
				}
				
				PacchettoBean temp = new PacchettoBean();
				if(request.getParameter("descr") != null) {
					if(!request.getParameter("descr").equals("")) {
						temp.setDescrizione(request.getParameter("descr"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("qnt") != null) {
					if(Integer.parseInt(request.getParameter("qnt")) > 0) {
						temp.setQnt(Integer.parseInt(request.getParameter("qnt")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("nome") != null) {
					if(!request.getParameter("nome").equals("")) {
						temp.setNome(request.getParameter("nome"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("prezzo") != null) {
					if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
						temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("sconto") != null) {
					if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
						temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("lingua") != null) {
					String lingua = request.getParameter("lingua").toLowerCase();
					if(lingua.equals("italiano") || lingua.equals("inglese") || lingua.equals("giapponese")) {
						temp.setLingua(lingua);
					} else valido = false;
				} else valido = false;
				if(request.getParameter("idSet") != null) {
					if(!request.getParameter("idSet").equals("")) {
						temp.setIdSet(request.getParameter("idSet"));
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
						e.printStackTrace();
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
					
				}
				
				StructureDeckBean temp = new StructureDeckBean();
				if(request.getParameter("descr") != null) {
					if(!request.getParameter("descr").equals("")) {
						temp.setDescrizione(request.getParameter("descr"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("qnt") != null) {
					if(Integer.parseInt(request.getParameter("qnt")) > 0) {
						temp.setQnt(Integer.parseInt(request.getParameter("qnt")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("nome") != null) {
					if(!request.getParameter("nome").equals("")) {
						temp.setNome(request.getParameter("nome"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("prezzo") != null) {
					if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
						temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("sconto") != null) {
					if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
						temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("lingua") != null) {
					String lingua = request.getParameter("lingua").toLowerCase();
					if(lingua.equals("italiano") || lingua.equals("inglese") || lingua.equals("giapponese")) {
						temp.setLingua(lingua);
					} else valido = false;
				} else valido = false;
				if(request.getParameter("idSet") != null) {
					if(!request.getParameter("idSet").equals("")) {
						temp.setIdSet(request.getParameter("idSet"));
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
						e.printStackTrace();
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
				}
				
				TinBean temp = new TinBean();
				if(request.getParameter("descr") != null) {
					if(!request.getParameter("descr").equals("")) {
						temp.setDescrizione(request.getParameter("descr"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("qnt") != null) {
					if(Integer.parseInt(request.getParameter("qnt")) > 0) {
						temp.setQnt(Integer.parseInt(request.getParameter("qnt")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("nome") != null) {
					if(!request.getParameter("nome").equals("")) {
						temp.setNome(request.getParameter("nome"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("prezzo") != null) {
					if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
						temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("sconto") != null) {
					if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
						temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("lingua") != null) {
					String lingua = request.getParameter("lingua").toLowerCase();
					if(lingua.equals("italiano") || lingua.equals("inglese") || lingua.equals("giapponese")) {
						temp.setLingua(lingua);
					} else valido = false;
				} else valido = false;
				if(request.getParameter("idSet") != null) {
					if(!request.getParameter("idSet").equals("")) {
						temp.setIdSet(request.getParameter("idSet"));
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
						e.printStackTrace();
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
					request.getSession().setAttribute("pathImg", uploadPath);
					request.getSession().setAttribute("part",part);
					
				}
				DeckBean temp = new DeckBean();
				if(request.getParameter("descr") != null) {
					if(!request.getParameter("descr").equals("")) {
						temp.setDescrizione(request.getParameter("descr"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("qnt") != null) {
					if(Integer.parseInt(request.getParameter("qnt")) > 0) {
						temp.setQnt(Integer.parseInt(request.getParameter("qnt")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("nome") != null) {
					if(!request.getParameter("nome").equals("")) {
						temp.setNome(request.getParameter("nome"));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("prezzo") != null) {
					if(Integer.parseInt(request.getParameter("prezzo")) > 0) {
						temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("sconto") != null) {
					if(Integer.parseInt(request.getParameter("sconto")) > 0 && Integer.parseInt(request.getParameter("sconto")) < 100 ) {
						temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
					} else valido = false;
				} else valido = false;
				if(request.getParameter("lingua") != null) {
					String lingua = request.getParameter("lingua").toLowerCase();
					if(lingua.equals("italiano") || lingua.equals("inglese") || lingua.equals("giapponese")) {
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
						int idDeck = deckDAO.retrieveByNome(request.getParameter("nome")).getId();
						int i = 0;
						String[] qnt = request.getParameterValues("qnt");
						for(String c : request.getParameterValues("nomeCarta")) {
							int idCarta = cartaDAO.retrieveByNome(c).getId();
							ContieneDeckBean cont = new ContieneDeckBean(idDeck,idCarta, Integer.parseInt(qnt[i]));
							contieneDAO.saveContieneDeck(cont);
							i++;
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}break;
				}	else {
					RequestDispatcher dispatcher =  request.getRequestDispatcher("/index");
					dispatcher.forward(request, response);	
					return;
				}
			}
		}
		if(img) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/UploadImmagine");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
			dispatcher.forward(request, response);
		}
		
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
