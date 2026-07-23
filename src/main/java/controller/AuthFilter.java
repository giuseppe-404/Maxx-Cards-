package controller;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
		String msg = "";
		String path = request.getServletPath();
		if(!path.startsWith("/admin/") && !path.startsWith("/common/")) {
			chain.doFilter(request, response);
			return;
		}
		HttpSession session = request.getSession(false);
		String role = null;
		if(session != null) {
			UtenteBean utente = (UtenteBean) session.getAttribute("utente");
			if(utente.isAdmin()) {
				role = "admin";
			} else {
				role = "common";
			}
		}
		boolean autorizzato = false; 
		if(role != null) {
			if(path.startsWith("/admin/")) {
				autorizzato = role.equals("admin");
			} else if(path.startsWith("/common/")) {
				autorizzato = role.equals("admin") || role.equals("users");
			}
		}
		if(autorizzato) {
			chain.doFilter(request, response);
		} else {
			String requestedUrl = request.getRequestURI();
			session.setAttribute("redirectedUrl", requestedUrl);
			msg = "";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/WEB-INF/views/account.jsp");
		}
	}
}
