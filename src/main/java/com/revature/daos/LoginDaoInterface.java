package com.revature.daos;

public interface LoginDaoInterface {
	
	public String getUserName(String username);                  // get the username back if it exists in the db
	
	public String getPassword(String username); // get the password for the username
	
	public String getFirstName(String username);
	
	public String getLastName(String username);
	
	public String getUserRole(String username);
	
	public int getUserId(String username);
}
