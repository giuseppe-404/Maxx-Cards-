package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;
import security.SecurityPassword;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;
import dao.UtenteDao;
import dao.UtenteDaoImpl;

/**
 * Servlet implementation class registraAccount
 */
@WebServlet("/registraAccount")
public class RegistraAccount extends HttpServlet {
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
    public RegistraAccount() {
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
		byte[] salt = SecurityPassword.generateSalt();
		String msg = "";
		try {
			HttpSession session = request.getSession();
			byte[] hashed = SecurityPassword.hashPassword(pwd,salt,10000,256);
			String password = SecurityPassword.bytesToHex(hashed);
			utente.setAdmin(false);
			utente.setSalt(salt);
			utente.setEmail(email);
			utente.setPwd(password);
			try {
				utenteDao.createUtente(utente);
				HttpSession sessione = request.getSession();
				sessione.setAttribute("utente",utente);
				String redirectUrl = (String) session.getAttribute("redirectedURL");
				request.setAttribute("msg", msg);
				if(redirectUrl != null) {
					session.removeAttribute("redirectedURL");
					response.sendRedirect(redirectUrl);
				}
				else {
					response.sendRedirect("/index");
				}
			}catch(SQLException e) {
				msg = "Email già in uso";
				request.setAttribute("msg",msg);
				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/account.jsp");
			}
		}catch(Exception e) {
			request.setAttribute("msg","Errore durante la creazione dell'account");
			response.sendError(500);
		}
		
	}
	
}