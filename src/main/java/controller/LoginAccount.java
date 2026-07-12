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
@WebServlet("/LoginAccount")
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
		String pwd = request.getParameter("pwd");
		try {
			UtenteBean utente = utenteDao.retrieveByEmail(email);
			System.out.println(utente.getEmail() + pwd);
			String storedPwd = utente.getPwd();
			byte[] salt = utente.getSalt();
			try{
				if(SecurityPassword.validatePassword(pwd, salt, storedPwd,10000 , 256)) {
					System.out.println("Successo");
					HttpSession session = request.getSession();
					session.setAttribute("utente",utente);
				}
				else System.out.println("Fallimento");
			}catch(Exception exc) {
				exc.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
