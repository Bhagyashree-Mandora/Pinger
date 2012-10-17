package pinger.logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pinger.servlet.Ping;

public class MySQLRepository implements PingRepository {

	private final String sqlSelectQuery;
	private final Connection connection;
	private final String sqlInsertQuery;

	public MySQLRepository(DatabaseDetails databaseDetails) throws SQLException, ClassNotFoundException {
		String dbDriverName = "com.mysql.jdbc.Driver";
		Class.forName(dbDriverName);
		this.connection = DriverManager.getConnection(
				databaseDetails.getUrlToDatabase(),
				databaseDetails.getUsername(), databaseDetails.getPassword());
		sqlInsertQuery = "insert into " + databaseDetails.getTableName()
				+ " values (?, ?)";
		sqlSelectQuery = "select * from " + databaseDetails.getTableName();
	}

	@Override
	public void write(Ping ping) {
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertQuery);
			preparedStatement.setString(1, ping.getArrivedAt());
			preparedStatement.setString(2, String.valueOf(ping.getProcessingTime()));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Ping> readAll() {
		ResultSet resultSet;
		List<Ping> pings =new ArrayList<>();
		try {
			
			Statement statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sqlSelectQuery);
			while(resultSet.next()){
				pings.add(new Ping(resultSet.getString(1), Long.parseLong(resultSet.getString(2))));
			};

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pings;
	}		
	
	@Override
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
