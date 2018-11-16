package com.empl.mgr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.convert.IncomeConvert;
import com.empl.mgr.dao.BankDao;
import com.empl.mgr.dao.IncomeDao;
import com.empl.mgr.dro.IncomeDro;
import com.empl.mgr.dto.IncomeDto;
import com.empl.mgr.field.SsConsumeField;
import com.empl.mgr.field.SsIncomeField;
import com.empl.mgr.model.SsBank;
import com.empl.mgr.model.SsIncome;
import com.empl.mgr.service.BankService;
import com.empl.mgr.service.IncomeService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.PageUtils;

@Scope
@Service
@Transactional(readOnly = false)
public class IncomeServiceImpl implements IncomeService {
	@Autowired
	private IncomeDao incomeDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private BankService bankService;

	public JSONReturn getIncome(String accId, String bankId, String content,
			int page) {
		return JSONReturn
				.buildSuccess((ArrayList<IncomeDto>) new IncomeConvert()
						.entityList2ToList(incomeDao.getIncome(accId, bankId,
								content, page)));
	}

	public JSONReturn getIncomeCounts(String accId, String bankId,
			String content, int page) {
		int count = incomeDao.getIncomeCounts(accId, bankId, content, page);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count,
				PageConstant.PAGE_LIST));
	}

	public JSONReturn removeIncome(long id, long accId) {
		SsIncome income = incomeDao.findUniqueByProperty(SsConsumeField.ID, id);
		bankService.useBank(income.getBankid(), accId, income.getJe(), "收入【"
				+ income.getDescp() + "】被删除，退款处理。");
		income.setStatus(2);
		incomeDao.merge(income);
		return JSONReturn.buildSuccess(income.getDescp() + " 已被删除！");
	}


	@Transactional
	public JSONReturn saveIncome(IncomeDro dro, long accId) {
		SsIncome income = new IncomeConvert().dro2Entity(dro);
		income.setMtime(DateTimeUtil.getCurrentTime());
		income.setCtime(DateTimeUtil.getCurrentTime());
		bankService
				.useBank(dro.getBankid(), accId, -dro.getJe(),"收入【"
						+ dro.getDescp() + "】。" );
		incomeDao.save(income);
		return JSONReturn.buildSuccess("插入成功");
	}
	



	@Transactional
	public JSONReturn updateIncome(IncomeDro dro, long accId) {
		boolean jeChange = false;
		SsIncome oldIncome = incomeDao.findById(dro.getId());
		SsIncome newIncome = new IncomeConvert().dro2Entity(dro);
		SsBank oldBank = bankDao.findById(oldIncome.getBankid());
		SsBank newBank = bankDao.findById(newIncome.getBankid());
		if (oldIncome.equals(newIncome)) {
			return JSONReturn.buildFailure("未发现改变，无需更改");
		}
		String oldConsumeDesc = oldIncome.getDescp();
		if (!oldIncome.getDescp().equals(newIncome.getDescp())) {
			oldIncome.setDescp(newIncome.getDescp());
		}
		if (!oldIncome.getDetail().equals(newIncome.getDetail())) {
			oldIncome.setDetail(newIncome.getDetail());
		}
		if (oldIncome.getJe() != newIncome.getJe()) {
			changeBank(dro, oldBank, newBank, accId, oldIncome.getJe(),
					oldConsumeDesc + "退账处理 ");
			jeChange = true;
			oldIncome.setJe(newIncome.getJe());
			if (oldIncome.getBankid() != newIncome.getBankid()) {
				oldIncome.setBankid(newIncome.getBankid());
			}
		}
	
		if (!jeChange && oldIncome.getBankid() != newIncome.getBankid()) {
			changeBank(dro, oldBank, newBank, accId, oldIncome.getJe(),
					oldConsumeDesc + "退账处理 ");
			oldIncome.setBankid(newIncome.getBankid());
		}
		oldIncome.setMtime(DateTimeUtil.getCurrentTime());
		oldIncome.setAccountid(accId);
		incomeDao.merge(oldIncome);
		return JSONReturn.buildSuccess("修改收入记录成功");

	}

	/**
	 * 旧的账户应该减去金额（+），新的应该加上金额（-）
	 * @param dro
	 * @param oldBank
	 * @param newBank
	 * @param accId
	 * @param backJe
	 * @param backReason
	 */
	private void changeBank(IncomeDro dro, SsBank oldBank, SsBank newBank,
			long accId, double backJe, String backReason) {
		bankService.useBank(oldBank.getId(), accId, backJe, backReason);
		bankService
				.useBank(newBank.getId(), accId, -dro.getJe(), dro.getDescp());
	}


	public JSONReturn findIncomeByid(long id, long accId) {
		SsIncome entity = incomeDao.findUniqueByProperty(SsIncomeField.ID, id);
		return JSONReturn.buildSuccess(new IncomeConvert().entity2Dto(entity));
	}

	//
	@Scheduled(cron="0/5 * * * * ? ")
	public void mrLiuMoney() {
		IncomeDro dro = new IncomeDro();
		dro.setBankid(1);
		dro.setAccountid(1);
		dro.setDescp(DateTimeUtil.getCurrentTime().getSeconds()+"月刘老师发钱。");
		dro.setJe(2080);
		dro.setStatus(0);
		saveIncome(dro, 1);
		System.out.println(DateTimeUtil.getCurrentTime().getMinutes()+"ahahah--"+DateTimeUtil.getCurrentTime().getSeconds());
	}

}
