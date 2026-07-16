package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.MagiaBean;
import model.MostroBean;
import model.TrappolaBean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.UUID;

import javax.sql.DataSource;

import dao.MagiaDao;
import dao.MagiaDaoImpl;
import dao.MostroDao;
import dao.MostroDaoImpl;
import dao.TrappolaDao;
import dao.TrappolaDaoImpl;

/**
 * Servlet implementation class nuovaCarta
 */
@WebServlet("/nuovaCarta")
public class nuovaCarta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMAGE_DIR = "images";
	private static final String UPLOAD_DIR = "uploads";
	private static final String CARDS_DIR = "carta";
	private MostroDao mostroDao = null;
	private MagiaDao magiaDao = null;
	private TrappolaDao trappolaDao = null;
       
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        mostroDao = new MostroDaoImpl(ds);
        magiaDao = new MagiaDaoImpl(ds);
        trappolaDao = new TrappolaDaoImpl(ds);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public nuovaCarta() {
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
		if(request.getParameter("classe_carta") != null) {
			String classe = request.getParameter("classe_carta").toLowerCase();
			if(classe.equals("mostro")) {
				MostroBean bean = new MostroBean();
				if(request.getParameter("nome_it") != null) {
					bean.setNomeIt(request.getParameter("nome_it"));
				} 
				if(request.getParameter("nome_en") != null) {
					bean.setNomeEn(request.getParameter("nome_en"));
				}
				if(request.getParameter("nome_jp") != null) {
					bean.setNomeIt(request.getParameter("nome_jp"));
				}
				if(request.getParameter("pnt_carta") != null) {
					bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta")));
				} else {
					bean.setPunteggio(101);
				}
				if(request.getParameter("testo_carta") != null) {
					bean.setTesto(request.getParameter("testo_carta"));
				}
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + CARDS_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				bean.setMimeType(mimeType);
				bean.setPathImg(uploadPath);
				if(request.getParameter("tipologia_carta") != null) {
					bean.setTipologia(request.getParameter("tipologia_carta"));
				}
				if(request.getParameter("livello_mostro") != null) {
					bean.setLivello(Integer.parseInt(request.getParameter("livello_mostro")));
				}
				if(request.getParameter("categoria_mostro") != null) {
					bean.setCategoria(request.getParameter("categoria_mostro"));
				}
				if(request.getParameter("attributo_mostro") != null) {
					bean.setAttributo(request.getParameter("attributi_mostro"));
				}
				if(request.getParameter("attacco_mostro") != null) {
					bean.setAtk(Integer.parseInt(request.getParameter("attacco_mostro")));
				}
				if(request.getParameter("difesa") != null) {
					bean.setDef(Integer.parseInt(request.getParameter("difesa_mostro")));
				}
				if(request.getParameter("tuner_mostro") != null) {
					if(Integer.parseInt(request.getParameter("tuner_mostro")) == 1) {
						bean.setTuner(1);
					} else if(Integer.parseInt(request.getParameter("tuner_mostro")) == 0) {
						bean.setTuner(0);
					}
				}
				if(request.getParameter("scala_mostro") != null) {
					bean.setScalaPendulum(Integer.parseInt(request.getParameter("scala_mostro")));
				}
				if(request.getParameter("tipo_mostro") != null) {
					bean.setTipo(request.getParameter("tipo_mostro"));
				}
				if(request.getParameter("frecce_link") != null) {
					String[] list = request.getParameterValues("frecce_Link");
    				BitSet bs = new BitSet();
    				for(int i = 0; i < 8; i++) {
    					 if(list[i].equals("true"))
    						 bs.set(i);
    				}
    				bean.setFrecceLink(bs);
				}
				try {
					mostroDao.saveMostro(bean);
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} else if (classe.equals("magia")) {
				MagiaBean bean = new MagiaBean();
				if(request.getParameter("nome_it") != null) {
					bean.setNomeIt(request.getParameter("nome_it"));
				} 
				if(request.getParameter("nome_en") != null) {
					bean.setNomeEn(request.getParameter("nome_en"));
				}
				if(request.getParameter("nome_jp") != null) {
					bean.setNomeIt(request.getParameter("nome_jp"));
				}
				if(request.getParameter("pnt_carta") != null) {
					bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta")));
				} else {
					bean.setPunteggio(101);
				}
				if(request.getParameter("testo_carta") != null) {
					bean.setTesto(request.getParameter("testo_carta"));
				}
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + CARDS_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				bean.setMimeType(mimeType);
				bean.setPathImg(uploadPath);
				if(request.getParameter("tipologia_carta") != null) {
					bean.setTipologia(request.getParameter("tipologia_carta"));
				}
				try {
					magiaDao.saveCarta(bean);
				}catch(SQLException e) {
					e.printStackTrace();
				}
			} else if (classe.equals("trappola")) {
				TrappolaBean bean = new TrappolaBean();
				if(request.getParameter("nome_it") != null) {
					bean.setNomeIt(request.getParameter("nome_it"));
				} 
				if(request.getParameter("nome_en") != null) {
					bean.setNomeEn(request.getParameter("nome_en"));
				}
				if(request.getParameter("nome_jp") != null) {
					bean.setNomeIt(request.getParameter("nome_jp"));
				}
				if(request.getParameter("pnt_carta") != null) {
					bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta")));
				} else {
					bean.setPunteggio(101);
				}
				if(request.getParameter("testo_carta") != null) {
					bean.setTesto(request.getParameter("testo_carta"));
				}
				Part part = request.getPart("image");
				String mimeType = null;
				String uploadPath = null;
				if(part != null) {
					String originalFileName = part.getSubmittedFileName();
					if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						mimeType = part.getContentType();
						String uniqueFileName = buildUniqueFileName(part);
						uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + CARDS_DIR + File.separator +uniqueFileName;
					}
				}
				request.getSession().setAttribute("pathImg", uploadPath);
				request.getSession().setAttribute("part",part);
				bean.setMimeType(mimeType);
				bean.setPathImg(uploadPath);
				if(request.getParameter("tipologia_carta") != null) {
					bean.setTipologia(request.getParameter("tipologia_carta"));
				}
				try {
					trappolaDao.saveCarta(bean);
				}catch(SQLException e) {
					e.printStackTrace();
				}
			} 	
			RequestDispatcher dispatcher = request.getRequestDispatcher("/UploadImmagine");
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
