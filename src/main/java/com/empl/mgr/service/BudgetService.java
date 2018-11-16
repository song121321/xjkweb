package com.empl.mgr.service;

import com.empl.mgr.dro.BudgetDro;
import com.empl.mgr.dro.ConsumeDro;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.support.ValidBean;

public abstract interface BudgetService {
	public abstract JSONReturn getBudgetMonthly(String month);
	public abstract JSONReturn getBudgetByTimeAndDescp(String month,String descp);

	public abstract JSONReturn findBudgetById(long id);

	public abstract boolean useBudget(long accountid,long consumeId, ConsumeDro consumeDro);
	public abstract JSONReturn getBudgetLog(long budgetId);
	public abstract boolean backBudget(String decp,long budgetId,double je,long accountid,long consumeId);
	public abstract ValidBean canUseBudget(long budgetId, double je);
	public abstract JSONReturn save(BudgetDro dro, long accId);
	public abstract JSONReturn update(BudgetDro dro, long acctID);
	public abstract JSONReturn removeBudget(long budgetId, long acctID);
	
}
