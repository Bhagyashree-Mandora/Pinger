package pinger.statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import pinger.logger.DatabaseDetails;
import pinger.logger.FileRepository;
import pinger.logger.MySQLRepository;
import pinger.logger.PingRepository;
import pinger.servlet.Ping;

public class PingTrend {

	private final PingRepository pingRepository;
	private final FileRepository pingFileRepository;

	public PingTrend(PingRepository pingRepository,
			FileRepository fileRepository) {
		this.pingRepository = pingRepository;
		this.pingFileRepository = fileRepository;
	}

	public void retrieve() {
		List<Ping> pings = pingRepository.readAll();

		for (Ping ping : pings) {

			pingFileRepository.write(ping);
		}
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, SQLException {
		if (args[0].equals("-f")) {
			FileWriter fileWriter = new FileWriter(new File(args[1]));

			DatabaseDetails databaseDetails = new DatabaseDetails(args[2],args[3], args[4], args[5]);
			PingTrend pingTrend = new PingTrend(new MySQLRepository(databaseDetails), 
					new FileRepository(new BufferedWriter(fileWriter)));
			pingTrend.retrieve();
		}
	}

}
