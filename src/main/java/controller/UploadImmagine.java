package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.CartaBean;
import model.ProdottoBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import javax.sql.DataSource;
import dao.CartaDao;
import dao.CartaDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

/**
 * Servlet implementation class UploadImmagine
 */
@WebServlet("/uploadImmagine")
public class UploadImmagine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMAGE_DIR = "images";
	private static final String UPLOAD_DIR = "uploads";
	private static final String PRODUCT_DIR = "prodotti";
	private static final String CARDS_DIR = "carta";
	private ProdottoDao prodottoDao = null;
    private CartaDao cartaDao = null;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        String base = getServletContext().getRealPath("");
        System.out.println(base);
        String file_path_product = base + File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR;
        String file_path_cards = base + File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + CARDS_DIR;
        File upload_product_file = new File(file_path_product);
        File upload_cards_file = new File(file_path_cards);
        if(!upload_product_file.exists()) upload_product_file.mkdirs();
        if(!upload_cards_file.exists()) upload_cards_file.mkdirs();
        prodottoDao = new ProdottoDaoImpl(ds);
        cartaDao = new CartaDaoImpl(ds);
    }
	
    public UploadImmagine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String temp = request.getParameter("isProdotto");
		String action = request.getParameter("action");
		if(temp.equals("true")) {
			if(action.equalsIgnoreCase("show")) {
				int id = Integer.parseInt(request.getParameter("prodottoId"));
				try {
					ProdottoBean bean = prodottoDao.retrieveByKey(id);
					String mimeType = bean.getMimeType();
					String path = bean.getPathImg();
					
					File file = new File(path);
					if(!file.exists() || path.equals("")) {
						path = getServletContext().getRealPath(""); 
						path = path + "images" + File.separator + "no_image_available.jpeg";
						mimeType = "image/jpeg";
					}
					System.out.println(path + "\n" +  mimeType);
					response.setContentType(mimeType);
					try(InputStream is = new FileInputStream(path)) {
						OutputStream os = response.getOutputStream();
						is.transferTo(os);
					}catch(IOException ioe) {
						request.setAttribute("msg","Errore nel caricamento dell'immagine!");
						response.sendError(500);
					}
				} catch( SQLException e) {
					request.setAttribute("msg", "Errore nell'ottenimento dell'immagine dal database!");
					response.sendError(500);
				}
			}
		} else if(temp.equals("false")){
			if(action.equalsIgnoreCase("show")) {
				int id = Integer.parseInt(request.getParameter("prodottoId"));
				try {
					CartaBean bean = cartaDao.retrieveByKey(id);
					String mimeType = bean.getMimeType();
					String path = bean.getPathImg();
					if(path == null || path.equals("")) {
						path = getServletContext().getRealPath(""); 
						path = path + "images" +
							File.separator	+ "no_image_available.jpeg";
						mimeType = "image/jpeg";
					}
					System.out.println(path + "\n" +  mimeType);
					File file = new File(path);
					if(!file.exists()) {
						path = getServletContext().getRealPath(""); 
						path = path + "images" +
								File.separator + "no_image_available.jpeg";
						mimeType = "image/jpeg";
					}
					response.setContentType(mimeType);
					try(InputStream is = new FileInputStream(path)) {
						OutputStream os = response.getOutputStream();
						is.transferTo(os);
					}catch(IOException ioe) {
						request.setAttribute("msg","Errore nel caricamento dell'immagine!");
						response.sendError(500);
					}
				} catch( SQLException e) {
					request.setAttribute("msg", "Errore nell'ottenimento dell'immagine dal database!");
					response.sendError(500);
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = (String) session.getAttribute("action");
		String temp = (String) session.getAttribute("isProdotto");
		String pathImg = (String) session.getAttribute("pathImg");
		Part img = (Part) request.getSession().getAttribute("part");
		session.removeAttribute("action");
		session.removeAttribute("isProdotto");
		session.removeAttribute("pathImg");
		session.removeAttribute("part");
		System.out.println(temp);
		if(temp.equals("true")){
			if("upload".equalsIgnoreCase(action)) {
				Part part = request.getPart("image");
				if(part != null) {
					part.write(pathImg);	
				}response.sendRedirect("");
			} else if("change".equalsIgnoreCase(action)) {
				String oldPath = (String)session.getAttribute("oldPath");
				session.removeAttribute("oldPath");
				File old = new File(oldPath);
				if(old.exists()) {
					old.delete();
				}
				Part part = request.getPart("image");
				if(part != null) {
					part.write(pathImg);
				} response.sendRedirect("");
			}
		} else {
			if("upload".equalsIgnoreCase(action)) {
				Part part = request.getPart("image");
				if(part != null) {
					part.write(pathImg);
				}response.sendRedirect("");
			} else if("change".equalsIgnoreCase(action)) {
				String oldPath = (String)session.getAttribute("oldPath");
				session.removeAttribute("oldPath");
				File old = new File(oldPath);
				if(old.exists()) {
					old.delete();
				}
				Part part = request.getPart("image");
				if(part != null) {
					part.write(pathImg);
				} response.sendRedirect("");
			}
		}
	}
}