package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.controllers.LoginController;
import com.revature.controllers.ReimbursementController;
import com.revature.utils.ConnectionUtil;

import io.javalin.Javalin;

public class Launcher {

	public static void main(String[] args) {

		// HTTP Handlers
		ReimbursementController rc = new ReimbursementController();
		LoginController lc = new LoginController();
		
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
		app.post("login", lc.loginHandler);
		
		// Logout
		app.post("logout", lc.logoutHandler);
		
		// TODO Submit Request (Employee)
		app.post("employee/submit", rc.e_submitRequestHandler);
		
		// View Pending Requests (Employee)
		app.get("employee/pending", rc.e_viewPendingRequestsHandler);
		
		// View Past Tickets (Employee)
		app.get("employee/history", rc.e_viewTicketHistoryHandler);
		
		// View Pending Requests (Manager)
		app.get("manager/pending", rc.m_viewPendingRequestsHandler);
		
		// View History (Manager)
		app.get("manager/history", rc.m_viewReimbursementHistoryHandler);
		
		// Approve Requests (Manager)
		app.patch("manager/approve", rc.m_approveRequestHandler);
		
		// TODO Deny Requests (Manager)
		app.patch("manager/deny", rc.m_denyRequestHandler);

	}

}
