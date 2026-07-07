package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.ProdottoDaoImpl;
import dao.UtenteDao;
import dao.UtenteDaoImpl;

/**
 * Servlet implementation class registraAccount
 */
@WebServlet("/registraAccount")
public class registraAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UtenteDao utenteDao = null;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        utenteDao = new UtenteDaoImpl(ds);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registraAccount() {
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
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		UtenteBean utente = new UtenteBean();
		byte[] b = new byte[128];
		utente.setAdmin(false);
		utente.setDarkTheme(false);
		utente.setSalt(b);
		utente.setEmail(email);
		utente.setPwd(pwd);
		try {
			utenteDao.createUtente(utente);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}