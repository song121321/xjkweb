package com.empl.mgr.dto;


public class WorkDto extends BaseModelDto {
	private static final long serialVersionUID = 1L;
	private WorkDto workitem;
	private double number;
	private double je;
	private String worktime;
	private String paytime;
	private long status;

	public WorkDto() {
	}

	public WorkDto(BaseModelDto base) {
		this.setId(base.getId());
		this.setC1(base.getC1());
		this.setC2(base.getC2());
		this.setD1(base.getD1());
		this.setD2(base.getD2());
		this.setN1(base.getN1());
		this.setN2(base.getN2());
		this.setCtime(base.getCtime());
		this.setMtime(base.getMtime());
		this.setDescp(base.getDescp());
		this.setDetail(base.getDetail());
	}

	public WorkDto getWorkitem() {
		return workitem;
	}



	public void setWorkitem(WorkDto workitem) {
		this.workitem = workitem;
	}



	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public double getJe() {
		return je;
	}

	public void setJe(double je) {
		this.je = je;
	}

	

	public String getWorktime() {
		return worktime;
	}



	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}



	public String getPaytime() {
		return paytime;
	}



	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}



	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
}
