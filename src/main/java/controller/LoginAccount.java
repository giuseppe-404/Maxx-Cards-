package controller;

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
 * Servlet implementation class LoginAccount
 */
@WebServlet("/loginAccount")
public class LoginAccount extends HttpServlet {
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
    public LoginAccount() {
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
		String msg = "";
		String pwd = request.getParameter("pwd");
		HttpSession session = request.getSession();
		try {
			UtenteBean utente = utenteDao.retrieveByEmail(email);
			String storedPwd = utente.getPwd();
			byte[] salt = utente.getSalt();
			try{
				if(SecurityPassword.validatePassword(pwd, salt, storedPwd,10000 , 256)) {
					System.out.println("Successo");
					
					session.setAttribute("utente",utente);
					String redirectUrl = (String) session.getAttribute("redirectedURL");
					session.setAttribute("msg", msg);
					if(redirectUrl != null) {
						session.removeAttribute("redirectedURL");
						response.sendRedirect(redirectUrl);
					}
					else response.sendRedirect("/index");
				}
				else {
					msg = "Email o password errata!";
					session.setAttribute("msg",msg);
				}
			}catch(Exception exc) {
				request.setAttribute("","Errore nella validazione delle informazioni!");
				response.sendError(500);
			}
		}catch(SQLException e) {
			msg = "Email o password errata!";
			session.setAttribute("msg",msg);
		}
		
	}

}
