package com.empl.mgr.service;

import com.empl.mgr.dro.BankDro;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.support.ValidBean;

public interface BankService {

	public abstract JSONReturn getBank(long id);
	public abstract JSONReturn getBank();
	public abstract JSONReturn getNormalBank(String zhName,long accId);
	public abstract JSONReturn deleteBank(long id);
	public abstract boolean useBank(long bankid, long accountid, double thisJe, String desp);
	public abstract ValidBean canUseBank(long bankid, double je, long type1);
	public abstract JSONReturn getBankLog(long bankId);
	public abstract JSONReturn saveBank(BankDro dro, long accId);
	public abstract JSONReturn updateBank(BankDro dro, long accId);
	public abstract JSONReturn deleteBank(long bankid, long accId);
}
