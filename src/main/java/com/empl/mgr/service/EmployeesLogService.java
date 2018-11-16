package com.empl.mgr.service;

public abstract interface EmployeesLogService {

	/**
	 * @author Ryan
	 * @param emplId
	 * @param acctName
	 * @param type
	 * @param note
	 */
	public abstract void save(long emplId, String acctName, int type, String note);

}
