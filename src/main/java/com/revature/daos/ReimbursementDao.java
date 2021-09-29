package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDao implements ReimbursementDaoInterface {

	@Override
	public List<Reimbursement> m_getReimbursementHistory() {

		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "select * from ers_reimbursements";
			
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			
			while(rs.next()) {
				reimbs.add(new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getInt("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getString("reimb_receipt"),
						rs.getInt("reimb_author_fk"),
						rs.getInt("reimb_resolver_fk"),
						rs.getInt("reimb_status_id_fk"),
						rs.getInt("reimb_type_id_fk")
						));
			}
			
			return reimbs;
		}
		catch(SQLException e) {
			System.out.println("Problem occured when viewing reimbursement history!");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Reimbursement> m_getPendingReimbursements() {
			try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "select * from ers_reimbursements where reimb_status_id_fk = 1";
			
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			
			while(rs.next()) {
				reimbs.add(new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getInt("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getString("reimb_receipt"),
						rs.getInt("reimb_author_fk"),
						rs.getInt("reimb_resolver_fk"),
						rs.getInt("reimb_status_id_fk"),
						rs.getInt("reimb_type_id_fk")
						));
			}
			
			return reimbs;
		}
		catch(SQLException e) {
			System.out.println("Problem occured when viewing pending reimbursements!");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void m_approveReimbursement(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void m_denyReimbursement(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reimbursement> e_getPendingReimbursements(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			// get all pending requests from employee
			String sql = "select * from ers_reimbursements where reimb_status_id_fk = 1 and reimb_author_fk = ?"; 
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			
			while(rs.next()) {
				reimbs.add(new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getInt("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getString("reimb_receipt"),
						rs.getInt("reimb_author_fk"),
						rs.getInt("reimb_resolver_fk"),
						rs.getInt("reimb_status_id_fk"),
						rs.getInt("reimb_type_id_fk")
						));
			}
			
			return reimbs;
		}
		catch(SQLException e) {
			System.out.println("Problem occured when viewing pending reimbursements!");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Reimbursement> e_getTicketHistory(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			// get all reimbs by employee
			String sql = "select * from ers_reimbursements where reimb_author_fk = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			
			while(rs.next()) {
				reimbs.add(new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getInt("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getString("reimb_receipt"),
						rs.getInt("reimb_author_fk"),
						rs.getInt("reimb_resolver_fk"),
						rs.getInt("reimb_status_id_fk"),
						rs.getInt("reimb_type_id_fk")
						));
			}
			
			return reimbs;
		}
		catch(SQLException e) {
			System.out.println("Problem occured when viewing ticket history!");
			e.printStackTrace();
		}
		
		return null;
	}

}
