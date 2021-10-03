package com.revature.daos;

public interface LoginDaoInterface {
	
	public boolean checkUsername(String username);                  // get the username back if it exists in the db
	
	public boolean checkPassword(String username, String password); // get the password for the username
	
	public int getUserId();                                         // will return the user's id, will only be called with successful logins
}
