package pinger.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pinger.logger.PingRepositoryWriter;

import net.sf.json.JSONObject;

public class PingServer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	private PingRepositoryWriter repositoryWriter;
	
	@Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        repositoryWriter = (PingRepositoryWriter) servletContext.getAttribute("repositoryWriter");
    }
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		long time = System.currentTimeMillis();
		String arrivedAt = new Date().toString().replaceAll(":", " ");
		JSONObject jSONResponse = new JSONObject();
		
		try {
			int simulatedSleepTime = (int) (10000* Math.random());
			Thread.sleep(simulatedSleepTime);
			
			long processingTime = System.currentTimeMillis()-time;

			repositoryWriter.write(new Ping(arrivedAt, processingTime));
			
			jSONResponse.put("processingTime", processingTime);
			response.setContentType("application/json");
			response.getWriter().write(jSONResponse.toString());

		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		} 
	}
}