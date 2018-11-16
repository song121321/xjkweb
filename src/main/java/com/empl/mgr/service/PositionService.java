package com.empl.mgr.service;

import com.empl.mgr.support.JSONReturn;

public abstract interface PositionService {

	/**
	 * @author Ryan
	 * @param page
	 * @param deptId
	 * @param searchValue
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findPositionListInfo(int page, long deptId, String searchValue, String acctName);

	/**
	 * @author Ryan
	 * @param deptId
	 * @param name
	 * @param desc
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn addPosition(long deptId, String name, String desc, String acctName);

	/**
	 * @author Ryan
	 * @param page
	 * @param deptId
	 * @param searchValue
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findPositionPage(int page, long deptId, String searchValue, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn deletePosition(long id, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @return
	 */
	public abstract JSONReturn findPositionById(long id);

	/**
	 * @author Ryan
	 * @param id
	 * @param deptId
	 * @param name
	 * @param desc
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn modifyPosition(long id, long deptId, String name, String desc, String acctName);

	/**
	 * @author Ryan
	 * @param deptId
	 * @return
	 */
	public abstract JSONReturn findPositionByDeptId(long deptId);

}
