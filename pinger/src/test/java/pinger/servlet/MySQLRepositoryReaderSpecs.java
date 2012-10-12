package pinger.servlet;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import pinger.logger.DatabaseDetails;
import pinger.logger.MySQLRepositoryReader;
import pinger.logger.PingRepositoryReader;

public class MySQLRepositoryReaderSpecs {

	@Test
	public void itShouldReadFromDatabase() {
		//given
		String dbDriverName = "com.mysql.jdbc.Driver";
		String urlToDatabase = "jdbc:mysql://127.0.0.1/testdb";

		DatabaseDetails databaseDetails = new DatabaseDetails(urlToDatabase, "root", "root", "testTable");
		PingRepositoryReader mySQLRepositoryReader = new MySQLRepositoryReader(databaseDetails);

		writeDummyValuesToTestDb(databaseDetails, dbDriverName);
		
		//when
		List<Ping> pings = mySQLRepositoryReader.readAll();
		
		//then
		assertThat(pings, hasItems(new Ping("dummyTimeOne",1),new Ping("dummyTimeTwo",2)));	
	}

	
	private void writeDummyValuesToTestDb(DatabaseDetails databaseDetails, String dbDriverName) {
		try {
			Class.forName(dbDriverName);
			
			Connection connection = DriverManager.getConnection(databaseDetails.getUrlToDatabase(), databaseDetails.getUsername(), databaseDetails.getPassword());
			java.sql.Statement statement = connection.createStatement();
			statement.executeUpdate("create database if not exists testdb;");
			
			statement.execute("use testdb;");
			statement.execute("drop table if exists testTable;");
			
			statement.execute("create table testTable(dummyString varchar(20), dummyValue bigint)");
			
			PreparedStatement preparedStatementOne = connection
					.prepareStatement("insert into testTable values (?, ?)");
			preparedStatementOne.setString(1, "dummyTimeOne");
			preparedStatementOne.setString(2,"1");
			preparedStatementOne.executeUpdate();

			PreparedStatement preparedStatementTwo = connection
					.prepareStatement("insert into testTable values (?, ?)");
			preparedStatementTwo.setString(1, "dummyTimeTwo");
			preparedStatementTwo.setString(2,"2");
			preparedStatementTwo.executeUpdate();

			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}