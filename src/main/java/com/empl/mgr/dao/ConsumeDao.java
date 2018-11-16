package com.empl.mgr.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.model.SsConsume;
import com.empl.mgr.utils.StringUtil;

@Repository
public class ConsumeDao extends AbstractDao<SsConsume> {

	@Override
	public Class<SsConsume> getEntityClass() {
		return SsConsume.class;
	}

	@SuppressWarnings("unchecked")
	public List<SsConsume> getConsume() {
		String query = " from SsConsume where id < ? order by mtime desc";
		return findSession().createQuery(query).setLong(0, 100).list();
	}

	@SuppressWarnings("unchecked")
	public List<SsConsume> getConsume(String month, String budgetId,String consumeTypeId,String bankId,
			String content, int page) {
		String query = " from SsConsume where status !=2 ";
		if (!StringUtil.empty(budgetId) && StringUtil.string2Int(budgetId) > 0) {
			query += " and budgetid = " + StringUtil.string2Int(budgetId);
		}

		if (!StringUtil.empty(consumeTypeId) && StringUtil.string2Int(consumeTypeId) > 0) {
			query += " and consumetypeid = " + StringUtil.string2Int(consumeTypeId);
		}
		
		if (!StringUtil.empty(bankId) && StringUtil.string2Int(bankId) > 0) {
			query += " and bankid = " + StringUtil.string2Int(bankId);
		}
		
		if (!StringUtil.empty(month)) {
			query += " and ctime like '%" + month + "%' ";
		}

		if (!StringUtil.empty(content)) {
			query += " and (descp like '%" + content + "%' or detail like '%"
					+ content + "%' )";
		}
		query+="order by ctime desc";
		return findSession().createQuery(query.toString())
				.setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}

	public int getConsumeCounts(String month, String budgetId, String content,
			int page) {
		String query = "select count(*) from SsConsume where status !=2 ";
		if (!StringUtil.empty(budgetId) && StringUtil.string2Int(budgetId) > 0) {
			query += " and budgetid = " + StringUtil.string2Int(budgetId);
		}

		if (!StringUtil.empty(month)) {
			query += " and ctime like '%" + month + "%' ";
		}

		if (!StringUtil.empty(content)) {
			query += " and (descp like '%" + content + "%' or detail like '%"
					+ content + "%' )";
		}
		return Integer.parseInt(findSession().createQuery(query.toString())
				.uniqueResult().toString());
	}
	
	public int getConsumeCountsByBudgetId(String budgetId) {
		String query = "select count(*) from SsConsume where status !=2 ";
		if (!StringUtil.empty(budgetId) && StringUtil.string2Int(budgetId) > 0) {
			query += " and budgetid = " + StringUtil.string2Int(budgetId);
		}
		return Integer.parseInt(findSession().createQuery(query.toString())
				.uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<SsConsume> getConsume(Date d1, Date d2) {
		String query = " from SsConsume where ctime > ? and ctime <? and status !=? order by mtime desc";
		return findSession().createQuery(query).setDate(0, d1).setDate(1, d2)
				.setLong(2, 2).list();
	}

	@SuppressWarnings("unchecked")
	public List<SsConsume> getConsumeMonthly(String month) {
		String query = " from SsConsume where status !=? and ctime like :ctime order by mtime desc";
		return findSession().createQuery(query).setLong(0, 2)
				.setString("ctime", "%" + month + "%").list();
	}
}
