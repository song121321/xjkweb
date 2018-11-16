package com.empl.mgr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.convert.BankConvert;
import com.empl.mgr.convert.BanklogConvert;
import com.empl.mgr.dao.BankDao;
import com.empl.mgr.dao.BanklogDao;
import com.empl.mgr.dro.BankDro;
import com.empl.mgr.field.SsBankField;
import com.empl.mgr.model.SsBank;
import com.empl.mgr.model.SsBanklog;
import com.empl.mgr.service.BankService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.support.ValidBean;
import com.empl.mgr.utils.DateTimeUtil;

@Scope
@Service
@Transactional
public class BankServiceImpl implements BankService {

	@Autowired
	private BankDao bankDao;
	@Autowired
	private BanklogDao banklogDao;

	public JSONReturn getBank(long id) {
		return JSONReturn.buildSuccess(new BankConvert().entity2To(bankDao.findById(id)));
	}

	public JSONReturn getBank() {
		List<SsBank> list = bankDao.findAll();
		return JSONReturn.buildSuccess(new BankConvert().entityList2ToList(list));
	}

	public JSONReturn getNormalBank(String zhName, long accId) {
		List<SsBank> list = bankDao.findNormalBank(zhName);
		return JSONReturn.buildSuccess(new BankConvert().entityList2ToList(list));
	}

	public JSONReturn deleteBank(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean useBank(long bankid, long accountid, double thisJe, String desp) {
		try {
			SsBank bank = bankDao.findUniqueByProperty(SsBankField.ID, bankid);
			double beforeJe = bank.getJe();
			double leftJe = beforeJe - thisJe;
			desp = "动账缘由：【" + desp + "】" + "动账" + (thisJe > 0 ? "减少" : "增加") + "金额：【" + thisJe + "】元，动账前金额:【" + beforeJe
					+ "】元,动账后金额：【" + leftJe + "】元.";
			Date now = DateTimeUtil.getCurrentTime();
			bank.setJe(leftJe);
			bank.setMtime(now);

			bankDao.merge(bank);
			SsBanklog bankLog = new SsBanklog();
			bankLog.setAccountid(accountid);
			bankLog.setBankid(bankid);
			bankLog.setCtime(now);
			bankLog.setDescp(desp);
			bankLog.setJe(thisJe);
			bankLog.setLeftje(leftJe);
			bankLog.setStatus(0);
			bankLog.setBeforeje(bankLog.getJe() + bankLog.getLeftje());
			banklogDao.save(bankLog);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param bankid
	 *            bankid
	 * @param je
	 *            动账使用金额
	 * @param type1
	 *            0 消费 1 充值
	 * @return
	 */
	public ValidBean canUseBank(long bankid, double je, long type1) {
		ValidBean vb = new ValidBean();
		SsBank bank = bankDao.findUniqueByProperty(SsBankField.ID, bankid);
		if (bank.getJe() < je) {
			vb.setSuccess(false);
			vb.setReason("支付账户余额不足");
			return vb;
		}
		if (bank.getStatus() == 0) {
			vb.setSuccess(false);
			vb.setReason("支付账户已暂停使用");
		}

		if (bank.getAccounttype() == 1 && type1 == 1) {
			vb.setSuccess(false);
			vb.setReason("信用卡不支持充值");
		}
		vb.setSuccess(true);
		return vb;
	}

	public JSONReturn getBankLog(long bankId) {
		ArrayList<SsBanklog> entityList = (ArrayList<SsBanklog>) banklogDao.findTop100LogByBankId(bankId);
		return JSONReturn.buildSuccess(new BanklogConvert().entityList2ToList(entityList));
	}

	public JSONReturn saveBank(BankDro dro, long accId) {
		SsBank bank = new BankConvert().dro2Entity(dro);
		long formBankId = bank.getFromBankId();
		if(formBankId>0){
			useBank(formBankId, accId, dro.getJe(), "新建资产账户【"+dro.getDescp()+"】，转账扣除。");
		}
		bank.setMtime(DateTimeUtil.getCurrentTime());
		bank.setStatus(1);
		bank.setCtime(DateTimeUtil.getCurrentTime());
		long bankid = bankDao.saveWithId(bank);
		SsBanklog bankLog = new SsBanklog();
		bankLog.setBankid(bankid);
		bankLog.setAccountid(accId);
		bankLog.setDescp("新增支付账号");
		bankLog.setJe(-bank.getJe());
		bankLog.setLeftje(bank.getJe());
		bankLog.setStatus(0);
		bankLog.setCtime(DateTimeUtil.getCurrentTime());
		bankLog.setBeforeje(bankLog.getJe() + bankLog.getLeftje());
		banklogDao.save(bankLog);
		return JSONReturn.buildSuccess("插入成功");
	}

	public JSONReturn updateBank(BankDro dro, long accId) {

		SsBank oldBank = bankDao.findById(dro.getId());
		SsBank newBank = new BankConvert().dro2Entity(dro);
		oldBank.setMtime(DateTimeUtil.getCurrentTime());
		String oldBankName = oldBank.getDescp();
		String banklogDesc = "【" + oldBankName + "】信息变更,";
		if (oldBank.getAccountid() != newBank.getAccountid()) {
			oldBank.setAccountid(newBank.getAccountid());
		}

		if (oldBank.getAccounttype() != newBank.getAccounttype()) {
			banklogDesc += "原类型【" + getBankType(oldBank.getAccounttype()) + "】变更为【" + getBankType(newBank.getAccounttype()) + "】,";
			oldBank.setAccounttype(newBank.getAccounttype());
		}

		if (oldBank.getDescp() != newBank.getDescp()) {
			banklogDesc += "原名称【" + oldBank.getDescp() + "】变更为【" + newBank.getDescp() + "】,";
			oldBank.setDescp(newBank.getDescp());
		}

		if (oldBank.getDetail() != newBank.getDetail()) {
			banklogDesc += "原详情【" + oldBank.getDetail() + "】变更为【" + newBank.getDetail() + "】,";
			oldBank.setDetail(newBank.getDetail());
		}

		if (oldBank.getJe() != newBank.getJe()) {
			banklogDesc += "原金额【" + oldBank.getJe() + "】变更为【" + newBank.getJe() + "】,";
			oldBank.setJe(newBank.getJe());
		}
		oldBank.setStatus(1);
		bankDao.merge(oldBank);
		SsBanklog bankLog = new SsBanklog();
		bankLog.setBankid(oldBank.getId());
		bankLog.setAccountid(accId);
		bankLog.setDescp(banklogDesc);
		bankLog.setJe(newBank.getJe() - oldBank.getJe());
		bankLog.setLeftje(newBank.getJe());
		bankLog.setStatus(1);
		bankLog.setCtime(DateTimeUtil.getCurrentTime());
		bankLog.setBeforeje(oldBank.getJe());
		banklogDao.save(bankLog);

		return JSONReturn.buildSuccess("更新成功");
	}

	private String getBankType(long id) {
		if (id == 0) {
			return "储蓄型";
		}
		if (id == 0) {
			return "信用型";
		}
		return "";
	}

	public JSONReturn deleteBank(long bankid, long accId) {
		SsBank bank = bankDao.findUniqueByProperty(SsBankField.ID, bankid);
		bank.setStatus(0);
		bank.setMtime(DateTimeUtil.getCurrentTime());
		bankDao.merge(bank);
		SsBanklog bankLog = new SsBanklog();
		bankLog.setBankid(bank.getId());
		bankLog.setAccountid(accId);
		bankLog.setDescp("注销账户");
		bankLog.setJe(0);
		bankLog.setLeftje(bank.getJe());
		bankLog.setStatus(2);
		bankLog.setCtime(DateTimeUtil.getCurrentTime());
		bankLog.setBeforeje(bank.getJe());
		banklogDao.save(bankLog);
		return JSONReturn.buildSuccess(bank.getDescp() + " 已被删除！");
	}

}
