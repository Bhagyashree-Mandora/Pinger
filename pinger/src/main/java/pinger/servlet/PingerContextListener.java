package pinger.servlet;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pinger.logger.DatabaseDetails;
import pinger.logger.MySQLRepository;

public class PingerContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
		MySQLRepository repositoryWriter;
		
		ServletContext servletContext = servletContextEvent.getServletContext();
		String urlToDatabase = (String) servletContext.getInitParameter("urlToDatabase");
		String username = (String) servletContext.getInitParameter("username");
		String password = (String) servletContext.getInitParameter("password");
		String tableName = (String) servletContext.getInitParameter("tableName");
		DatabaseDetails mySQLDetails = new DatabaseDetails(urlToDatabase, username, password, tableName);
		
		try {
			repositoryWriter = new MySQLRepository(mySQLDetails);
			servletContext.setAttribute("repositoryWriter", repositoryWriter);    
		} catch (ClassNotFoundException| SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("Context Destroyed");
	}
}
