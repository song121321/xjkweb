package com.empl.mgr.dao;

import org.springframework.stereotype.Repository;

import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.field.SsConsumelogField;
import com.empl.mgr.model.SsConsumelog;
import com.empl.mgr.utils.DateTimeUtil;

@Repository
public class ConsumelogDao extends AbstractDao<SsConsumelog> {
	@Override
	public Class<SsConsumelog> getEntityClass() {
		return SsConsumelog.class;
	}

	public void deleteById(long id) {
		deleteByProperty(SsConsumelogField.ID, id);
	}

	public SsConsumelog findById(int id) {
		return findUniqueByProperty(SsConsumelogField.ID, id);
	}

	public boolean add(long accId, long consumeId, double je,long status, String descp) {
		SsConsumelog consumeLog = new SsConsumelog();
		consumeLog.setAccountid(accId);
		consumeLog.setConsumeid(consumeId);
		consumeLog.setCtime(DateTimeUtil.getCurrentTime());
		consumeLog.setDescp(descp);
		consumeLog.setJe(je);
		consumeLog.setStatus(status);
		save(consumeLog);
		return true;
	}
}
