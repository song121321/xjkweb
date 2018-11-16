package com.empl.mgr.service;

import com.empl.mgr.dro.IncomeDro;
import com.empl.mgr.support.JSONReturn;

public abstract interface IncomeService {

	public abstract JSONReturn getIncome( String accId,String bankId,String content,int page);

	public abstract JSONReturn getIncomeCounts(String accId,String bankId,String content,int page);

	public abstract JSONReturn findIncomeByid(long id, long accId);

	public abstract JSONReturn removeIncome(long id, long accId);

	public abstract JSONReturn saveIncome(IncomeDro dro, long accId);


	public abstract JSONReturn updateIncome(IncomeDro consumeDro, long accId);

}
