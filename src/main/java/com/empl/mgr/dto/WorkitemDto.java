package com.empl.mgr.dto;


public class WorkitemDto extends BaseModelDto {
	private static final long serialVersionUID = 1L;
	private AccountListDto account;
	private double je;
	private String place;
	private String starttime;
	private String endtime;
	private long status;

	public WorkitemDto() {
	}

	public WorkitemDto(BaseModelDto base) {
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

	public AccountListDto getAccount() {
		return account;
	}



	public void setAccount(AccountListDto account) {
		this.account = account;
	}



	public double getJe() {
		return je;
	}

	public void setJe(double je) {
		this.je = je;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	

	public String getStarttime() {
		return starttime;
	}



	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}



	public String getEndtime() {
		return endtime;
	}



	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}



	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
}
