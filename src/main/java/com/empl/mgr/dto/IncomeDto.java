package com.empl.mgr.dto;

public class IncomeDto extends BaseModelDto {
	private static final long serialVersionUID = 1L;
	private AccountListDto account;
	private long accountId;
	private String accountName;
	private BankDto  bank;
	private long bankId;
	private String bankName;
	private double je;
	private long status;

	public IncomeDto() {
	}

	public IncomeDto(BaseModelDto base) {
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



	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BankDto getBank() {
		return bank;
	}



	public void setBank(BankDto bank) {
		this.bank = bank;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public double getJe() {
		return je;
	}

	public void setJe(double je) {
		this.je = je;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
}
