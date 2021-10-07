package com.revature.models;

public class UserData {
	private String userRole;
	private String userFirstName;
	private String userLastName;

	public UserData() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public UserData(String userRole, String userFirstName, String userLastName) {
		super();
		this.userRole = userRole;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}



	public String getUserFirstName() {
		return userFirstName;
	}



	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}



	public String getUserLastName() {
		return userLastName;
	}



	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	
}
