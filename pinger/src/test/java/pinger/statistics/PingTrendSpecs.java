package pinger.statistics;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import pinger.logger.FileRepository;
import pinger.logger.PingRepository;
import pinger.servlet.Ping;
import pinger.statistics.PingTrend;

public class PingTrendSpecs {

	@Test
	public void itShouldReadFromDatabase() throws IOException {
		//Given
		PingRepository mockPingRepository = mock(PingRepository.class);
		FileRepository mockFileRepository= mock(FileRepository.class);
		PingTrend pingTrend = new PingTrend(mockPingRepository, mockFileRepository);

		
		//When
		pingTrend.retrieve();
		
		//Then
		verify(mockPingRepository).readAll();
	}
	
	@Test
	public void itShouldWriteToAnOutputStream() throws IOException {
		//Given
		PingRepository mockPingRepository = mock(PingRepository.class);
		FileRepository mockFileRepository = mock(FileRepository.class);
		PingTrend pingTrend = new PingTrend(mockPingRepository,mockFileRepository);

		given(mockPingRepository.readAll()).willReturn(Arrays.asList(new Ping("a",1), new Ping("b",2)));

		//When
		pingTrend.retrieve();
		
		//Then
		verify(mockFileRepository, times(2)).write(any(Ping.class));
	}
}