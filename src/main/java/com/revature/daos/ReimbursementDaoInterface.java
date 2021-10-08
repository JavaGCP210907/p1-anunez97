package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementUpdateDTO;

public interface ReimbursementDaoInterface {
	
	// m - Manager method, e - Employee method
	
	public List<Reimbursement> m_getReimbursementHistory();         // get the history of all reimbursements
	
	public List<Reimbursement> m_getPendingReimbursements();        // get all pending reimbursements
	
	public void m_approveReimbursement(ReimbursementUpdateDTO reimb, int id);                     // approve the pending reimbursement by id
	
	public void m_denyReimbursement(ReimbursementUpdateDTO reimb, int id);                        // deny a pending reimbursement by id
	
	public List<Reimbursement> e_getPendingReimbursements(int id);  // get all pending requests by the employee's id
	
	public List<Reimbursement> e_getTicketHistory(int id);          // get employee ticket history  
	
	public int getRequestId(int id);
	
	public int getRequestStatus(int id);
}
