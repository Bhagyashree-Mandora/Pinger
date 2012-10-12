package pinger.logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import pinger.servlet.Ping;

public class MySQLRepositoryWriter implements PingRepositoryWriter {
	private static final String dbDriverName = "com.mysql.jdbc.Driver";
	private final DatabaseDetails databaseDetails;

	public MySQLRepositoryWriter(DatabaseDetails databaseDetails) {
		this.databaseDetails = databaseDetails;

	}

	@Override
	public void write(Ping ping) {
		try {
			Class.forName(dbDriverName);

			Connection connection = DriverManager.getConnection(databaseDetails.getUrlToDatabase(), databaseDetails.getUsername(), databaseDetails.getPassword());
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into "+ databaseDetails.getTableName() +" values (?, ?)");
			preparedStatement.setString(1, ping.getArrivedAt());
			preparedStatement.setString(2,
					String.valueOf(ping.getProcessingTime()));
			preparedStatement.executeUpdate();

			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}