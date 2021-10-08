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
		
		return DriverManager.getConnection(System.getenv("url_p1"), System.getenv("username"), System.getenv("password"));
	}
}
