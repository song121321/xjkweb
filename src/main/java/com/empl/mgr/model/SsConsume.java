package com.empl.mgr.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ss_consume", uniqueConstraints = @UniqueConstraint(columnNames = "Id"))
public class SsConsume implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String descp;
	private long bankid;
	private long budgetid;
	private long accountid;
	private long currencyid;
	private long consumetypeid;
	private double je;
	private long status;
	private Date ctime;
	private Date mtime;
	private double n1;
	private double n2;
	private String c1;
	private String c2;
	private Date d1;
	private Date d2;
	private String detail;

	public SsConsume() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "descp")
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	@Column(name = "consumetypeid")
	public long getConsumetypeid() {
		return consumetypeid;
	}
	
	public void setConsumetypeid(long consumetypeid) {
		this.consumetypeid = consumetypeid;
	}

	@Column(name = "bankid")
	public long getBankid() {
		return bankid;
	}

	public void setBankid(long bankid) {
		this.bankid = bankid;
	}

	@Column(name = "budgetid")
	public long getBudgetid() {
		return budgetid;
	}

	public void setBudgetid(long budgetid) {
		this.budgetid = budgetid;
	}

	@Column(name = "accountid")
	public long getAccountid() {
		return accountid;
	}

	public void setAccountid(long accountid) {
		this.accountid = accountid;
	}

	@Column(name = "currencyid")
	public long getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}

	@Column(name = "je")
	public double getJe() {
		return je;
	}

	public void setJe(double je) {
		this.je = je;
	}

	@Column(name = "status")
	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	@Column(name = "ctime")
	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	@Column(name = "mtime")
	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	@Column(name = "n1")
	public double getN1() {
		return n1;
	}

	public void setN1(double n1) {
		this.n1 = n1;
	}

	@Column(name = "n2")
	public double getN2() {
		return n2;
	}

	public void setN2(double n2) {
		this.n2 = n2;
	}

	@Column(name = "c1")
	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}

	@Column(name = "c2")
	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	@Column(name = "d1")
	public Date getD1() {
		return d1;
	}

	public void setD1(Date d1) {
		this.d1 = d1;
	}

	@Column(name = "d2")
	public Date getD2() {
		return d2;
	}

	public void setD2(Date d2) {
		this.d2 = d2;
	}

	@Column(name = "detail")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof SsConsume) {

			SsConsume u = (SsConsume) obj;

			if (this.id == u.id && this.accountid == u.accountid&& this.je == u.je && this.consumetypeid == u.consumetypeid&& this.bankid == u.bankid && this.budgetid == u.budgetid
					&& this.currencyid == u.currencyid && this.detail.equals(u.detail) && this.descp.equals(u.descp)) {
				return true;
			}

		}
		return false;

	}
}
