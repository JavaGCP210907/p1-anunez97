package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.revature.models.LoginDTO;
import com.revature.services.LoginService;

import io.javalin.http.Handler;

public class LoginController {

	LoginService ls = new LoginService();
	Logger log = LogManager.getLogger(LoginController.class);
	
	public Handler loginHandler = (ctx) -> {
		
		// get the body of the request and turn it into a string
		String body = ctx.body();
		
		Gson gson = new Gson();
		
		LoginDTO LDTO = gson.fromJson(body, LoginDTO.class);
		
		if(ls.login(LDTO.getUsername(), LDTO.getPassword())) {
			
			ctx.req.getSession();
			
			ctx.status(200);
			
			String gsonUser = gson.toJson(ls.getUserData());
			
			ctx.result(gsonUser);
			
			System.out.println("Logged in as " + LDTO.getUsername());
		}
		else {
			ctx.status(403);
		}
	};
	
	public Handler logoutHandler = (ctx) -> {
		System.out.println(ctx.body());
		log.warn("USER LOGGED OUT");
		
		ctx.req.getSession().invalidate();
	};
}
