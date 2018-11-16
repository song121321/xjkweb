package com.empl.mgr.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.convert.ConsumetypeConvert;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dao.ConsumeTypeDao;
import com.empl.mgr.field.SsBaseModelField;
import com.empl.mgr.model.SsConsumetype;
import com.empl.mgr.model.TeAccount;
import com.empl.mgr.service.ComsumeTypeService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.CompareUtil;
import com.empl.mgr.utils.DateTimeUtil;

@Scope
@Service
@Transactional(readOnly = true)
public class ComsumeTypeServiceImpl implements ComsumeTypeService {
	@Autowired
	private ConsumeTypeDao consumeTypeDao;

	@Autowired
	private AccountDao accountDao;

	public JSONReturn getConsumeType() {

		List<SsConsumetype> list = consumeTypeDao.findAll();
		return JSONReturn.buildSuccess(new ConsumetypeConvert().entityList2ToList(list));
	}
	
	public JSONReturn getByName(String name) {
		List<SsConsumetype> list = consumeTypeDao.getByName(name);
		return JSONReturn.buildSuccess(new ConsumetypeConvert().entityList2ToList(list));
	}
	

	@Transactional
	public JSONReturn addConsumeType(String desp, String detail, long acctId) {

		if (StringUtils.isEmpty(desp)) {

			return JSONReturn.buildFailure("添加失败,名称为空!");
		}

		TeAccount account = accountDao.findUniqueByProperty(SsBaseModelField.ID, (long) acctId);
		if (CompareUtil.isEmpty(account))
			return JSONReturn.buildFailure("添加失败, 当前用户不存在!");
		Date now = DateTimeUtil.getCurrentTime();
		SsConsumetype entity = new SsConsumetype();
		entity.setDescp(desp);
		entity.setDetail(detail);
		entity.setMuser((int) acctId);
		entity.setMtime(now);
		entity.setCtime(now);
		entity.setD1(now);
		entity.setD2(now);
		consumeTypeDao.save(entity);
		return JSONReturn.buildSuccess("消费类型添加成功!");
	}

	@Transactional
	public JSONReturn deleteConsumeType(long id) {

		consumeTypeDao.deleteById(id);

		return JSONReturn.buildSuccess("删除成功!");
	}

	

}
