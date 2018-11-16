package com.empl.mgr.service;

import com.empl.mgr.support.JSONReturn;

public abstract interface TrainingService {

	/**
	 * @author Ryan
	 * @param name
	 * @param description
	 * @param startTime
	 * @param endTime
	 * @param isInsertAttend
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn addTraining(String name, String description, String startTime, String endTime,
			boolean isInsertAttend, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param name
	 * @param description
	 * @param startTime
	 * @param endTime
	 * @param isInsertAttend
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn modifyTraining(long id, String name, String description, String startTime,
			String endTime, boolean isInsertAttend, String acctName);

	/**
	 * @author Ryan
	 * @param searchValue
	 * @param page
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findTrainingList(String searchValue, int page, String acctName);

	/**
	 * @author Ryan
	 * @param searchValue
	 * @param page
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findTrainingPage(String searchValue, int page, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn delete(long id, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findTrainingInfoById(long id, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn start(long id, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param note
	 * @param enddate
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn stop(long id, String note, String enddate, String acctName);

	/** 
	 * @author Ryan
	 * @param trainingId
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findRecord(long trainingId, String acctName);

	/**
	 * @author Ryan
	 * @param emplId
	 * @param trainingId
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn addTrainingByEmplId(long emplId, long trainingId, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn delEmployeesTrainingRecord(long id, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param note
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn stopEmployeesTraining(long id, String note, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param note
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn evaluationEmployeesTraining(long id, String note, String acctName);

}
