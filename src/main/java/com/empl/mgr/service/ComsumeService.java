package com.empl.mgr.service;

import java.util.Date;

import com.empl.mgr.dro.ConsumeDro;
import com.empl.mgr.support.JSONReturn;

public abstract interface ComsumeService {

	public abstract JSONReturn Test();

	public abstract JSONReturn getConsume(Date d1, Date d2);

	public abstract JSONReturn getConsumeMonthly(String month);
	
	public abstract JSONReturn getConsume(String month, String budgetId,String consumeTypeId,String bankId,String content,int page);

	public abstract JSONReturn getConsumeCounts(String month, String budgetId,String content,int page);
	
	public abstract JSONReturn findConsumeByid(long id, long accId);

	public abstract JSONReturn removeConsume(long id, long accId);

	public abstract JSONReturn saveConsume(ConsumeDro dro, long accId);

	public abstract JSONReturn saveForImport(ConsumeDro dro, long accId);


	public abstract JSONReturn updateConsume(ConsumeDro consumeDro, long accId);

	public abstract JSONReturn getConsumeLog(long consumeId);
}
