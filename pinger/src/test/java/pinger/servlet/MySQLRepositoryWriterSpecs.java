package pinger.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.Assert;

import org.junit.Test;

import pinger.logger.DatabaseDetails;
import pinger.logger.MySQLRepositoryWriter;
import pinger.logger.PingRepositoryWriter;

public class MySQLRepositoryWriterSpecs {

	
	private void setupDatabase(DatabaseDetails databaseDetails,String dbDriverName) throws ClassNotFoundException, SQLException {
		Class.forName(dbDriverName);
		
		Connection connection = DriverManager.getConnection(databaseDetails.getUrlToDatabase(), databaseDetails.getUsername(), databaseDetails.getPassword());
		Statement statement = connection.createStatement();
		statement.executeUpdate("create database if not exists testdb;");		
		statement.executeUpdate("create table if not exists testTable(dummyString varchar(20), dummyValue bigint);");		
	}
	

	@Test
	public void itWritesToMySQLDatabase() throws ClassNotFoundException, SQLException, IOException {
		// Given
		String urlToDatabase = "jdbc:mysql://127.0.0.1/testdb";
		String dbDriverName = "com.mysql.jdbc.Driver";
		DatabaseDetails databaseDetails = new DatabaseDetails(urlToDatabase, "root", "root", "testTable");
		
		setupDatabase(databaseDetails, dbDriverName);
		
		PingRepositoryWriter mySQLRepositoryWriter = new MySQLRepositoryWriter(databaseDetails);

		// When
		mySQLRepositoryWriter.write(new Ping("Fri Oct 12 11 00 00", 12345));

		// Then
		String expected = getExpected(databaseDetails, dbDriverName);

		Assert.assertEquals(expected, "Fri Oct 12 11 00 00:12345");

	}
	
	
	private String getExpected(DatabaseDetails databaseDetails, String dbDriverName) {
		ResultSet resultSet;
		String returnString = "";
		try {
			Class.forName(dbDriverName);

			Connection connection = DriverManager.getConnection(databaseDetails.getUrlToDatabase(), databaseDetails.getUsername(), databaseDetails.getPassword());
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from " + databaseDetails.getTableName());

			resultSet.last();

			returnString = (resultSet.getString(1) + ":" + resultSet.getString(2));

			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return returnString;
	}
}
