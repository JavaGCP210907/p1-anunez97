package com.revature;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.services.LoginService;
import com.revature.services.ReimbursementService;
import com.revature.utils.ConnectionUtil;

public class Tests {

	// test variables
	public String fake_username =" amnotreal";
	public String fake_password = "amnotreal";
	
	public String real_username = "echupe";
	public String real_password = "pass6";
	
	public String emptyString = "";
	
	public int resolved_id = 2;
	public int unresolved_id = 1;
	public int invalid_id = -1;
	
	public static LoginService ls;
	public static ReimbursementService rs;
	
	
	@BeforeAll
	public static void beforeTests() {
		System.out.println("In BeforeAll method ...");
		
		// attempt to connect to the database
		try(Connection conn = ConnectionUtil.getConnection()) {
			System.out.println("Employee Reimbursement System connection successful!");
		}
		catch(SQLException e) {
			System.out.println("Employee Reimbursement System connection failed!");
			e.printStackTrace();
		}
		
		ls = new LoginService();
		rs = new ReimbursementService();
	}
	
	@AfterAll
	public static void afterTests() {
		System.out.println("In AfterAll method ...");
	}
	
	@Test
	public void testWrongUsername() {
		System.out.println("Testing Wrong Username ...");
		
		assertTrue(false == ls.login(fake_username, fake_password));
		
	}
	
	@Test
	public void testWrongPassword() {
		System.out.println("Testing Wrong Password ...");
		
		assertTrue(false == ls.login(real_username, fake_password));
	}
	
	@Test
	public void testEmptyUsername() {
		System.out.println("Testing Empty Username ...");
		
		assertTrue(false == ls.login(emptyString, fake_password));
	}
	
	@Test
	public void testEmptyPassword() {
		System.out.println("Testing Empty Password ...");
		
		assertTrue(false == ls.login(real_username, emptyString));
	}
	
	@Test
	public void testResolvedRequestId() {
		System.out.println("Testing Resolved Request Id ...");
		
		assertTrue(false == rs.requestPending(resolved_id));
	}
	
	@Test
	public void testUnresolvedRequestId() {
		System.out.println("Testing Unresolved Request Id ...");
		
		assertTrue(true == rs.requestPending(unresolved_id));
	}
	
	@Test
	public void testInvalidRequestId() {
		System.out.println("Testing Invalid Request Id ...");
		
		assertTrue(false == rs.requestExists(invalid_id));
	}
	
	@Test
	public void testInvalidNumberInput() {
		System.out.println("Testing Invalid Manager Input ...");
		
		assertTrue(false == rs.checkForNumberInput("5asdf"));
	}
	
	@Test
	public void testValidNumberInput() {
		System.out.println("Testing Invalid Employee Input ...");
		
		assertTrue(true == rs.checkForNumberInput("6"));
	}
}
