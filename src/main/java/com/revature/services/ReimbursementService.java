package com.revature.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementUpdateDTO;

public class ReimbursementService {
	
	ReimbursementDao rDao = new ReimbursementDao();
	private static int user_id; // all reimbursement services will share the same current user, gets updated when a new user logs in
	
	Logger log = LogManager.getLogger(ReimbursementService.class);
	
	public List<Reimbursement> m_getReimbursementHistory() {
		
		System.out.println("Checking request history as manager id#: " + user_id);
		log.info("USER# " + user_id + " RETRIEVED REIMBURSEMENT HISTORY");
		return rDao.m_getReimbursementHistory();
	}
	
	public List<Reimbursement> m_getPendingReimbursements() {
		
		System.out.println("Checking pending requests as manager id#: " + user_id);
		log.info("USER# " + user_id + " RETRIEVED PENDING REIMBURSEMENTS");
		return rDao.m_getPendingReimbursements();
	}
	
	public List<Reimbursement> e_getPendingReimbursements() {
		
		// TODO ---------- hardcoded getting employee id -------------------
		// store current user somewhere when logged in and get user id from there and put it here
		System.out.println("Checking pending requests for employee#: " + user_id);
		log.info("USER# "+ user_id + " RETRIEVED PENDING REIMBURSEMENTS");
		return rDao.e_getPendingReimbursements(user_id);
	}
	
	public List<Reimbursement> e_getTicketHistory() {
		
		// TODO same as above
		System.out.println("Getting employee request history");
		log.info("USER# "+ user_id + " RETRIEVED REIMBURSEMENT HISTORY");
		return rDao.e_getTicketHistory(user_id);
	}

	public void e_submitRequest(ReimbursementDTO reimb) {
		
		rDao.e_submitRequest(reimb, user_id);
		log.info("USER# "+ user_id + " SUBMITTED A REIMBURSEMENT");
	}
	
	public static void setCurrentUser(int id) {
		System.out.println("Current user id: " + id);
		user_id = id;
	}
	
	public boolean m_approveRequest(ReimbursementUpdateDTO reimb) {
		
		if(requestExists(rDao.getRequestId(reimb.getReimb_id()))) {
			
			// check if request is pending
			if(requestPending(rDao.getRequestStatus(reimb.getReimb_id()))) {
				
				rDao.m_approveReimbursement(reimb, user_id);
				
				log.warn("USER# "+ user_id + " APPROVED A REIMBURSEMENT REQUEST");
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean m_denyRequest(ReimbursementUpdateDTO reimb) {
		
		if(requestExists(rDao.getRequestId(reimb.getReimb_id()))) {
			
			// check if request is pending
			if(requestPending(rDao.getRequestStatus(reimb.getReimb_id()))) {
				
				rDao.m_denyReimbursement(reimb, user_id);
				
				log.warn("USER# "+ user_id + " DENIED A REIMBURSEMENT REQUEST");
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean requestPending(int status) {
		return status == 1;
	}
	
	public boolean requestExists(int id) {
		return id != -1;
	}
	
	public boolean checkForNumberInput(String s) {
		
		if (s == null) // checks if the String is null {
	         return false;
	
	    for (int i = 0; i < s.length(); i++) {
	       // checks whether the character is not a letter
	       // if it is not a letter ,it will return false
	       if ((Character.isLetter(s.charAt(i)))) {
	          return false;
	       }
	    }
	    
	    return true;
	}
}
