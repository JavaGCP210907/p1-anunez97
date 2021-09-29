package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.controllers.ReimbursementController;
import com.revature.utils.ConnectionUtil;

import io.javalin.Javalin;

public class Launcher {

	public static void main(String[] args) {

		// HTTP Handlers
		ReimbursementController rc = new ReimbursementController();
		
		// attempt to connect to the database
		try(Connection conn = ConnectionUtil.getConnection()) {
			System.out.println("Employee Reimbursement System connection successful!");
		}
		catch(SQLException e) {
			System.out.println("Employee Reimbursement System connection failed!");
			e.printStackTrace();
		}
		
		// create a Javalin object and start the server
		Javalin app = Javalin.create(
				config -> {
					config.enableCorsForAllOrigins(); // allows the server to process JS requests from anywhere
				}
				).start(8090);
		
		// --------------------------- Requests -----------------------------------
		// Login (Employee/Manager)
		// app.post("login", lc.loginHandler);
		
		// Submit Request (Employee)
		// app.put("employee/submit", rc.submitRequestHandler);
		
		// View Pending Requests (Employee)
		app.get("/employee/pending", rc.e_viewPendingRequestsHandler);
		
		// View Past Tickets (Employee)
		app.get("employee/history", rc.e_viewTicketHistoryHandler);
		
		// View Pending Requests (Manager)
		app.get("manager/pending", rc.m_viewPendingRequestsHandler);
		
		// View History (Manager)
		app.get("manager/history", rc.m_viewReimbursementHistoryHandler);
		
		// Approve Requests (Manager)
		// app.patch("manager/requests/pending", rc.approveRequestHandler);
		
		// Deny Requests (Manager)
		// app.patch("manager/requests/pending", rc.denyRequestHandler);

	}

}
