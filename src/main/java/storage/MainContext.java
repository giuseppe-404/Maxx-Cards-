package storage;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class MainContext implements ServletContextListener{

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		DataSource ds = null;
		try {
			Context initCntxt = new InitialContext();
			Context envCntxt = (Context) initCntxt.lookup("java:comp/env");
			ds = (DataSource) envCntxt.lookup("jdbc/maxxcardsdb");
		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
		context.setAttribute("DataSource", ds);
	}
	
	public void contextDestroyed(ServletContextEvent event) {
	}
}
