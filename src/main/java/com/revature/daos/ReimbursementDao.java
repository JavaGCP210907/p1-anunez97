package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementUpdateDTO;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDao implements ReimbursementDaoInterface {

	@Override
	public List<Reimbursement> m_getReimbursementHistory() {

		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "select ers_reimbursements.reimb_id, ers_reimbursements.reimb_amount, ers_reimbursements.reimb_submitted, "
					+ "ers_reimbursements.reimb_resolved, "
					+ "ers_reimbursements.reimb_description, ers_reimbursements.reimb_author_fk, ers_reimbursements.reimb_resolver_fk, "
					+ "ers_reimbursement_statuses.reimb_status, ers_reimbursement_types.reimb_type "
					+ "from ers_reimbursements "
					+ "left join ers_reimbursement_statuses "
					+ "on ers_reimbursements.reimb_status_id_fk = ers_reimbursement_statuses.reimb_status_id "
					+ "left join ers_reimbursement_types "
					+ "on ers_reimbursements.reimb_type_id_fk = ers_reimbursement_types.reimb_type_id "
					+ "order by ers_reimbursements.reimb_id";
			
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			
			while(rs.next()) {
				reimbs.add(new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getInt("reimb_author_fk"),
						rs.getInt("reimb_resolver_fk"),
						rs.getString("reimb_status"),
						rs.getString("reimb_type")
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
			
			String sql = "select ers_reimbursements.reimb_id, ers_reimbursements.reimb_amount, ers_reimbursements.reimb_submitted, "
					+ "ers_reimbursements.reimb_resolved, "
					+ "ers_reimbursements.reimb_description, ers_reimbursements.reimb_author_fk, ers_reimbursements.reimb_resolver_fk, "
					+ "ers_reimbursement_statuses.reimb_status, ers_reimbursement_types.reimb_type "
					+ "from ers_reimbursements "
					+ "left join ers_reimbursement_statuses "
					+ "on ers_reimbursements.reimb_status_id_fk = ers_reimbursement_statuses.reimb_status_id "
					+ "left join ers_reimbursement_types "
					+ "on ers_reimbursements.reimb_type_id_fk = ers_reimbursement_types.reimb_type_id "
					+ "where ers_reimbursements.reimb_status_id_fk = 1"
					+ "order by ers_reimbursements.reimb_id";
			
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			
			while(rs.next()) {
				reimbs.add(new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getInt("reimb_author_fk"),
						rs.getInt("reimb_resolver_fk"),
						rs.getString("reimb_status"),
						rs.getString("reimb_type")
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
	public List<Reimbursement> e_getPendingReimbursements(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			// get all pending requests from employee
			String sql = "select ers_reimbursements.reimb_id, ers_reimbursements.reimb_amount, ers_reimbursements.reimb_submitted, "
					+ "ers_reimbursements.reimb_resolved, "
					+ "ers_reimbursements.reimb_description, ers_reimbursements.reimb_author_fk, ers_reimbursements.reimb_resolver_fk, "
					+ "ers_reimbursement_statuses.reimb_status, ers_reimbursement_types.reimb_type "
					+ "from ers_reimbursements "
					+ "left join ers_reimbursement_statuses "
					+ "on ers_reimbursements.reimb_status_id_fk = ers_reimbursement_statuses.reimb_status_id "
					+ "left join ers_reimbursement_types "
					+ "on ers_reimbursements.reimb_type_id_fk = ers_reimbursement_types.reimb_type_id "
					+ "where ers_reimbursements.reimb_status_id_fk = 1"
					+ "and ers_reimbursements.reimb_author_fk = ?"
					+ "order by ers_reimbursements.reimb_id";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			
			while(rs.next()) {
				reimbs.add(new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getInt("reimb_author_fk"),
						rs.getInt("reimb_resolver_fk"),
						rs.getString("reimb_status"),
						rs.getString("reimb_type")
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
			String sql = "select ers_reimbursements.reimb_id, ers_reimbursements.reimb_amount, ers_reimbursements.reimb_submitted, "
					+ "ers_reimbursements.reimb_resolved, "
					+ "ers_reimbursements.reimb_description, ers_reimbursements.reimb_author_fk, ers_reimbursements.reimb_resolver_fk, "
					+ "ers_reimbursement_statuses.reimb_status, ers_reimbursement_types.reimb_type "
					+ "from ers_reimbursements "
					+ "left join ers_reimbursement_statuses "
					+ "on ers_reimbursements.reimb_status_id_fk = ers_reimbursement_statuses.reimb_status_id "
					+ "left join ers_reimbursement_types "
					+ "on ers_reimbursements.reimb_type_id_fk = ers_reimbursement_types.reimb_type_id "
					+ "where ers_reimbursements.reimb_author_fk = ?"
					+ "order by ers_reimbursements.reimb_id";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			
			while(rs.next()) {
				reimbs.add(new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getInt("reimb_author_fk"),
						rs.getInt("reimb_resolver_fk"),
						rs.getString("reimb_status"),
						rs.getString("reimb_type")
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

	public void e_submitRequest(ReimbursementDTO rDto, int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			// get all reimbs by employee
			String sql = "insert into ers_reimbursements "
					+ "(reimb_amount, reimb_submitted, reimb_description, reimb_author_fk, reimb_status_id_fk, reimb_type_id_fk)"
					+ "values(?, ?, ?, ?, ?, ?)";
			
			String pendingSql = "select reimb_status_id from ers_reimbursement_statuses where reimb_status = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			PreparedStatement ps2 = conn.prepareStatement(pendingSql);
			
			ps2.setString(1, "PENDING"); // just in case db for status gets changed
			
			ResultSet rs = ps2.executeQuery();
			
			rs.next();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // this will format my dates to be SQL acceptable
			Date date = new Date(); // from java.util - will be converted into a java.sql date
			String currentDate = dateFormat.format(date); // will format our date based on the format we gave above
			
			// set the values for the reimbursement from the dto
			ps.setDouble(1, rDto.getReimb_amount());
			ps.setDate(2, java.sql.Date.valueOf(currentDate));
			ps.setString(3, rDto.getReimb_description());
			ps.setInt(4, id);
			ps.setInt(5, rs.getInt("reimb_status_id"));
			ps.setInt(6, rDto.getReimb_type_id_fk());
			
			// execute the insert query
			ps.executeUpdate();
			
			// TODO test inserting a request
		}
		catch(SQLException e) {
			System.out.println("Problem occured when viewing ticket history!");
			e.printStackTrace();
		}
	}

	@Override
	public void m_approveReimbursement(ReimbursementUpdateDTO reimb, int id) {
		// TODO Auto-generated method stub
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			// get all reimbs by employee
			String sql = "update ers_reimbursements set reimb_resolver_fk = ?, reimb_resolved = ?, reimb_status_id_fk = 2 where reimb_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // this will format my dates to be SQL acceptable
			Date date = new Date(); // from java.util - will be converted into a java.sql date
			String currentDate = dateFormat.format(date); // will format our date based on the format we gave above
			
			// set the values for the reimbursement from the dto
			ps.setInt(1, id);
			ps.setDate(2, java.sql.Date.valueOf(currentDate));
			ps.setInt(3, reimb.getReimb_id());
			
			// execute the update query
			ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("Problem occured when updating a ticket!");
			e.printStackTrace();
		}
	}

	@Override
	public void m_denyReimbursement(ReimbursementUpdateDTO reimb, int id) {
		// TODO Auto-generated method stub
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			// get all reimbs by employee
			String sql = "update ers_reimbursements set reimb_resolver_fk = ?, reimb_resolved = ?, reimb_status_id_fk = 3 where reimb_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // this will format my dates to be SQL acceptable
			Date date = new Date(); // from java.util - will be converted into a java.sql date
			String currentDate = dateFormat.format(date); // will format our date based on the format we gave above
			
			// set the values for the reimbursement from the dto
			ps.setInt(1, id);
			ps.setDate(2, java.sql.Date.valueOf(currentDate));
			ps.setInt(3, reimb.getReimb_id());
			
			// execute the update query
			ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("Problem occured when updating a ticket!");
			e.printStackTrace();
		}
		
	}

	@Override
	public int getRequestId(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			// get all reimbs by employee
			String sql = "select reimb_id from ers_reimbursements where reimb_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("reimb_id");
			}
		}
		catch(SQLException e) {
			System.out.println("Problem occured when viewing ticket history!");
			e.printStackTrace();
		}
		
		return -1;
	}

	// will only be called when the request is confirmed
	@Override
	public int getRequestStatus(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			// get all reimbs by employee
			String sql = "select reimb_status_id_fk from ers_reimbursements where reimb_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			return rs.getInt("reimb_status_id_fk");
		}
		catch(SQLException e) {
			System.out.println("Problem occured when viewing ticket history!");
			e.printStackTrace();
		}
		
		return -1;
	}

}
