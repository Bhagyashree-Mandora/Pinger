package pinger.logger;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pinger.logger.DatabaseDetails;
import pinger.logger.MySQLRepository;
import pinger.logger.PingRepository;
import pinger.servlet.Ping;

public class MySQLRepositorySpecs {

	static final String dbDriverName = "com.mysql.jdbc.Driver";
	static final String urlToDatabase = "jdbc:mysql://127.0.0.1/testdb";
	static final DatabaseDetails databaseDetails = new DatabaseDetails(urlToDatabase, "root", "root", "testTable");
	static Connection connection;
	
	@BeforeClass
	public static void setupDatabase() throws ClassNotFoundException, SQLException {
		
		Class.forName(dbDriverName);
		connection = DriverManager.getConnection(databaseDetails.getUrlToDatabase(), databaseDetails.getUsername(), databaseDetails.getPassword());
		Statement statement = connection.createStatement();
		statement.executeUpdate("create database if not exists testdb");		
		statement.execute("use testdb;");
		statement.execute("drop table if exists testTable;");
		statement.executeUpdate("create table " + databaseDetails.getTableName() + "(dummyString varchar(20), dummyValue bigint);");		
	}
	
	@AfterClass
	public static void tearDownDatabase() throws SQLException{
		connection.close();
	}
	
	@Test
	public void itShouldWriteToMySQLDatabase() throws ClassNotFoundException, SQLException, IOException {
		// Given
		//setupDatabase();
		PingRepository mySQLRepositoryWriter = new MySQLRepository(databaseDetails);
		
		// When
		mySQLRepositoryWriter.write(new Ping("Fri Oct 12 11 00 00", 12345));

		// Then
		String expected = getExpected();

		Assert.assertEquals(expected, "Fri Oct 12 11 00 00:12345");

	}
	
	
	private String getExpected() {
		ResultSet resultSet;
		String returnString = "";
		try {
			Class.forName(dbDriverName);
			//Connection connection = DriverManager.getConnection(databaseDetails.getUrlToDatabase(), databaseDetails.getUsername(), databaseDetails.getPassword());
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from " + databaseDetails.getTableName());

			resultSet.last();

			returnString = (resultSet.getString(1) + ":" + resultSet.getString(2));

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	
	@Test
	public void itShouldReadFromMySQLDatabase() throws ClassNotFoundException, SQLException {
		//given
		//setupDatabase();
		PingRepository mySQLRepositoryReader = new MySQLRepository(databaseDetails);

		writeDummyValuesToTestDb();
		
		//when
		List<Ping> pings = mySQLRepositoryReader.readAll();
		
		//then
		assertThat(pings, hasItems(new Ping("dummyTimeOne",1),new Ping("dummyTimeTwo",2)));	
	}
	
	
	private void writeDummyValuesToTestDb() {
		try {
			Class.forName(dbDriverName);
			
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

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
