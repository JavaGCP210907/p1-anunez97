package com.revature.services;

import java.util.List;

import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;

public class ReimbursementService {
	
	ReimbursementDao rDao = new ReimbursementDao();
	
	public List<Reimbursement> m_getReimbursementHistory() {
		
		return rDao.m_getReimbursementHistory();
	}
	
	public List<Reimbursement> m_getPendingReimbursements() {
		
		return rDao.m_getPendingReimbursements();
	}
	
	public List<Reimbursement> e_getPendingReimbursements() {
		
		// TODO ---------- hardcoded getting employee id -------------------
		// store current user somewhere when logged in and get user id from there and put it here
	
		return rDao.e_getPendingReimbursements(1);
	}
	
	public List<Reimbursement> e_getTicketHistory() {
		
		// TODO same as above
		
		return rDao.e_getTicketHistory(1);
	}
}
