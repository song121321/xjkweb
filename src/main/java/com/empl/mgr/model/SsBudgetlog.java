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
@Table(name = "ss_budgetlog", uniqueConstraints = @UniqueConstraint(columnNames = "Id"))
public class SsBudgetlog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private double accountid;
	private String descp;
	private double je;
	private long status;
	private Date ctime;
	private double leftje;
	private long consumeid;
	private long budgetid;

	public SsBudgetlog() {
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

	@Column(name = "accountid")
	public double getAccountid() {
		return accountid;
	}

	public void setAccountid(double accountid) {
		this.accountid = accountid;
	}

	@Column(name = "descp")
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
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

	@Column(name = "leftje")
	public double getLeftje() {
		return leftje;
	}

	public void setLeftje(double leftje) {
		this.leftje = leftje;
	}

	@Column(name = "consumeid")
	public long getConsumeid() {
		return consumeid;
	}

	public void setConsumeid(long consumeid) {
		this.consumeid = consumeid;
	}

	@Column(name = "budgetid")
	public long getBudgetid() {
		return budgetid;
	}

	public void setBudgetid(long budgetid) {
		this.budgetid = budgetid;
	}
	
	
}
