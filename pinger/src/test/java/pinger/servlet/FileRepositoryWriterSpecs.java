package pinger.servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.BufferedWriter;
import java.io.IOException;

import org.junit.Test;

import pinger.logger.FileRepositoryWriter;

public class FileRepositoryWriterSpecs {

	@Test
	public void itShouldWriteAPingToFile() throws IOException {
		//given
		BufferedWriter mockBufferedWriter= mock(BufferedWriter.class); 
		FileRepositoryWriter fileRepositoryWriter = new FileRepositoryWriter(mockBufferedWriter);
		
		//when
		fileRepositoryWriter.write(new Ping("a",1));
		
		//then
		verify(mockBufferedWriter).write("a:1,");
	}

}
