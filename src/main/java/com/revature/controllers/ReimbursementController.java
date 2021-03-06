package com.revature.controllers;

import java.util.List;

import com.google.gson.Gson;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementUpdateDTO;
import com.revature.services.ReimbursementService;

import io.javalin.http.Handler;

public class ReimbursementController {
	
	ReimbursementService rs = new ReimbursementService();
	
	public Handler m_viewReimbursementHistoryHandler = (ctx) -> {
		
		// check if there is a session
		if(ctx.req.getSession(false) != null) {
			
			// get the list from the service class
			List<Reimbursement> reimbs = rs.m_getReimbursementHistory();
			
			// create a Gson object
			Gson gson = new Gson();
			
			// convert the Java (array) object into JSON
			String JSONreimbs = gson.toJson(reimbs);
			
			//
			ctx.result(JSONreimbs);
			
			ctx.status(200); // success
			
		}
		else {
			ctx.status(403);
		}
	};
	
	public Handler m_viewPendingRequestsHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			List<Reimbursement> reimbs = rs.m_getPendingReimbursements();
			
			Gson gson = new Gson();
			
			String JSONreimbs = gson.toJson(reimbs);
			
			ctx.result(JSONreimbs);
			
			ctx.status(200);
		}
		else {
			ctx.status(403);
		}
	};
	
	public Handler m_approveRequestHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			String body = ctx.body();
			
			Gson gson = new Gson();
			
			ReimbursementUpdateDTO rDto = gson.fromJson(body, ReimbursementUpdateDTO.class);
			
			rs.m_approveRequest(rDto);
			
			System.out.println("Request approved by manager!");
			
			ctx.status(200);
		}
		else {
			ctx.status(403);
		}
	};
	
	public Handler m_denyRequestHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			String body = ctx.body();
			
			Gson gson = new Gson();
			
			ReimbursementUpdateDTO rDto = gson.fromJson(body, ReimbursementUpdateDTO.class);
			
			rs.m_denyRequest(rDto);
			
			System.out.println("Request denied by manager!");
			
			ctx.status(200);
		}
		else {
			ctx.status(403);
		}
	};
	
	public Handler e_viewPendingRequestsHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			List<Reimbursement> reimbs = rs.e_getPendingReimbursements();
			
			Gson gson = new Gson();
			
			String JSONreimbs = gson.toJson(reimbs);
			
			ctx.result(JSONreimbs);
			
			ctx.status(200);
		}
		else {
			ctx.status(403);
		}
	};
	
	public Handler e_viewTicketHistoryHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			List<Reimbursement> reimbs = rs.e_getTicketHistory();
			
			Gson gson = new Gson();
			
			String JSONreimbs = gson.toJson(reimbs);
			
			ctx.result(JSONreimbs);
			
			ctx.status(200);
		}
		else {
			ctx.status(403);
		}
	};
	
	public Handler e_submitRequestHandler = (ctx) -> {
		
		if(ctx.req.getSession(false) != null) {
			
			String body = ctx.body();
			
			Gson gson = new Gson();
			
			ReimbursementDTO rDto = gson.fromJson(body, ReimbursementDTO.class);
			
			rs.e_submitRequest(rDto);
			
			System.out.println("Request submitted by employee!");
			
			ctx.status(200);
		}
		else {
			ctx.status(403);
		}
	};
}
