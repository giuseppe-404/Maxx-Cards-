package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.CartaBean;
import model.ProdottoBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import dao.CartaDao;
import dao.CartaDaoImpl;
import dao.ConfezionatoDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import dao.ProdottoYGODaoImpl;
import dao.TinDaoImpl;

/**
 * Servlet implementation class UploadImmagine
 */
@WebServlet("/UploadImmagine")
public class UploadImmagine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "uploads";
	private static final String PRODUCT_DIR = "prodotti";
	private static final String CARDS_DIR = "carte";
    private ProdottoDao prodottoDao = null;
    private CartaDao cartaDao = null;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        String file_path_product = getServletContext().getRealPath(File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR);
        String file_path_cards = getServletContext().getRealPath(File.separator + UPLOAD_DIR + File.separator + CARDS_DIR);
        File upload_product_file = new File(file_path_product);
        File upload_cards_file = new File(file_path_cards);
        if(!upload_product_file.exists()) upload_product_file.mkdir();
        if(!upload_cards_file.exists()) upload_product_file.mkdir();
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
		String action = request.getParameter("action");
		if(request.getParameter("isProdotto").equals("true")) {
			if(action.equalsIgnoreCase("show")) {
				int id = Integer.parseInt(request.getParameter("prodottoId"));
				try {
					ProdottoBean bean = prodottoDao.retrieveByKey(id);
					String mimeType = bean.getMimeType();
					String path = bean.getPathImg();
					response.setContentType(mimeType);
					try(InputStream is = new FileInputStream(path)) {
						OutputStream os = response.getOutputStream();
						is.transferTo(os);
					}catch(IOException ioe) {
						ioe.printStackTrace();
					}
				} catch( SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			if(action.equalsIgnoreCase("show")) {
				int id = Integer.parseInt(request.getParameter("cartaId"));
				try {
					CartaBean bean = cartaDao.retrieveByKey(id);
					String mimeType = bean.getMimeType();
					String path = bean.getPathImg();
					response.setContentType(mimeType);
					try(InputStream is = new FileInputStream(path)) {
						OutputStream os = response.getOutputStream();
						is.transferTo(os);
					}catch(IOException ioe) {
						ioe.printStackTrace();
					}
				} catch( SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(request.getParameter("isProdotto").equals("true")){
			if("upload".equalsIgnoreCase(action)) {
				int id = Integer.parseInt(request.getParameter("prodottoId"));
				Part part = request.getPart("image");
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						String mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						String uploadPath = getServletContext().getRealPath(File.separator + UPLOAD_DIR + File.separator + PRODUCT_DIR + File.separator +uniqueFileName);
						ProdottoBean bean = new ProdottoBean();
						bean.setId(id);
						bean.setPathImg(uploadPath);
						bean.setMimeType(mimeType);
						try {
							part.write(uploadPath);
							prodottoDao.changeImage(bean);
							System.out.println(uploadPath);
						}catch(SQLException e ) {
							e.printStackTrace();
						}
					}
				}
			}response.sendRedirect("");
		}
		else {
			if(request.getParameter("isProdotto").equals("true")) {
				int id = Integer.parseInt(request.getParameter("cartaId"));
				Part part = request.getPart("image");
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						String mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						String uploadPath = getServletContext().getRealPath(File.separator + UPLOAD_DIR + File.separator + CARDS_DIR + File.separator +uniqueFileName);
						CartaBean bean = new CartaBean();
						bean.setId(id);
						bean.setPathImg(uploadPath);
						bean.setMimeType(mimeType);
						try {
							part.write(uploadPath);
							cartaDao.changeImage(bean);
							System.out.println(uploadPath);
						}catch(SQLException e ) {
							e.printStackTrace();
						}
					}
				}
			}response.sendRedirect("");
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
