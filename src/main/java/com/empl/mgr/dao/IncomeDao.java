package com.empl.mgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.field.SsIncomeField;
import com.empl.mgr.model.SsIncome;
import com.empl.mgr.utils.StringUtil;

@Repository
public class IncomeDao extends AbstractDao<SsIncome> {
	@Override
	public Class<SsIncome> getEntityClass() {
		return SsIncome.class;
	}

	public void deleteById(long id) {
		deleteByProperty(SsIncomeField.ID, id);
	}

	public SsIncome findById(long id) {
		return findUniqueByProperty(SsIncomeField.ID, id);
	}

	@SuppressWarnings("unchecked")
	public List<SsIncome> getIncome(String accId, String bankId,
			String content, int page) {
		String query = " from SsIncome where status=0 ";
		if (!StringUtil.empty(accId) && StringUtil.string2Int(accId) > 0) {
			query += " and accountid = " + StringUtil.string2Int(accId);
		}

		if (!StringUtil.empty(bankId) && StringUtil.string2Int(bankId) > 0) {
			query += " and bankid = " + StringUtil.string2Int(bankId);
		}

		if (!StringUtil.empty(content)) {
			query += " and (descp like '%" + content + "%' or detail like '%"
					+ content + "%' )";
		}
		query += "order by ctime desc";
		return findSession().createQuery(query.toString())
				.setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}

	public int getIncomeCounts(String accId, String bankId,
			String content, int page) {
		String query = "select count(*) from SsIncome where status=0 ";
		if (!StringUtil.empty(accId) && StringUtil.string2Int(accId) > 0) {
			query += " and accountid = " + StringUtil.string2Int(accId);
		}
		if (!StringUtil.empty(bankId) && StringUtil.string2Int(bankId) > 0) {
			query += " and bankid = " + StringUtil.string2Int(bankId);
		}

		if (!StringUtil.empty(content)) {
			query += " and (descp like '%" + content + "%' or detail like '%"
					+ content + "%' )";
		}
		return Integer.parseInt(findSession().createQuery(query.toString())
				.uniqueResult().toString());
	}

}
