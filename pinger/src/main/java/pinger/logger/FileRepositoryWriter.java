package pinger.logger;

import java.io.BufferedWriter;
import java.io.IOException;

import pinger.servlet.Ping;


public class FileRepositoryWriter implements PingRepositoryWriter {

	private final BufferedWriter bufferedWriter;
	
	public FileRepositoryWriter(BufferedWriter bufferedWriter) throws IOException{
		this.bufferedWriter = bufferedWriter;
	}
	
	@Override
	public void write(Ping ping) throws IOException {
		bufferedWriter.write(ping.getArrivedAt()+":"+ping.getProcessingTime()+",");
		bufferedWriter.flush();
	}
}
