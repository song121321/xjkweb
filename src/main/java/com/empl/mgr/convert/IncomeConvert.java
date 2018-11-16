package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dao.BankDao;
import com.empl.mgr.dro.IncomeDro;
import com.empl.mgr.dto.IncomeDto;
import com.empl.mgr.model.SsBank;
import com.empl.mgr.model.SsIncome;
import com.empl.mgr.model.TeAccount;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.StringUtil;

@Component
public class IncomeConvert {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private BankDao bankDao;

	public static IncomeConvert mySelf;

	@PostConstruct
	public void init() {
		mySelf = this;
	}

	public IncomeDto entity2Dto(SsIncome entity) {
		IncomeDto dto = new IncomeDto();
		dto.setId(entity.getId());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity.getDescp());
		TeAccount account =  mySelf.accountDao.findById((long) entity.getAccountid());
		dto.setAccount(null);
		dto.setAccountId(account.getAcctId());
		dto.setAccountName(account.getAcctNickname());
		SsBank bank = mySelf.bankDao.findById((long) entity.getAccountid());
		dto.setBank(null);
		dto.setBankId(bank.getId());
		dto.setBankName(bank.getDescp());
		dto.setJe(entity.getJe());
		dto.setStatus(entity.getStatus());
		dto.setCtime(DateTimeUtil.conversionTime(entity.getCtime(), FormatConstant.DATETIMEFORMAT));
		dto.setMtime(DateTimeUtil.conversionTime(entity.getMtime(), FormatConstant.DATETIMEFORMAT));
		dto.setN1(entity.getN1());
		dto.setN2(entity.getN2());
		dto.setC1(StringUtil.empty(entity.getC1()) ? "" : entity.getC1());
		dto.setC2(StringUtil.empty(entity.getC2()) ? "" : entity.getC2());
		dto.setD1(DateTimeUtil.conversionTime(entity.getD1(), FormatConstant.DATETIMEFORMAT));
		dto.setD2(DateTimeUtil.conversionTime(entity.getD2(), FormatConstant.DATETIMEFORMAT));
		dto.setDetail(StringUtil.empty(entity.getDetail()) ? "" : entity.getDetail());
		return dto;
	}

	public SsIncome dro2Entity(IncomeDro dro) {
		SsIncome entity = new SsIncome();
		entity.setId(dro.getId());
		entity.setDescp(StringUtil.empty(dro.getDescp()) ? "" : dro.getDescp());
		entity.setAccountid(dro.getAccountid());
		entity.setBankid(dro.getBankid());
		entity.setJe(dro.getJe());
		entity.setStatus(dro.getStatus());
		entity.setN1(dro.getN1());
		entity.setN2(dro.getN2());
		entity.setC1(StringUtil.empty(dro.getC1()) ? "" : dro.getC1());
		entity.setC2(StringUtil.empty(dro.getC2()) ? "" : dro.getC2());
		entity.setDetail(StringUtil.empty(dro.getDetail()) ? "" : dro.getDetail());
		return entity;
	}

	public List<IncomeDto> entityList2ToList(List<SsIncome> entityList) {
		List<IncomeDto> dtoList = new ArrayList<IncomeDto>();
		for (SsIncome entity : entityList) {
			dtoList.add(entity2Dto(entity));
		}
		return dtoList;
	}

	public List<SsIncome> toList2EntityList(List<IncomeDro> droList) {
		List<SsIncome> entityList = new ArrayList<SsIncome>();
		for (IncomeDro dro : droList) {
			entityList.add(dro2Entity(dro));
		}
		return entityList;
	}

}
