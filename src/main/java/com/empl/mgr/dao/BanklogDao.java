package com.empl.mgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.field.SsBanklogField;
import com.empl.mgr.model.SsBanklog;

@Repository
public class BanklogDao extends AbstractDao<SsBanklog> {
	@Override
	public Class<SsBanklog> getEntityClass() {
		return SsBanklog.class;
	}

	public void deleteById(long id) {
		deleteByProperty(SsBanklogField.ID, id);
	}

	public SsBanklog findById(int id) {
		return findUniqueByProperty(SsBanklogField.ID, id);
	}

	@SuppressWarnings("unchecked")
	public List<SsBanklog> findTop100LogByBankId(long bankId) {
		String query = " from SsBanklog where bankid ="+bankId+"  ORDER BY ctime desc";
		return findSession().createQuery(query).setFirstResult(0).setMaxResults(100).list();
	}
}
