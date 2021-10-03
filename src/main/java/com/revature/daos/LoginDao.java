package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.services.ReimbursementService;
import com.revature.utils.ConnectionUtil;

public class LoginDao implements LoginDaoInterface{

	@Override
	public boolean checkUsername(String username) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sqlUser = "select ers_username from ers_users where ers_username = ?";
			
			PreparedStatement ps = conn.prepareStatement(sqlUser);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			// check if the username is in the database
			if(!rs.next()) {
				return false;
			}
			else if(rs.getString("ers_username").equals(username)) {
				
				return true;
			}
			
		}
		catch(SQLException e) {
			System.out.println("Problem occurred wieh logining in");
			e.printStackTrace();
		}

		return false; // return an empty string
	}

	@Override
	public boolean checkPassword(String username, String password) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sqlUser = "select ers_password from ers_users where ers_username = ?";
			
			PreparedStatement ps = conn.prepareStatement(sqlUser);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {
				return false;
			}
			else if(rs.getString("ers_password").equals(password)) { // if the login will be a success
				
				String sql = "select ers_user_id from ers_users where ers_username = ?";
				
				ps = conn.prepareStatement(sql);
				
				ps.setString(1, username);
				
				rs = ps.executeQuery(); // get the user_id from the db
				
				rs.next(); // user exists so no need to check
				
				ReimbursementService.setCurrentUser(rs.getInt("ers_user_id")); // set the current user id in the reimbursement service
				
				return true;
			}
			
		}
		catch(SQLException e) {
			System.out.println("Problem occurred wieh logining in");
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public int getUserId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
