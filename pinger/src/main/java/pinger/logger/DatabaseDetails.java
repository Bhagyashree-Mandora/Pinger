package pinger.logger;

public class DatabaseDetails {

	private final String urlToDatabase;
	private final String username;
	private final String password;
	private final String tableName;

	public DatabaseDetails(String urlToDatabase, String username, String password, String tableName){
		this.urlToDatabase = urlToDatabase;
		this.username = username;
		this.password = password;
		this.tableName = tableName;
	}
	
	public String getUrlToDatabase() {
		return urlToDatabase;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getTableName() {
		return tableName;
	}

}
