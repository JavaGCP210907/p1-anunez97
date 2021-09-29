package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException{
		
		try {
			// search for the postgres driver
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException e) {
			System.out.println("Failed to get driver!");
			e.printStackTrace();
		}
		
		// login credentials for the database
		String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=p1_ers";
		String username = "postgres";
		String password = "t4coss1212";
		
		return DriverManager.getConnection(url, username, password);
	}
}
