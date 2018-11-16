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
@Table(name = "ss_work", uniqueConstraints = @UniqueConstraint(columnNames = "Id"))
public class SsWork implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private long workitemid;
	private double number;
	private double je;
	private Date worktime;
	private Date paytime;
	private long status;
	private double n1;
	private double n2;
	private String c1;
	private String c2;
	private Date d1;
	private Date d2;
	private String detail;
	private String descp;
	private Date mtime;
	private Date ctime;

	public SsWork() {
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

	@Column(name = "workitemid")
	public long getWorkitemid() {
		return workitemid;
	}

	public void setWorkitemid(long workitemid) {
		this.workitemid = workitemid;
	}

	@Column(name = "number")
	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	@Column(name = "je")
	public double getJe() {
		return je;
	}

	public void setJe(double je) {
		this.je = je;
	}

	@Column(name = "worktime")
	public Date getWorktime() {
		return worktime;
	}

	public void setWorktime(Date worktime) {
		this.worktime = worktime;
	}

	@Column(name = "paytime")
	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	@Column(name = "status")
	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
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

	@Column(name = "descp")
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	@Column(name = "mtime")
	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	@Column(name = "ctime")
	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
}
