package com.empl.mgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.field.SsBankField;
import com.empl.mgr.model.SsBank;
import com.empl.mgr.utils.StringUtil;

@Repository
public class BankDao extends AbstractDao<SsBank> {
	@Override
	public Class<SsBank> getEntityClass() {
		return SsBank.class;
	}

	public void deleteById(long id) {
		deleteByProperty(SsBankField.ID, id);
	}

	public SsBank findById(long id) {
		return findUniqueByProperty(SsBankField.ID, id);
	}

	@SuppressWarnings("unchecked")
	public List<SsBank> findNormalBank(String name) {
		String query = " from SsBank where status = 1";
		if (!StringUtil.empty(name)) {
			query += " and descp like :descp";
			return findSession().createQuery(query).setString("descp", "%" + name + "%").list();
		}

		return findSession().createQuery(query).list();
	}

}
