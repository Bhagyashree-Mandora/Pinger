package pinger.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pinger.logger.DatabaseDetails;
import pinger.logger.MySQLRepositoryWriter;

public class PingerContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
		MySQLRepositoryWriter repositoryWriter;
		
		ServletContext servletContext = servletContextEvent.getServletContext();
		String urlToDatabase = (String) servletContext.getInitParameter("urlToDatabase");
		String username = (String) servletContext.getInitParameter("username");
		String password = (String) servletContext.getInitParameter("password");
		String tableName = (String) servletContext.getInitParameter("tableName");
		DatabaseDetails mySQLDetails = new DatabaseDetails(urlToDatabase, username, password, tableName);
		
		repositoryWriter = new MySQLRepositoryWriter(mySQLDetails);
		servletContext.setAttribute("repositoryWriter", repositoryWriter);    
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("Context Destroyed");
	}
}
