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
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public nuovoProdotto() {
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
		request.getSession().setAttribute("isProdotto", "true");
		request.getSession().setAttribute("action", "upload");
		String option = request.getParameter("tipo");
		switch(option) {
			case "prodotto": {
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				ProdottoBean temp = new ProdottoBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setQnt(Integer.parseInt(request.getParameter("qnt")));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
				try{
					prodottoDAO.saveProdotto(temp);
				}catch(SQLException e) {
					e.printStackTrace();
				}break;
			}
			case "prodottoYGO":{
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				ProdottoYGOBean temp = new ProdottoYGOBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setLingua(request.getParameter("lingua"));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
				try{
					prodottoYGODAO.saveProdottoYGO(temp);
				}catch(SQLException e) {
					e.printStackTrace();
				}break;
			}
			case "confezionato":{
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				ConfezionatoBean temp = new ConfezionatoBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setLingua(request.getParameter("lingua"));
				temp.setIdSet(request.getParameter("set"));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
				try {
					confezionatoDAO.saveConfezionato(temp);
				}catch(SQLException e) {
					e.printStackTrace();
				}break;
			}
			case "cartaSingola":{
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				CartaSingolaBean temp = new CartaSingolaBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setLingua(request.getParameter("lingua"));
				temp.setQuality(request.getParameter("qlt"));
				temp.setIdSet(request.getParameter("set"));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
				try {
					temp.setIdCarta(cartaDAO.retrieveByNome(request.getParameter("nome")).getId());
					cartasingolaDAO.saveCartaSingola(temp);
				}catch(SQLException e) {
					e.printStackTrace();
				}break;
			}
			case "box":{
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				BoxBean temp = new BoxBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setLingua(request.getParameter("lingua"));
				temp.setIdSet(request.getParameter("set"));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
				try {
					boxDAO.saveBox(temp);
				}catch(SQLException e) {
					e.printStackTrace();
				} break;
			} 
			case "pacchetto":{
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				PacchettoBean temp = new PacchettoBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setLingua(request.getParameter("lingua"));
				temp.setIdSet(request.getParameter("set"));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
				try {
					pacchettoDAO.savePacchetto(temp);
				}catch(SQLException e) {
					e.printStackTrace();
				} break;
			}
			case "structure":{
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				StructureDeckBean temp = new StructureDeckBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setLingua("italiano");
				temp.setIdSet((request.getParameter("set")));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
				try {
					structureDAO.saveStructureDeck(temp);
				}catch(SQLException e) {
					e.printStackTrace();
				} break;
			}
			case "tin":{
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				TinBean temp = new TinBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setLingua(request.getParameter("lingua"));
				temp.setIdSet(request.getParameter("set"));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
				try {
					tinDAO.saveTin(temp);
				}catch(SQLException e) {
					e.printStackTrace();
				} break;
			}
			case "deck":{
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				DeckBean temp = new DeckBean();
				temp.setDescrizione(request.getParameter("descr"));
				temp.setNome(request.getParameter("nome"));
				temp.setPrezzo((Integer.parseInt(request.getParameter("prezzo"))*100));
				temp.setSconto(Integer.parseInt(request.getParameter("sconto")));
				temp.setLingua(request.getParameter("lingua"));
				temp.setPathImg(uploadPath);
				temp.setMimeType(mimeType);
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
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/UploadImmagine");
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
