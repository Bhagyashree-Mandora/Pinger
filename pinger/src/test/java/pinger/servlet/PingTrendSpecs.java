package pinger.servlet;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import pinger.logger.FileRepositoryWriter;
import pinger.logger.PingRepositoryReader;
import pinger.statistics.PingTrend;

public class PingTrendSpecs {

	@Test
	public void itReadsFromDatabase() throws IOException {
		//Given
		PingRepositoryReader mockPingRepositoryReader = mock(PingRepositoryReader.class);
		FileRepositoryWriter mockFileRepositoryWriter = mock(FileRepositoryWriter.class);
		PingTrend pingTrend = new PingTrend(mockPingRepositoryReader, mockFileRepositoryWriter);

		
		//When
		pingTrend.retrieve();
		
		//Then
		verify(mockPingRepositoryReader).readAll();
	}
	
	@Test
	public void itWritesToAnOutputStream() throws IOException {
		//Given
		PingRepositoryReader mockPingRepositoryReader = mock(PingRepositoryReader.class);
		FileRepositoryWriter mockFileRepositoryWriter = mock(FileRepositoryWriter.class);
		PingTrend pingTrend = new PingTrend(mockPingRepositoryReader,mockFileRepositoryWriter);

		given(mockPingRepositoryReader.readAll()).willReturn(Arrays.asList(new Ping("a",1), new Ping("b",2)));

		//When
		pingTrend.retrieve();
		
		//Then
		verify(mockFileRepositoryWriter, times(2)).write(any(Ping.class));
	}
}