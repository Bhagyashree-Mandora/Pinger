package pinger.statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import pinger.logger.DatabaseDetails;
import pinger.logger.FileRepositoryWriter;
import pinger.logger.MySQLRepositoryReader;
import pinger.logger.PingRepositoryReader;
import pinger.servlet.Ping;

public class PingTrend {

	private final PingRepositoryReader pingRepositoryReader;
	private final FileRepositoryWriter pingRepositoryWriter;

	public PingTrend(PingRepositoryReader pingRepositoryReader,
			FileRepositoryWriter fileRepositoryWriter) {
		this.pingRepositoryReader = pingRepositoryReader;
		this.pingRepositoryWriter = fileRepositoryWriter;
	}

	public void retrieve() {
		List<Ping> pings = pingRepositoryReader.readAll();

		try {
			for (Ping ping : pings) {

				pingRepositoryWriter.write(ping);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		if(args[0].equals("-f")){
		FileWriter fileWriter = new FileWriter(new File(args[1]));

		DatabaseDetails databaseDetails = new DatabaseDetails(args[2], args[3],
				args[4], args[5]);
		PingTrend pingTrend = new PingTrend(new MySQLRepositoryReader(
				databaseDetails), new FileRepositoryWriter(new BufferedWriter(
				fileWriter)));
		pingTrend.retrieve();
		}
	}

}
