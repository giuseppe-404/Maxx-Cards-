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
import model.CartaBean;
import model.MagiaBean;
import model.MostroBean;
import model.TipoBean;
import model.TrappolaBean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.UUID;

import javax.sql.DataSource;

import dao.CartaDao;
import dao.CartaDaoImpl;
import dao.MagiaDao;
import dao.MagiaDaoImpl;
import dao.MostroDao;
import dao.MostroDaoImpl;
import dao.TipoDao;
import dao.TipoDaoImpl;
import dao.TrappolaDao;
import dao.TrappolaDaoImpl;

/**
 * Servlet implementation class nuovaCarta
 */
@WebServlet("/nuovaCarta")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 2* 1024 * 1024)
public class nuovaCarta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMAGE_DIR = "images";
	private static final String UPLOAD_DIR = "uploads";
	private static final String CARDS_DIR = "carta";
	private CartaDao cartaDao = null;
	private MostroDao mostroDao = null;
	private MagiaDao magiaDao = null;
	private TrappolaDao trappolaDao = null;
	private ArrayList<String> attributi = null;
	private ArrayList<String> tipologiaM = null;
	private ArrayList<String> categoria = null;
	private TipoDao tipoDao = null;
 	private ArrayList<String> tipologiaMa = null;
    private ArrayList<String> tipologiaT = null;
    
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
        tipoDao = new TipoDaoImpl(ds);
        cartaDao = new CartaDaoImpl(ds);
        attributi = new ArrayList<>();
        tipologiaM = new ArrayList<>();
        categoria = new ArrayList<>();
        tipologiaMa = new ArrayList<>();
        tipologiaT = new ArrayList<>();
        attributi.add("luce");
        attributi.add("oscurita");
        attributi.add("divino");
        attributi.add("terra");
        attributi.add("acqua");
        attributi.add("fuoco");
        attributi.add("vento");
        tipologiaM.add("none");
        tipologiaM.add("fusione");
        tipologiaM.add("synchro");
        tipologiaM.add("xyz");
        tipologiaM.add("link");
        tipologiaM.add("rituale");
        categoria.add("none");
        categoria.add("toon");
        categoria.add("gemello");
        categoria.add("spirit");
        categoria.add("unione");
        tipologiaMa.add("none");
        tipologiaMa.add("rapida");
        tipologiaMa.add("terreno");
        tipologiaMa.add("equipaggiamento");
        tipologiaMa.add("rituale");
        tipologiaMa.add("continua");
        tipologiaT.add("none");
        tipologiaT.add("contro");
        tipologiaT.add("continua");
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
		HttpSession session = request.getSession();
		session.setAttribute("isProdotto", "false");
		boolean img = false;
		boolean valido = true;
		String action_carta = request.getParameter("action");
		if(action_carta != null) {
			if(action_carta.equals("delete")) {
				if(request.getParameter("old_id") != null) {
					int oldId = Integer.parseInt(request.getParameter("old_id"));
					try{
						magiaDao.deleteCarta(oldId);
					} catch (SQLException e) {
						request.setAttribute("msg", "Errore nell'eliminazione della carta!");
						response.sendError(500);
					}
				} else {
					request.setAttribute("msg", "Errore nella richiesta, parametro mancante!");
					response.sendError(400);
				}
			} else if(action_carta.equals("alter")) {
				session.setAttribute("action", "change");
				if(request.getParameter("old_id") != null) {
					int oldId = Integer.parseInt(request.getParameter("old_id"));
					try{
						int prec = cartaDao.cartaType(oldId);
						switch(prec) {
						case 0: 
						{
							CartaBean carta = cartaDao.retrieveByKey(oldId);
							CartaBean bean = new CartaBean();
							bean.setId(oldId);
							if(request.getParameter("id_carta") != null) {
								bean.setId(Integer.parseInt(request.getParameter("id_carta")));
								if(carta.getId() != bean.getId()) {
									cartaDao.changeId(carta, prec);
								}
							}
							if(request.getParameter("nome_it") != null) {
								bean.setNomeIt(request.getParameter("nome_it").trim());
								if(!carta.getNomeIt().equals(bean.getNomeIt())) {
									cartaDao.changeNomeIt(bean);
								}
							}
							if(request.getParameter("nome_en") != null) {
								bean.setNomeEn(request.getParameter("nome_en").trim());
								if(!carta.getNomeEn().equals(bean.getNomeEn())) {
									cartaDao.changeNomeEn(bean);
								}
							}
							if(request.getParameter("nome_jp") != null) {
								bean.setNomeJp(request.getParameter("nome_jp").trim());
								if(!carta.getNomeJp().equals(bean.getNomeJp())) {
									cartaDao.changeNomeJp(bean);
								}
							}
							if(request.getParameter("pnt_carta") != null) {
								bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta").trim()));
								if(carta.getPunteggio() != bean.getPunteggio()) {
									cartaDao.changePunteggio(bean);
								}
							} else {
								CartaBean temp = new CartaBean();
								temp.setPunteggio(101);
								temp.setId(carta.getId());
								cartaDao.changePunteggio(carta);
							}
							if(request.getParameter("testo_carta") != null) {
								bean.setTesto(request.getParameter("testo_carta").trim());
								if(!carta.getTesto().equals(bean.getTesto())){
									cartaDao.changeTesto(bean);
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
									uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + CARDS_DIR + File.separator +uniqueFileName;
									System.out.println("immagine valida...");
									session.setAttribute("pathImg", uploadPath);
									session.setAttribute("part",part);
									session.setAttribute("oldPath",carta.getPathImg());
									bean.setMimeType(mimeType);
									bean.setPathImg(uploadPath);
									cartaDao.changeImage(bean);
									RequestDispatcher dispatcher = request.getRequestDispatcher("/UploadImmagine");
									dispatcher.forward(request, response);
								}
							}
							break;
							} case 1:{
								MostroBean carta = mostroDao.retrieveByKey(oldId);
								MostroBean bean = new MostroBean();
								bean.setId(oldId);
								if(request.getParameter("id_carta") != null) {
									bean.setId(Integer.parseInt(request.getParameter("id_carta")));
									if(carta.getId() != bean.getId()) {
										mostroDao.changeId(carta, prec);
									}
								}
								if(request.getParameter("nome_it") != null) {
									bean.setNomeIt(request.getParameter("nome_it").trim());
									if(!carta.getNomeIt().equals(bean.getNomeIt())) {
										mostroDao.changeNomeIt(bean);
									}
								}
								if(request.getParameter("nome_en") != null) {
									bean.setNomeEn(request.getParameter("nome_en").trim());
									if(!carta.getNomeEn().equals(bean.getNomeEn())) {
										mostroDao.changeNomeEn(bean);
									}
								}
								if(request.getParameter("nome_jp") != null) {
									bean.setNomeJp(request.getParameter("nome_jp").trim());
									if(!carta.getNomeJp().equals(bean.getNomeJp())) {
										mostroDao.changeNomeJp(bean);
									}
								}
								if(request.getParameter("pnt_carta") != null) {
									bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta").trim()));
									if(carta.getPunteggio() != bean.getPunteggio()) {
										mostroDao.changePunteggio(bean);
									}
								} else {
									CartaBean temp = new CartaBean();
									temp.setPunteggio(101);
									temp.setId(carta.getId());
									mostroDao.changePunteggio(carta);
								}
								if(request.getParameter("testo_carta") != null) {
									bean.setTesto(request.getParameter("testo_carta").trim());
									if(!carta.getTesto().equals(bean.getTesto())){
										mostroDao.changeTesto(bean);
									}
								}
								if(request.getParameter("tipologia_carta") != null) {
									if(tipologiaM.contains((String)request.getParameter("tipologia_carta").trim())) {
										bean.setTipologia(request.getParameter("tipologia_carta").trim());
										if(!carta.getTipologia().equals(bean.getTipologia())) {
											mostroDao.changeTipologia(bean);
										}
									}
								}
								if(request.getParameter("livello_mostro") != null) {
									if(Integer.parseInt(request.getParameter("livello_mostro").trim()) >= 0 && Integer.parseInt(request.getParameter("livello_mostro").trim()) <= 13) {
										bean.setLivello(Integer.parseInt(request.getParameter("livello_mostro").trim()));
										if(carta.getLivello() != bean.getLivello()) {
											mostroDao.changeLivello(bean);
										}
									}
								}
								if(request.getParameter("categoria_mostro") != null) {
									if(categoria.contains(request.getParameter("categoria_mostro").trim())) {
										bean.setCategoria(request.getParameter("categoria_mostro").trim());
										if(!carta.getCategoria().equals(bean.getCategoria())) {
											mostroDao.changeCategoria(bean);
										}
									}
								}
								if(request.getParameter("attributo_mostro") != null) {
									if(attributi.contains(request.getParameter("attributo_mostro").trim())) {
										bean.setAttributo(request.getParameter("attributi_mostro").trim());
										if(!carta.getAttributo().equals(bean.getAttributo())) {
											mostroDao.changeAttributo(bean);
										}
									}
								}
								if(request.getParameter("attacco_mostro") != null) {
									if(Integer.parseInt(request.getParameter("attacco_mostro").trim()) >= -1) {
										bean.setAtk(Integer.parseInt(request.getParameter("attacco_mostro").trim()));
										if(carta.getAtk() != bean.getAtk()) {
											mostroDao.changeAtk(bean);
										}
									} 
								} 
								if(request.getParameter("difesa_mostro") != null) {
									if(Integer.parseInt(request.getParameter("difesa_mostro").trim()) >= -1) {
										bean.setDef(Integer.parseInt(request.getParameter("difesa_mostro").trim()));
										if(carta.getDef() != bean.getDef()) {
											mostroDao.changeDef(bean);
										}
									} 
								}
								if(request.getParameter("tuner_mostro") != null) {
									if(Integer.parseInt(request.getParameter("tuner_mostro").trim()) == 1 || Integer.parseInt(request.getParameter("tuner_mostro").trim()) == 0) {
										bean.setTuner(Integer.parseInt(request.getParameter("tuner_mostro").trim()));
										if(carta.getTuner() != bean.getTuner()) {
											mostroDao.changeTuner(bean);
										}
									}
								}
								if(request.getParameter("scala_mostro") != null) {
									if(Integer.parseInt(request.getParameter("scala_mostro").trim()) >= 0 && Integer.parseInt(request.getParameter("livello_mostro").trim()) <= 13) {
										bean.setScalaPendulum(Integer.parseInt(request.getParameter("scala_mostro").trim()));
										if(carta.getScalaPendulum() != bean.getScalaPendulum()) {
											mostroDao.changeScalaPendulum(bean);
										}
									} else if(request.getParameter("scala_mostro").equals("")){
										bean.setScalaPendulum(Integer.parseInt(request.getParameter("scala_mostro").trim()));
										if(carta.getScalaPendulum() != bean.getScalaPendulum()) {
											mostroDao.changeScalaPendulum(bean);
										}
									}
								}
								if(request.getParameter("tipo_mostro") != null) {
									TipoBean filter = new TipoBean();
									if(request.getParameter("tipo_mostro") != null) {
										if(request.getParameter("tipo_mostro").trim().equals("nuovo")) {
											filter.setTipo(request.getParameter("nuovo_tipo").trim());
											tipoDao.saveTipo(filter);
											bean.setTipo(filter.getTipo());
											mostroDao.changeTipo(bean);
										}else {
											filter.setTipo(request.getParameter("tipo_mostro").trim());
											tipoDao.retrieveByKey(request.getParameter("tipo_mostro").trim());
											bean.setTipo(request.getParameter("tipo_mostro").trim());
											if(!carta.getTipo().equals(bean.getTipo())) {
												mostroDao.changeTipo(bean);
											}
										}
									}
								}
								if(request.getParameter("frecce_link") != null) {
									String[] list = request.getParameterValues("frecce_Link");
				    				BitSet bs = new BitSet();
				    				for(int i = 0; i < 8; i++) {
				    					 if(list[i].equals("true"))
				    						 bs.set(i);
				    				}
				    				bean.setFrecceLink(bs);
				    				if(!carta.getFrecceLink().equals(bean.getFrecceLink())) {
				    					mostroDao.changeFrecceLink(bean);
				    				}
								} Part part = request.getPart("image");
								if(part != null) {
									String originalFileName = part.getSubmittedFileName();
									if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
										String mimeType = part.getContentType();
										String uniqueFileName = buildUniqueFileName(part);
										String uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + CARDS_DIR + File.separator +uniqueFileName;
										session.setAttribute("pathImg", uploadPath);
										session.setAttribute("part",part);
										session.setAttribute("oldPath",carta.getPathImg());
										bean.setMimeType(mimeType);
										bean.setPathImg(uploadPath);
										mostroDao.changeImage(bean);
										RequestDispatcher dispatcher = request.getRequestDispatcher("/UploadImmagine");
										dispatcher.forward(request, response);
									}
								}break;
							} case 2:{
								MagiaBean carta = magiaDao.retrieveByKey(oldId);
								MagiaBean bean = new MagiaBean();
								bean.setId(oldId);
								if(request.getParameter("id_carta") != null) {
									bean.setId(Integer.parseInt(request.getParameter("id_carta")));
									if(carta.getId() != bean.getId()) {
										magiaDao.changeId(carta, prec);
									}
								}
								if(request.getParameter("nome_it") != null) {
									bean.setNomeIt(request.getParameter("nome_it").trim());
									if(!carta.getNomeIt().equals(bean.getNomeIt())) {
										magiaDao.changeNomeIt(bean);
									}
								}
								if(request.getParameter("nome_en") != null) {
									bean.setNomeEn(request.getParameter("nome_en").trim());
									if(!carta.getNomeEn().equals(bean.getNomeEn())) {
										magiaDao.changeNomeEn(bean);
									}
								}
								if(request.getParameter("nome_jp") != null) {
									bean.setNomeJp(request.getParameter("nome_jp").trim());
									if(!carta.getNomeJp().equals(bean.getNomeJp())) {
										magiaDao.changeNomeJp(bean);
									}
								}
								if(request.getParameter("pnt_carta") != null) {
									bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta").trim()));
									if(carta.getPunteggio() != bean.getPunteggio()) {
										magiaDao.changePunteggio(bean);
									}
								} else {
									CartaBean temp = new CartaBean();
									temp.setPunteggio(101);
									temp.setId(carta.getId());
									magiaDao.changePunteggio(carta);
								}
								if(request.getParameter("testo_carta") != null) {
									bean.setTesto(request.getParameter("testo_carta").trim());
									if(!carta.getTesto().equals(bean.getTesto())){
										magiaDao.changeTesto(bean);
									}
								}
								if(request.getParameter("tipologia_carta") != null) {
									if(tipologiaMa.contains((String)request.getParameter("tipologia_carta").trim())) {
										bean.setTipologia(request.getParameter("tipologia_carta").trim());
										if(!carta.getTipologia().equals(bean.getTipologia())) {
											magiaDao.changeTipologia(bean);
										}
									}
								}
								Part part = request.getPart("image");
								if(part != null) {
									String originalFileName = part.getSubmittedFileName();
									if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
										String mimeType = part.getContentType();
										String uniqueFileName = buildUniqueFileName(part);
										String uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + CARDS_DIR + File.separator +uniqueFileName;
										session.setAttribute("pathImg", uploadPath);
										session.setAttribute("part",part);
										session.setAttribute("oldPath",carta.getPathImg());
										bean.setMimeType(mimeType);
										bean.setPathImg(uploadPath);
										magiaDao.changeImage(bean);
										RequestDispatcher dispatcher = request.getRequestDispatcher("/UploadImmagine");
										dispatcher.forward(request, response);
									}
								}break;
								
							} case 3: {
								TrappolaBean carta = trappolaDao.retrieveByKey(oldId);
								TrappolaBean bean = new TrappolaBean();
								bean.setId(oldId);
								if(request.getParameter("id_carta") != null) {
									bean.setId(Integer.parseInt(request.getParameter("id_carta")));
									if(carta.getId() != bean.getId()) {
										trappolaDao.changeId(carta, prec);
									}
								}
								if(request.getParameter("nome_it") != null) {
									bean.setNomeIt(request.getParameter("nome_it").trim());
									if(!carta.getNomeIt().equals(bean.getNomeIt())) {
										trappolaDao.changeNomeIt(bean);
									}
								}
								if(request.getParameter("nome_en") != null) {
									bean.setNomeEn(request.getParameter("nome_en").trim());
									if(!carta.getNomeEn().equals(bean.getNomeEn())) {
										trappolaDao.changeNomeEn(bean);
									}
								}
								if(request.getParameter("nome_jp") != null) {
									bean.setNomeJp(request.getParameter("nome_jp").trim());
									if(!carta.getNomeJp().equals(bean.getNomeJp())) {
										trappolaDao.changeNomeJp(bean);
									}
								}
								if(request.getParameter("pnt_carta") != null) {
									bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta").trim()));
									if(carta.getPunteggio() != bean.getPunteggio()) {
										trappolaDao.changePunteggio(bean);
									}
								} else {
									CartaBean temp = new CartaBean();
									temp.setPunteggio(101);
									temp.setId(carta.getId());
									trappolaDao.changePunteggio(carta);
								}
								if(request.getParameter("testo_carta") != null) {
									bean.setTesto(request.getParameter("testo_carta").trim());
									if(!carta.getTesto().equals(bean.getTesto())){
										trappolaDao.changeTesto(bean);
									}
								}
								if(request.getParameter("tipologia_carta") != null) {
									if(tipologiaT.contains((String)request.getParameter("tipologia_carta").trim())) {
										bean.setTipologia(request.getParameter("tipologia_carta").trim());
										if(!carta.getTipologia().equals(bean.getTipologia())) {
											trappolaDao.changeTipologia(bean);
										}
									}
								}
								Part part = request.getPart("image");
								if(part != null) {
									String originalFileName = part.getSubmittedFileName();
									if(originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
										String mimeType = part.getContentType();
										String uniqueFileName = buildUniqueFileName(part);
										String uploadPath = getServletContext().getRealPath("")+ File.separator + IMAGE_DIR + File.separator + UPLOAD_DIR + File.separator + CARDS_DIR + File.separator +uniqueFileName;
										session.setAttribute("pathImg", uploadPath);
										session.setAttribute("part",part);
										session.setAttribute("oldPath",carta.getPathImg());
										bean.setMimeType(mimeType);
										bean.setPathImg(uploadPath);
										trappolaDao.changeImage(bean);
										RequestDispatcher dispatcher = request.getRequestDispatcher("/UploadImmagine");
										dispatcher.forward(request, response);
									}
								}break;
							}
						}
					} catch (SQLException e) {
						request.setAttribute("msg","Errore nell'ottenimento della carta richiesta!");
						response.sendError(500);
					}
				} else {
					request.setAttribute("msg","Errore nella richiesta, parametro mancante!");
					response.sendError(400);
				}
			} else if(action_carta.equals("add")) {
				request.getSession().setAttribute("action", "upload");
				if(request.getParameter("classe_carta") != null) {
					String classe = request.getParameter("classe_carta").toLowerCase();
					if(classe.equals("mostro")) {
						MostroBean bean = new MostroBean();
						if(request.getParameter("id_carta") != null) {
							bean.setId(Integer.parseInt(request.getParameter("id_carta")));
							System.out.println("id valido...");
						}
						if(request.getParameter("nome_it") != null) {
							bean.setNomeIt(request.getParameter("nome_it").trim());
							System.out.println("nome_it valido...");
						} 
						if(request.getParameter("nome_en") != null) {
							bean.setNomeEn(request.getParameter("nome_en").trim());
							System.out.println("nome_en valido...");
						}
						if(request.getParameter("nome_jp") != null) {
							bean.setNomeIt(request.getParameter("nome_jp").trim());
							System.out.println("nome_jp valido...");
						}
						if(request.getParameter("pnt_carta") != null) {
							bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta").trim()));
							System.out.println("pnt_carta valido...");
						} else {
							bean.setPunteggio(101);
						}
						if(request.getParameter("testo_carta") != null) {
							bean.setTesto(request.getParameter("testo_carta").trim());
							System.out.println("testo_carta valido...");
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
								img = true;
								System.out.println("immagine valida...");
								request.getSession().setAttribute("pathImg", uploadPath);
								request.getSession().setAttribute("part",part);
							}
						}
						bean.setMimeType(mimeType);
						bean.setPathImg(uploadPath);
						if(request.getParameter("tipologia_carta") != null) {
							if(tipologiaM.contains((String)request.getParameter("tipologia_carta").trim())) {
								bean.setTipologia(request.getParameter("tipologia_carta").trim());
								System.out.println("tipologia valida...");
							} else valido = false;
						} else valido = false;
						if(request.getParameter("livello_mostro") != null) {
							if(Integer.parseInt(request.getParameter("livello_mostro").trim()) >= 0 && Integer.parseInt(request.getParameter("livello_mostro").trim()) <= 13) {
								bean.setLivello(Integer.parseInt(request.getParameter("livello_mostro").trim()));
								System.out.println("livello valido...");
							} else valido = false;
						} else valido = false;
						if(request.getParameter("categoria_mostro") != null) {
							if(categoria.contains(request.getParameter("categoria_mostro").trim())) {
								bean.setCategoria(request.getParameter("categoria_mostro").trim());
								System.out.println("categoria valido...");
							} else valido = false;
						} else valido = false;
						if(request.getParameter("attributo_mostro") != null) {
							if(attributi.contains(request.getParameter("attributo_mostro").trim())) {
								bean.setAttributo(request.getParameter("attributi_mostro").trim());
								System.out.println("attributo valido...");
							} else valido = false;
						} else valido = false;
						if(request.getParameter("attacco_mostro") != null) {
							if(Integer.parseInt(request.getParameter("attacco_mostro").trim()) >= -1) {
								bean.setAtk(Integer.parseInt(request.getParameter("attacco_mostro").trim()));
								System.out.println("attaco valido...");
							} else valido = false;
						} else valido = false;
						if(request.getParameter("difesa_mostro") != null) {
							if(Integer.parseInt(request.getParameter("difesa_mostro").trim()) >= -1) {
								bean.setDef(Integer.parseInt(request.getParameter("difesa_mostro").trim()));
								System.out.println("difesa valido...");
							} else valido = false;
						} else valido = false;
						if(request.getParameter("tuner_mostro") != null) {
							if(Integer.parseInt(request.getParameter("tuner_mostro").trim()) == 1 || Integer.parseInt(request.getParameter("tuner_mostro").trim()) == 0) {
								bean.setTuner(Integer.parseInt(request.getParameter("tuner_mostro").trim()));
								System.out.println("tuner valido...");
							} else valido = false;
						} else valido = false;
						if(request.getParameter("scala_mostro") != null) {
							if(Integer.parseInt(request.getParameter("scala_mostro").trim()) >= 0 && Integer.parseInt(request.getParameter("livello_mostro").trim()) <= 13) {
								bean.setScalaPendulum(Integer.parseInt(request.getParameter("scala_mostro").trim()));
								System.out.println("scala pendulum valido...");
							} else if(request.getParameter("scala_mostro").equals("")){
								System.out.println("scala pendulum valido...");
							} else {
								valido = false;
							}
						} else valido = false;
						if(request.getParameter("tipo_mostro") != null) {
							try {
								TipoBean filter = new TipoBean();
								if(request.getParameter("tipo_mostro") != null) {
									if(request.getParameter("tipo_mostro").trim().equals("nuovo")) {
										filter.setTipo(request.getParameter("nuovo_tipo").trim());
										tipoDao.saveTipo(filter);
										bean.setTipo(filter.getTipo());
										System.out.println("tipo valido...");
									}else {
										filter.setTipo(request.getParameter("tipo_mostro").trim());
										tipoDao.retrieveByKey(request.getParameter("tipo_mostro").trim());
										bean.setTipo(request.getParameter("tipo_mostro").trim());
										System.out.println("tipo valido...");
									}
								}
								
							} catch (SQLException e) {
								valido = false;
							}
						} else valido = false;
						if(request.getParameter("frecce_link") != null) {
							String[] list = request.getParameterValues("frecce_Link");
		    				BitSet bs = new BitSet();
		    				for(int i = 0; i < 8; i++) {
		    					 if(list[i].equals("true"))
		    						 bs.set(i);
		    				}
		    				bean.setFrecceLink(bs);
		    				System.out.println("frecce link valido...");
						}
						if(valido) {
							try {
								mostroDao.saveMostro(bean);
							} catch(SQLException e) {
								request.setAttribute("msg","Errore nel salvataggio del mostro nel database!");
								response.sendError(500);
							}
						}
					} else if (classe.equals("magia")) {
						MagiaBean bean = new MagiaBean();
						if(request.getParameter("id_carta") != null) {
							bean.setId(Integer.parseInt(request.getParameter("id_carta")));
							System.out.println("id valido...");
						}
						if(request.getParameter("nome_it") != null) {
							bean.setNomeIt(request.getParameter("nome_it").trim());
							System.out.println("nome_it valido...");
						} 
						if(request.getParameter("nome_en") != null) {
							bean.setNomeEn(request.getParameter("nome_en").trim());
							System.out.println("nome_en valido...");
						}
						if(request.getParameter("nome_jp") != null) {
							bean.setNomeIt(request.getParameter("nome_jp").trim());
							System.out.println("nome_jp valido...");
						}
						if(request.getParameter("pnt_carta") != null) {
							bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta").trim()));
							System.out.println("pnt_carta valido...");
						} else {
							bean.setPunteggio(101);
						}
						if(request.getParameter("testo_carta") != null) {
							bean.setTesto(request.getParameter("testo_carta").trim());
							System.out.println("testo_carta valido...");
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
								img = true;
								request.getSession().setAttribute("pathImg", uploadPath);
								request.getSession().setAttribute("part",part);
								System.out.println("immagine valido...");
							}
						}
						bean.setMimeType(mimeType);
						bean.setPathImg(uploadPath);
						if(request.getParameter("tipologia_carta") != null) {
							if(tipologiaMa.contains(request.getParameter("tipologia_carta").trim())) {
								bean.setTipologia(request.getParameter("tipologia_carta").trim());
								System.out.println("tipologia_carta valido...");
							} else valido = false;
		 				} else valido = false;
						if(valido) {
							try {
								magiaDao.saveCarta(bean);
							}catch(SQLException e) {
								request.setAttribute("msg","Errore nel salvataggio della magia nel database!");
								response.sendError(500);
							}
						}
					} else if (classe.equals("trappola")) {
						TrappolaBean bean = new TrappolaBean();
						if(request.getParameter("id_carta") != null) {
							bean.setId(Integer.parseInt(request.getParameter("id_carta")));
							System.out.println("id valido...");
						}
						if(request.getParameter("nome_it") != null) {
							bean.setNomeIt(request.getParameter("nome_it").trim());
							System.out.println("nome_it valido...");
						} 
						if(request.getParameter("nome_en") != null) {
							bean.setNomeEn(request.getParameter("nome_en").trim());
							System.out.println("nome_en valido...");
						}
						if(request.getParameter("nome_jp") != null) {
							bean.setNomeIt(request.getParameter("nome_jp").trim());
							System.out.println("nome_jp valido...");
						}
						if(request.getParameter("pnt_carta") != null) {
							bean.setPunteggio(Integer.parseInt(request.getParameter("pnt_carta").trim()));
							System.out.println("pnt_carta valido...");
						} else {
							bean.setPunteggio(101);
						}
						if(request.getParameter("testo_carta") != null) {
							bean.setTesto(request.getParameter("testo_carta").trim());
							System.out.println("testo_carta valido...");
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
								img = true;
								request.getSession().setAttribute("pathImg", uploadPath);
								request.getSession().setAttribute("part",part);
								System.out.println("immagine valido...");
							}
						}
						bean.setMimeType(mimeType);
						bean.setPathImg(uploadPath);
						if(request.getParameter("tipologia_carta") != null) {
							if(tipologiaT.contains(request.getParameter("tipologia_carta").trim())) {
								bean.setTipologia(request.getParameter("tipologia_carta").trim());
								System.out.println("tipologia valido...");
							} else valido = false;
						} else valido = false;
						if(valido) {
							try {
								trappolaDao.saveCarta(bean);
							}catch(SQLException e) {
								request.setAttribute("msg","Errore nel salvataggio della trappola nel database!");
								response.sendError(500);
							}
						}
					} 	
					if(img) {
						RequestDispatcher dispatcher = request.getRequestDispatcher("/UploadImmagine");
						dispatcher.forward(request, response);
					} else {
						RequestDispatcher dispatcher = request.getRequestDispatcher("");
						dispatcher.forward(request, response);
					}
				} 	request.setAttribute("msg","Errore nella richiesta, parametro mancante!");
					response.sendError(400);
			}
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
