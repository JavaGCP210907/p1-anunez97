package com.revature.services;

import com.revature.daos.LoginDao;

public class LoginService {

	LoginDao lDao = new LoginDao();
	
	public boolean login(String username, String password) {
		
		return lDao.checkUsername(username) && lDao.checkPassword(username, password);
	}
}
