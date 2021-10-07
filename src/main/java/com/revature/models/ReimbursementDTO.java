package com.revature.models;

public class ReimbursementDTO {

	private double reimb_amount;
	private String reimb_description;
	private int reimb_type_id_fk;
	
	public ReimbursementDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReimbursementDTO(double reimb_amount, String reimb_description, int reimb_type_id_fk) {
		super();
		this.reimb_amount = reimb_amount;
		this.reimb_description = reimb_description;
		this.reimb_type_id_fk = reimb_type_id_fk;
	}

	public double getReimb_amount() {
		return reimb_amount;
	}

	public void setReimb_amount(int reimb_amount) {
		this.reimb_amount = reimb_amount;
	}

	public String getReimb_description() {
		return reimb_description;
	}

	public void setReimb_description(String reimb_description) {
		this.reimb_description = reimb_description;
	}

	public int getReimb_type_id_fk() {
		return reimb_type_id_fk;
	}

	public void setReimb_type_id_fk(int reimb_type_id_fk) {
		this.reimb_type_id_fk = reimb_type_id_fk;
	}
}
