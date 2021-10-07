package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.utils.ConnectionUtil;

public class LoginDao implements LoginDaoInterface{

	@Override
	public String getUserName(String username) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sqlUser = "select ers_username from ers_users where ers_username = ?";
			
			PreparedStatement ps = conn.prepareStatement(sqlUser);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			// check if the username is in the database
			if(!rs.next()) {
				return "";
			}
			
			return rs.getString("ers_username");
			
		}
		catch(SQLException e) {
			System.out.println("Problem occurred wieh logining in");
			e.printStackTrace();
		}

		return ""; // return an empty string
	}

	@Override
	public String getPassword(String username) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sqlUser = "select ers_password from ers_users where ers_username = ?";
			
			PreparedStatement ps = conn.prepareStatement(sqlUser);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {
				return "";
			}
			
			return rs.getString("ers_password");
			
		}
		catch(SQLException e) {
			System.out.println("Problem occurred with logging in");
			e.printStackTrace();
		}

		return "";
	}

	@Override
	public String getFirstName(String username) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select user_first_name from ers_users where ers_username = ?";
		
			PreparedStatement ps = conn.prepareStatement(sql);
		
			ps.setString(1, username);
		
			ResultSet rs = ps.executeQuery(); // get the user_first_name from the db
		
			rs.next(); // user exists so no need to check
		
			return rs.getString("user_first_name");
		}
		catch(SQLException e) {
			System.out.println("Problem occurred with logging in");
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public String getLastName(String username) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select user_last_name from ers_users where ers_username = ?";
		
			PreparedStatement ps = conn.prepareStatement(sql);
		
			ps.setString(1, username);
		
			ResultSet rs = ps.executeQuery(); // get the user_first_name from the db
		
			rs.next(); // user exists so no need to check
		
			return rs.getString("user_last_name");
		}
		catch(SQLException e) {
			System.out.println("Problem occurred with logging in");
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public String getUserRole(String username) {
		
		// this will only get called when the username/password given are confirmed
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select user_role from ers_user_roles where ers_user_role_id = "
					+ "(select user_role_id_fk from ers_users where ers_username = ?)";
		
			PreparedStatement ps = conn.prepareStatement(sql);
		
			ps.setString(1, username);
		
			ResultSet rs = ps.executeQuery(); // get the user_id from the db
		
			rs.next(); // user exists so no need to check
			
			return rs.getString("user_role");

		}
		catch(SQLException e) {
			System.out.println("Problem occurred with logging in");
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public int getUserId(String username) {
		// this will only get called when the username/password given are confirmed
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select ers_user_id from ers_users where ers_username = ?";
				
			PreparedStatement ps = conn.prepareStatement(sql);
				
			ps.setString(1, username);
				
			ResultSet rs = ps.executeQuery(); // get the user_id from the db
				
			rs.next(); // user exists so no need to check
			
			return rs.getInt("ers_user_id");

		}
		catch(SQLException e) {
			System.out.println("Problem occurred with logging in");
			e.printStackTrace();
		}
				
		return 0;
	}
}
