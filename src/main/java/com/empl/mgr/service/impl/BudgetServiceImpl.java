package com.empl.mgr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.convert.BudgetConvert;
import com.empl.mgr.convert.BudgetlogConvert;
import com.empl.mgr.dao.BudgetDao;
import com.empl.mgr.dao.BudgetlogDao;
import com.empl.mgr.dao.ConsumeTypeDao;
import com.empl.mgr.dro.BudgetDro;
import com.empl.mgr.dro.ConsumeDro;
import com.empl.mgr.field.SsBudgetField;
import com.empl.mgr.model.SsBudget;
import com.empl.mgr.model.SsBudgetlog;
import com.empl.mgr.service.BudgetService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.support.ValidBean;
import com.empl.mgr.utils.CompareUtil;
import com.empl.mgr.utils.DateTimeUtil;

@Scope
@Service
@Transactional(readOnly = true)
public class BudgetServiceImpl implements BudgetService {

	@Autowired
	BudgetDao budgetDao;

	@Autowired
	BudgetlogDao budgetlogDao;

	@Autowired
	ConsumeTypeDao consumeTypeDao;

	public JSONReturn getBudgetMonthly(String month) {
		List<SsBudget> list = budgetDao.getBudgetMonthly(month);
		if (CollectionUtils.isEmpty(list))
			return JSONReturn.buildFailure("未获取到数据!");
		return JSONReturn.buildSuccess(new BudgetConvert().entityList2ToList(list));
	}

	public JSONReturn findBudgetById(long id) {
		SsBudget obj = budgetDao.findUniqueByProperty(SsBudgetField.ID, id);
		if (CompareUtil.isEmpty(obj))
			return JSONReturn.buildFailure("获取源数据失败!");
		return JSONReturn.buildSuccess(new BudgetConvert().entity2Dto(obj));
	}

	public JSONReturn getBudgetByTimeAndDescp(String month, String descp) {
		List<SsBudget> list = budgetDao.getBudgetByTimeAndDescp(month, descp);
		if (CollectionUtils.isEmpty(list))
			return JSONReturn.buildFailure("未获取到数据!");
		return JSONReturn.buildSuccess(new BudgetConvert()
				.entityList2ToList(list));
	}

	@Transactional
	public JSONReturn save(BudgetDro dro, long accId) {
		SsBudget budget = new BudgetConvert().dro2Entity(dro);
		budget.setMtime(DateTimeUtil.getCurrentTime());
		budget.setCtime(DateTimeUtil.getCurrentTime());
		long budgetId = budgetDao.saveWithId(budget);
		SsBudgetlog log = new SsBudgetlog();
		log.setAccountid(accId);
		log.setBudgetid(budgetId);
		log.setConsumeid(0);
		log.setCtime(DateTimeUtil.getCurrentTime());
		log.setDescp("新建预算" + budget.getDescp());
		log.setLeftje(budget.getLeftje());
		log.setJe(0);
		log.setStatus(0);
		budgetlogDao.save(log);
		return JSONReturn.buildSuccess("新增成功");
	}

	@Transactional
	public JSONReturn update(BudgetDro dro, long accId) {

		SsBudget oldBudget = budgetDao.findById(dro.getId());
		SsBudget newBudget = new BudgetConvert().dro2Entity(dro);
		oldBudget.setMtime(DateTimeUtil.getCurrentTime());
		String oldBudgetName = oldBudget.getDescp();
		String banklogDesc = "【" + oldBudgetName + "】信息变更,";

		if (!oldBudget.getDescp().trim().equals(newBudget.getDescp().trim())) {
			banklogDesc += "原名称【" + oldBudget.getDescp() + "】变更为【"
					+ newBudget.getDescp() + "】,";
			oldBudget.setDescp(newBudget.getDescp());
		}

		if (!oldBudget.getDetail().trim().equals(newBudget.getDetail().trim())) {
			banklogDesc += "原详情【" + oldBudget.getDetail() + "】变更为【"
					+ newBudget.getDetail() + "】,";
			oldBudget.setDetail(newBudget.getDetail());
		}

		if (oldBudget.getJe() != newBudget.getJe()) {
			banklogDesc += "原金额【" + oldBudget.getJe() + "】变更为【"
					+ newBudget.getJe() + "】,";
			oldBudget.setJe(newBudget.getJe());
		}

		budgetDao.merge(oldBudget);
		SsBudgetlog budgetLog = new SsBudgetlog();
		budgetLog.setConsumeid(0);
		budgetLog.setBudgetid(oldBudget.getId());
		budgetLog.setAccountid(accId);
		budgetLog.setDescp(banklogDesc);
		budgetLog.setJe(0);
		budgetLog.setLeftje(newBudget.getJe());
		budgetLog.setCtime(DateTimeUtil.getCurrentTime());
		budgetLog.setStatus(1);
		budgetlogDao.save(budgetLog);
		return JSONReturn.buildSuccess("更新成功");
	}

	public boolean backBudget(String decp, long budgetId, double je,
			long accountid, long consumeId) {
		try {
			SsBudget budget = budgetDao.findUniqueByProperty(SsBudgetField.ID,
					budgetId);
			double beforeJe = budget.getLeftje();
			double afterJe = beforeJe + je;
			String logDesp = "【" + decp + "】退回【" + je + "】元,目前金额：【" + afterJe
					+ "】元。 ";
			Date now = DateTimeUtil.getCurrentTime();
			budget.setLeftje(afterJe);
			budget.setMtime(now);
			budgetDao.merge(budget);
			SsBudgetlog budgetLog = new SsBudgetlog();
			budgetLog.setAccountid(accountid);
			budgetLog.setBudgetid(budgetId);
			budgetLog.setConsumeid(consumeId);
			budgetLog.setCtime(now);
			budgetLog.setDescp(logDesp);
			budgetLog.setJe(-je);
			budgetLog.setLeftje(afterJe);
			budgetLog.setStatus(1);
			budgetlogDao.save(budgetLog);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean useBudget(long accountid, long consumeId,
			ConsumeDro consumeDro) {
		try {
			SsBudget budget = budgetDao.findUniqueByProperty(SsBudgetField.ID,
					consumeDro.getBudgetid());
			double thisJe = consumeDro.getJe();
			double beforeJe = budget.getLeftje();
			double afterJe = beforeJe - thisJe;
			String desp = "【" + consumeDro.getDescp() + "】占用【" + thisJe
					+ "】元，剩余：【" + afterJe + "】元。 ";
			Date now = DateTimeUtil.getCurrentTime();
			budget.setLeftje(afterJe);
			budget.setMtime(now);
			budgetDao.merge(budget);
			SsBudgetlog budgetLog = new SsBudgetlog();
			budgetLog.setAccountid(accountid);
			budgetLog.setBudgetid(consumeDro.getBudgetid());
			budgetLog.setConsumeid(consumeId);
			budgetLog.setCtime(now);
			budgetLog.setDescp(desp);
			budgetLog.setJe(thisJe);
			budgetLog.setLeftje(afterJe);
			budgetLog.setStatus(1);
			budgetlogDao.save(budgetLog);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public ValidBean canUseBudget(long budgetId, double je) {
		ValidBean vb = new ValidBean();
		// 预算可以超额
		// SsBudget budget = budgetDao.findUniqueByProperty(SsBudgetField.ID,
		// budgetId);
		// if (budget.getLeftje() < je) {
		// vb.setSuccess(false);
		// vb.setReason("此预算可用余额不足！");
		// return vb;
		// }
		vb.setSuccess(true);
		return vb;
	}

	public JSONReturn getBudgetLog(long budgetId) {
		ArrayList<SsBudgetlog> entityList = (ArrayList<SsBudgetlog>) budgetlogDao
				.findBudgetLogByBudgetId(budgetId);
		return JSONReturn.buildSuccess(new BudgetlogConvert()
				.entityList2ToList(entityList));
	}

	@Transactional
	public JSONReturn removeBudget(long budgetId, long accId) {
		budgetDao.deleteByProperty(SsBudgetField.ID, budgetId);
		SsBudgetlog budgetLog = new SsBudgetlog();
		budgetLog.setAccountid(accId);
		budgetLog.setBudgetid(budgetId);
		budgetLog.setConsumeid(0);
		budgetLog.setCtime(DateTimeUtil.getCurrentTime());
		budgetLog.setDescp("预算删除");
		budgetLog.setJe(0);
		budgetLog.setLeftje(0);
		budgetLog.setStatus(1);
		budgetlogDao.save(budgetLog);
		return JSONReturn.buildSuccess("预算已被删除！");
	}
}
