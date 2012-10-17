package pinger.logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.BufferedWriter;
import java.io.IOException;

import org.junit.Test;

import pinger.logger.FileRepository;
import pinger.servlet.Ping;

public class FileRepositorySpecs {

	@Test
	public void itShouldWriteAPingToFile() throws IOException {
		//given
		BufferedWriter mockBufferedWriter= mock(BufferedWriter.class); 
		FileRepository fileRepositoryWriter = new FileRepository(mockBufferedWriter);
		
		//when
		fileRepositoryWriter.write(new Ping("a",1));
		
		//then
		verify(mockBufferedWriter).write("a:1,");
	}

}
