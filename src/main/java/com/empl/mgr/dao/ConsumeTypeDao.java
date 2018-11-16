package com.empl.mgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.field.SsConsumeTypeField;
import com.empl.mgr.model.SsConsumetype;
import com.empl.mgr.utils.StringUtil;

@Repository
public class ConsumeTypeDao extends AbstractDao<SsConsumetype> {

	@Override
	public Class<SsConsumetype> getEntityClass() {
		return SsConsumetype.class;
	}

	public void deleteById(long id) {
		deleteByProperty(SsConsumeTypeField.ID, id);
	}

	public SsConsumetype findById(long id) {
		return findUniqueByProperty(SsConsumeTypeField.ID, id);
	}

	@SuppressWarnings("unchecked")
	public List<SsConsumetype> getByName(String name) {
		String query = " from SsConsumetype ";
		if (!StringUtil.empty(name)) {
			query += " where (descp like '%" + name + "%' or detail like '%"
					+ name + "%' )";
		}
		return findSession().createQuery(query).list();
	}

}
