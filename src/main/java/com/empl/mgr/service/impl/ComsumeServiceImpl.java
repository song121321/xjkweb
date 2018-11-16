package com.empl.mgr.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.convert.ConsumeConvert;
import com.empl.mgr.convert.ConsumelogConvert;
import com.empl.mgr.dao.BankDao;
import com.empl.mgr.dao.BudgetDao;
import com.empl.mgr.dao.ConsumeDao;
import com.empl.mgr.dao.ConsumeTypeDao;
import com.empl.mgr.dao.ConsumelogDao;
import com.empl.mgr.dro.ConsumeDro;
import com.empl.mgr.dto.ConsumeDto;
import com.empl.mgr.field.SsConsumeField;
import com.empl.mgr.field.SsConsumelogField;
import com.empl.mgr.model.SsBank;
import com.empl.mgr.model.SsBudget;
import com.empl.mgr.model.SsConsume;
import com.empl.mgr.model.SsConsumetype;
import com.empl.mgr.model.SsConsumelog;
import com.empl.mgr.service.BankService;
import com.empl.mgr.service.BudgetService;
import com.empl.mgr.service.ComsumeService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.support.ValidBean;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.PageUtils;

@Scope
@Service
@Transactional(readOnly = false)
public class ComsumeServiceImpl implements ComsumeService {
	@Autowired
	private ConsumeDao consumeDao;
	@Autowired
	private ConsumelogDao consumelogDao;
	@Autowired
	private BudgetDao budgetDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private ConsumeTypeDao typeDao;
	@Autowired
	private BudgetService budgetService;
	@Autowired
	private BankService bankService;

	public JSONReturn removeConsume(long id, long accId) {
		SsConsume consume = consumeDao.findUniqueByProperty(SsConsumeField.ID, id);

		budgetService.backBudget(consume.getDescp() + "退回预算处理", consume.getBudgetid(), consume.getJe(), accId, consume.getId());
		bankService.useBank(consume.getBankid(), accId, -consume.getJe(), consume.getDescp() + "退款处理");
		consume.setStatus(2);
		consumeDao.merge(consume);
		consumelogDao.add(accId, id, consume.getJe(), 2, "删除记录。");
		return JSONReturn.buildSuccess(consume.getDescp() + " 已被删除！");
	}

	public JSONReturn getConsume(Date d1, Date d2) {
		return JSONReturn.buildSuccess((ArrayList<ConsumeDto>) new ConsumeConvert().entityList2ToList(consumeDao.getConsume(d1, d2)));
	}
	public JSONReturn getConsume(String month, String budgetId,String consumeTypeId,String bankId,String content,int page) {
		return JSONReturn.buildSuccess((ArrayList<ConsumeDto>) new ConsumeConvert().entityList2ToList(consumeDao.getConsume(month,budgetId,consumeTypeId,bankId,content,page)));
	}
	public JSONReturn getConsumeCounts(String month, String budgetId,String content,int page) {
		int count = consumeDao.getConsumeCounts(month,budgetId,content,page);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.PAGE_LIST));
	}

	public JSONReturn getConsumeMonthly(String month) {
		return JSONReturn.buildSuccess((ArrayList<ConsumeDto>) new ConsumeConvert().entityList2ToList(consumeDao.getConsumeMonthly(month)));
	}

	public JSONReturn findConsumeByid(long id, long accId) {
		SsConsume entity = consumeDao.findUniqueByProperty(SsConsumeField.ID, id);
		consumelogDao.add(accId, id, entity.getJe(), 3, "查看");
		return JSONReturn.buildSuccess(new ConsumeConvert().entity2Dto(entity));
	}

	
	public JSONReturn Test() {
		// TODO Auto-generated method stub
		return JSONReturn.buildSuccess("Helloworld");
	}
	
//	@Scheduled(cron="0/5 * * * * ? ")
//	public void Test1() {
//		System.out.println(DateTimeUtil.getCurrentTime().getMinutes()+"ahahah--"+DateTimeUtil.getCurrentTime().getSeconds());
//	}

	/**
	 * 更改消费记录 1、消费记录属性变更 2、budget应当恢复预算金额 3、budgetlog增加一条恢复log 4、bank应当恢复金额
	 * 5、banklog增加一条恢复log 6、新budget预算金额减少 7、新budgetlog增加一条 8、新bank金额减少
	 * 9、新banklog增加一条 10、consumelog增加一条 update
	 */
	@Transactional
	public JSONReturn updateConsume(ConsumeDro dro, long accId) {
		boolean jeChange = false;
		ValidBean vb = bankService.canUseBank(dro.getBankid(), dro.getJe(), 0);
		if (!vb.isSuccess()) {
			return JSONReturn.buildFailure(vb.getReason());
		}
		vb = budgetService.canUseBudget(dro.getBudgetid(), dro.getJe());
		if (!vb.isSuccess()) {
			return JSONReturn.buildFailure(vb.getReason());
		}
		SsConsume oldConsume = consumeDao.findById(dro.getId());
		SsConsume newConsume = new ConsumeConvert().dro2Entity(dro);
		SsBudget oldBudget = budgetDao.findById(oldConsume.getBudgetid());
		SsBudget newBudget = budgetDao.findById(newConsume.getBudgetid());
		SsBank oldBank = bankDao.findById(oldConsume.getBankid());
		SsBank newBank = bankDao.findById(newConsume.getBankid());
		SsConsumetype oldConsumeType = typeDao.findById(oldConsume.getConsumetypeid());
		SsConsumetype newConsumeType = typeDao.findById(newConsume.getConsumetypeid());

		if (oldConsume.equals(newConsume)) {
			return JSONReturn.buildFailure("未发现改变，无需更改");
		}
		String oldConsumeDesc = oldConsume.getDescp();
		String consumeLog = "变更消费记录：【" + oldConsumeDesc + "】,";
		if (!oldConsume.getDescp().equals(newConsume.getDescp())) {
			consumeLog += "\n\t 消费简述由【" + oldConsume.getDescp() + "】变更为【" + newConsume.getDescp() + "】,";
			oldConsume.setDescp(newConsume.getDescp());
		}
		if (!oldConsume.getDetail().equals(newConsume.getDetail())) {
			consumeLog += "\n\t 消费详情由【" + oldConsume.getDetail() + "】变更为【" + newConsume.getDetail() + "】,";
			oldConsume.setDetail(newConsume.getDetail());
		}
		if (oldConsumeType.getId()!=newConsumeType.getId()) {
			consumeLog += "\n\t 消费类型由【" + oldConsumeType.getDetail() + "】变更为【" + oldConsumeType.getDetail() + "】,";
			oldConsume.setConsumetypeid(newConsume.getConsumetypeid());
		}
		if (oldConsume.getJe() != newConsume.getJe()) {
			consumeLog += "\n\t 金额由【" + oldConsume.getJe() + "】变更为【" + newConsume.getJe() + "】,";
			changeBudget(dro, oldBudget, newBudget, accId, oldConsume.getJe(), oldConsumeDesc + "退回预算处理 ");
			changeBank(dro, oldBank, newBank, accId, oldConsume.getJe(), oldConsumeDesc + "退账处理 ");
			jeChange = true;
			oldConsume.setJe(newConsume.getJe());
			if (oldConsume.getBudgetid() != newConsume.getBudgetid()) {
				consumeLog += "\n\t 预算由【" + oldBudget.getDescp() + "】变更为【" + newBudget.getDescp() + "】,";
				oldConsume.setBudgetid(newConsume.getBudgetid());
			}
			if (oldConsume.getBankid() != newConsume.getBankid()) {
				consumeLog += "\n\t 支付账户由【" + oldBank.getDescp() + "】变更为【" + newBank.getDescp() + "】,";
				oldConsume.setBankid(newConsume.getBankid());
			}
		}
		if (!jeChange && oldConsume.getBudgetid() != newConsume.getBudgetid()) {
			consumeLog += "\n\t 预算由【" + oldBudget.getDescp() + "】变更为【" + newBudget.getDescp() + "】,";
			changeBudget(dro, oldBudget, newBudget, accId, oldConsume.getJe(), oldConsumeDesc + "退回预算处理 ");
			oldConsume.setBudgetid(newConsume.getBudgetid());
		}

		if (!jeChange && oldConsume.getBankid() != newConsume.getBankid()) {
			consumeLog += "\n\t 支付账户由【" + oldBank.getDescp() + "】变更为【" + newBank.getDescp() + "】,";
			changeBank(dro, oldBank, newBank, accId, oldConsume.getJe(), oldConsumeDesc + "退账处理 ");
			oldConsume.setBankid(newConsume.getBankid());
		}
		oldConsume.setMtime(DateTimeUtil.getCurrentTime());
		oldConsume.setAccountid(accId);
		consumeDao.merge(oldConsume);
		consumelogDao.add(accId, dro.getId(), dro.getJe(), 1, consumeLog);
		return JSONReturn.buildSuccess("修改消费记录成功");

	}

	private void changeBudget(ConsumeDro dro, SsBudget oldBudget, SsBudget newBudget, long accId, double backJe, String backReason) {

		budgetService.backBudget(backReason, oldBudget.getId(), backJe, accId, dro.getId());
		budgetService.useBudget(accId, dro.getId(), dro);
	}

	private void changeBank(ConsumeDro dro, SsBank oldBank, SsBank newBank, long accId, double backJe, String backReason) {
		bankService.useBank(oldBank.getId(), accId, -backJe, backReason);
		bankService.useBank(newBank.getId(), accId, dro.getJe(), dro.getDescp());
	}
	@Transactional
	public JSONReturn saveForImport(ConsumeDro dro, long accId) {
		ValidBean vb = bankService.canUseBank(dro.getBankid(), dro.getJe(), 0);
		if (!vb.isSuccess()) {
			return JSONReturn.buildFailure(vb.getReason());
		}
		vb = budgetService.canUseBudget(dro.getBudgetid(), dro.getJe());
		if (!vb.isSuccess()) {
			return JSONReturn.buildFailure(vb.getReason());
		}

		SsConsume consume = new ConsumeConvert().dro2Entity(dro);
		consume.setAccountid(accId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			consume.setCtime(sdf.parse(dro.getC1()));
			consume.setMtime(sdf.parse(dro.getC1()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		bankService.useBank(dro.getBankid(), accId, dro.getJe(), dro.getDescp());
		long consumeId = consumeDao.saveWithId(consume);
		consumelogDao.add(accId, consumeId, dro.getJe(), 0, "新增消费记录：" + dro.getDescp());

		budgetService.useBudget(accId, consumeId, dro);
		return JSONReturn.buildSuccess("插入成功");
	}

	@Transactional
	public JSONReturn saveConsume(ConsumeDro dro, long accId) {
		ValidBean vb = bankService.canUseBank(dro.getBankid(), dro.getJe(), 0);
		if (!vb.isSuccess()) {
			return JSONReturn.buildFailure(vb.getReason());
		}
		vb = budgetService.canUseBudget(dro.getBudgetid(), dro.getJe());
		if (!vb.isSuccess()) {
			return JSONReturn.buildFailure(vb.getReason());
		}

		SsConsume consume = new ConsumeConvert().dro2Entity(dro);
		consume.setMtime(DateTimeUtil.getCurrentTime());
		consume.setCtime(DateTimeUtil.getCurrentTime());

		bankService.useBank(dro.getBankid(), accId, dro.getJe(), dro.getDescp());
		long consumeId = consumeDao.saveWithId(consume);
		consumelogDao.add(accId, consumeId, dro.getJe(), 0, "新增消费记录：" + dro.getDescp());

		budgetService.useBudget(accId, consumeId, dro);
		return JSONReturn.buildSuccess("插入成功");
	}

	public JSONReturn getConsumeLog(long consumeId) {
		ArrayList<SsConsumelog> entityList = (ArrayList<SsConsumelog>) consumelogDao.findByProperty(SsConsumelogField.CONSUMEID, consumeId);
		return JSONReturn.buildSuccess(new ConsumelogConvert().entityList2ToList(entityList));
	}

}
