package pinger.servlet;


import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import pinger.logger.PingRepository;

public class ServletSpecs {

	@Test
	public void itShouldGiveProcessingTime() throws IOException, ServletException {

		//given
		HttpServletRequest ignoreRequest=mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		PingRepository ignorePingRepositoryWriter =mock(PingRepository.class);
		PrintWriter mockWriter = mock(PrintWriter.class);

		ServletConfig mockConfig = mock(ServletConfig.class);
        ServletContext mockServletContext = mock(ServletContext.class);

		given(response.getWriter()).willReturn(mockWriter);
        given(mockConfig.getServletContext()).willReturn(mockServletContext);
        given(mockServletContext.getAttribute("repositoryWriter")).willReturn(ignorePingRepositoryWriter);
		
		PingServer pingServer = new PingServer();
		pingServer.init(mockConfig);
		
		//when
		pingServer.doGet(ignoreRequest, response);
		
		//then
		verify(response).setContentType("application/json");
		verify(mockWriter).write("{processingTime:"+anyInt()+"}");
	}
	
	@Test
	public void itShouldOutputToARepository() throws IOException, ServletException {
		//Given
		HttpServletRequest ignoreRequest=mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		PrintWriter ignorePrintWriter =mock(PrintWriter.class);
		
		ServletConfig mockConfig = mock(ServletConfig.class);
        ServletContext mockServletContext = mock(ServletContext.class);
        PingRepository mockPingRepositoryWriter = mock(PingRepository.class);

        given(response.getWriter()).willReturn(ignorePrintWriter);
        given(mockConfig.getServletContext()).willReturn(mockServletContext);
        given(mockServletContext.getAttribute("repositoryWriter")).willReturn(mockPingRepositoryWriter);
        
        
		PingServer pingServer = new PingServer();
		pingServer.init(mockConfig);
		
		given(response.getWriter()).willReturn(ignorePrintWriter);
		
        when(mockConfig.getServletContext()).thenReturn(mockServletContext);
        when(mockServletContext.getAttribute("repositoryWriter")).thenReturn(mockPingRepositoryWriter);
        pingServer.init(mockConfig);

				
		//When
		pingServer.doGet(ignoreRequest, response);

		//Then
		verify(mockPingRepositoryWriter).write(any(Ping.class));
	}

}