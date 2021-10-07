package com.revature.services;

import com.revature.daos.LoginDao;
import com.revature.models.UserData;

public class LoginService {

	LoginDao lDao = new LoginDao();
	private UserData ud = new UserData();
	
	public boolean login(String username, String password) {
		
		if(checkLoginCredentials(username, password)) {
			
			if(lDao.getUserName(username).equals(username) && lDao.getPassword(username).equals(password)) {
			
				ud.setUserFirstName(lDao.getFirstName(username));
				ud.setUserLastName(lDao.getLastName(username));
				ud.setUserRole(lDao.getUserRole(username));
			
				ReimbursementService.setCurrentUser(lDao.getUserId(username));
			
				return true;
			}
		}
		
		return false;
	}
	
	public UserData getUserData() {
		return ud;
	}
	
	// basic checks - no empty strings, etc
	private boolean checkLoginCredentials(String username, String password) {
		
		return username.length() > 0 && password.length() > 0;
	}
}
