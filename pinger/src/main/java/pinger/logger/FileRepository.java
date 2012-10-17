package pinger.logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import pinger.servlet.Ping;


public class FileRepository implements PingRepository {

	private final BufferedWriter bufferedWriter;
	
	public FileRepository(BufferedWriter bufferedWriter) throws IOException{
		this.bufferedWriter = bufferedWriter;
	}
	
	@Override
	public void write(Ping ping) {
		try {
			bufferedWriter.write(ping.getArrivedAt()+":"+ping.getProcessingTime()+",");
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeConnection() {
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Ping> readAll() {
		return null;
	}
}
