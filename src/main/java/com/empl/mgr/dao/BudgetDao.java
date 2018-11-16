package com.empl.mgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.model.SsBudget;
import com.empl.mgr.utils.StringUtil;

@Repository
public class BudgetDao extends AbstractDao<SsBudget> {

	@Override
	public Class<SsBudget> getEntityClass() {
		return SsBudget.class;
	}

	@SuppressWarnings("unchecked")
	public List<SsBudget> getBudgetByTimeAndDescp(String month,String descp) {
		String query = " from SsBudget where 1=1 ";
		if (!StringUtil.empty(month)) {
		int year = StringUtil.string2Int(month.split("-")[0]);
		int monthi = StringUtil.string2Int(month.split("-")[1]);
		query += " and year = "+year+" and month = "+monthi+" ";
		}
	
		if (!StringUtil.empty(descp)) {
			query += " and descp like :descp order by mtime desc";
			return findSession().createQuery(query).setString("descp", "%" + descp + "%").list();
		}
		query += " order by mtime desc ";
		return findSession().createQuery(query).list();
	}

	@SuppressWarnings("unchecked")
	public List<SsBudget> findBudgetById(int Id) {
		String query = " from SsBudget where id = ?";
		return findSession().createQuery(query).setInteger(0, Id).list();
	}

	@SuppressWarnings("unchecked")
	public List<SsBudget> getBudgetMonthly(String month) {
		if (StringUtil.empty(month)) {
			return null;
		}
		int year = StringUtil.string2Int(month.split("-")[0]);
		int monthi = StringUtil.string2Int(month.split("-")[1]);
		String query = " from SsBudget  where year = ? and month = ? order by mtime desc";
		return findSession().createQuery(query).setLong(0, year).setLong(1, monthi).list();
	}

}
