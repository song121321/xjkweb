package com.empl.mgr.dro;                                                              
 import java.io.Serializable;                                                                                        
public class IncomeDro implements Serializable {                                 
	private static final long serialVersionUID = 1L;
private long id;
private String descp;
private long accountid;
private long bankid;
private double je;
private long status;
private double n1;
private double n2;
private String c1;
private String c2;
private String detail;
public IncomeDro(){}
public long getId() {                  
	return id;                           
}                                            
                                             
public void setId(long id) {     
	this.id = id;                  
}                                            
public String getDescp() {                  
	return descp;                           
}                                            
                                             
public void setDescp(String descp) {     
	this.descp = descp;                  
}                                            
public long getAccountid() {                  
	return accountid;                           
}                                            
                                             
public void setAccountid(long accountid) {     
	this.accountid = accountid;                  
}                                            
public long getBankid() {                  
	return bankid;                           
}                                            
                                             
public void setBankid(long bankid) {     
	this.bankid = bankid;                  
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
public double getN1() {                  
	return n1;                           
}                                            
                                             
public void setN1(double n1) {     
	this.n1 = n1;                  
}                                            
public double getN2() {                  
	return n2;                           
}                                            
                                             
public void setN2(double n2) {     
	this.n2 = n2;                  
}                                            
public String getC1() {                  
	return c1;                           
}                                            
                                             
public void setC1(String c1) {     
	this.c1 = c1;                  
}                                            
public String getC2() {                  
	return c2;                           
}                                            
                                             
public void setC2(String c2) {     
	this.c2 = c2;                  
}                                            
public String getDetail() {                  
	return detail;                           
}                                            
                                             
public void setDetail(String detail) {     
	this.detail = detail;                  
}                                            
	}
