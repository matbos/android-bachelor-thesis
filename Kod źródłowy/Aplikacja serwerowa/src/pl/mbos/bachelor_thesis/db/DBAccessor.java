package pl.mbos.bachelor_thesis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBAccessor {
	static String serverAddress = "localhost";
	static String portNumber = "3306";
	static String userName = "matt";
	static String password = "faster";
	
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		conn = DriverManager.getConnection("jdbc:mysql://" + serverAddress + ":" + portNumber + "/", connectionProps);		
		System.out.println("Connected to database");
		return conn;
	}
	
}
