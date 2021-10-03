package com.revature.services;

import java.util.List;

import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementUpdateDTO;

public class ReimbursementService {
	
	ReimbursementDao rDao = new ReimbursementDao();
	private static int user_id; // all reimbursement services will share the same current user, gets updated when a new user logs in
	
	public List<Reimbursement> m_getReimbursementHistory() {
		
		System.out.println("Checking request history as manager id#: " + user_id);
		return rDao.m_getReimbursementHistory();
	}
	
	public List<Reimbursement> m_getPendingReimbursements() {
		
		System.out.println("Checking pending requests as manager id#: " + user_id);
		return rDao.m_getPendingReimbursements();
	}
	
	public List<Reimbursement> e_getPendingReimbursements() {
		
		// TODO ---------- hardcoded getting employee id -------------------
		// store current user somewhere when logged in and get user id from there and put it here
		System.out.println("Checking pending requests for employee#: " + user_id);
		return rDao.e_getPendingReimbursements(user_id);
	}
	
	public List<Reimbursement> e_getTicketHistory() {
		
		// TODO same as above
		System.out.println("Getting employee request history");
		return rDao.e_getTicketHistory(1);
	}

	public void e_submitRequest(ReimbursementDTO reimb) {

		rDao.e_submitRequest(reimb, user_id);
		
	}
	
	public static void setCurrentUser(int id) {
		System.out.println("Current user id: " + id);
		user_id = id;
	}
	
	public void m_approveRequest(ReimbursementUpdateDTO reimb) {
		rDao.m_approveReimbursement(reimb, user_id);
	}
	
	public void m_denyRequest(ReimbursementUpdateDTO reimb) {
		rDao.m_denyReimbursement(reimb, user_id);
	}
}
