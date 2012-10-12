package pinger.logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pinger.servlet.Ping;

public class MySQLRepositoryReader implements PingRepositoryReader {

	private final DatabaseDetails databaseDetails;
	private final String dbDriverName="com.mysql.jdbc.Driver"; 
	
	public MySQLRepositoryReader(DatabaseDetails databaseDetails) {
		this.databaseDetails = databaseDetails;
	}

	@Override
	public List<Ping> readAll() {
		ResultSet resultSet;
		List<Ping> pings =new ArrayList<>();
		try {
			
			Class.forName(this.dbDriverName);
			Connection connection = DriverManager.getConnection(databaseDetails.getUrlToDatabase(), databaseDetails.getUsername(), databaseDetails.getPassword());
			Statement statement = connection.createStatement();
			
			resultSet = statement.executeQuery("select * from " + databaseDetails.getTableName());
			while(resultSet.next()){
				pings.add(new Ping(resultSet.getString(1), Long.parseLong(resultSet.getString(2))));
			};

			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pings;
	}		
}