package com.empl.mgr.service;

import com.empl.mgr.support.JSONReturn;

public abstract interface ComsumeTypeService {

	public abstract JSONReturn getConsumeType();
	public abstract JSONReturn getByName(String name);
	public abstract JSONReturn deleteConsumeType(long id);
	public abstract JSONReturn addConsumeType(String desp, String detail, long acctId) ;
}
