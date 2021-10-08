package com.revature.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.LoginDao;
import com.revature.models.UserData;

public class LoginService {

	LoginDao lDao = new LoginDao();
	private UserData ud = new UserData();
	Logger log = LogManager.getLogger(LoginService.class);
	
	public boolean login(String username, String password) {
		
		
		if(checkLoginCredentials(username, password)) {
			
			if(lDao.getUserName(username).equals(username) && lDao.getPassword(username).equals(password)) {
			
				ud.setUserFirstName(lDao.getFirstName(username));
				ud.setUserLastName(lDao.getLastName(username));
				ud.setUserRole(lDao.getUserRole(username));
			
				ReimbursementService.setCurrentUser(lDao.getUserId(username));
				log.info("SUCCESSFUL LOGIN");
				return true;
			}
		}
		
		log.warn("UNSUCCESSFUL LOGIN ATTEMPT");
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
