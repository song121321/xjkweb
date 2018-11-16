package com.empl.mgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.field.SsBudgetlogField;
import com.empl.mgr.model.SsBudgetlog;

@Repository
public class BudgetlogDao extends AbstractDao<SsBudgetlog> {
	@Override
	public Class<SsBudgetlog> getEntityClass() {
		return SsBudgetlog.class;
	}

	public void deleteById(long id) {
		deleteByProperty(SsBudgetlogField.ID, id);
	}

	public SsBudgetlog findById(int id) {
		return findUniqueByProperty(SsBudgetlogField.ID, id);
	}
	@SuppressWarnings("unchecked")
	public List<SsBudgetlog> findBudgetLogByBudgetId(long budgetId) {
		String query = " from SsBudgetlog where budgetid = ? order by ctime desc ";
		return findSession().createQuery(query).setLong(0, budgetId).list();
	}
}
