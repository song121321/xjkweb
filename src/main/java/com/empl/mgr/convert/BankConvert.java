package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dao.BankDao;
import com.empl.mgr.dro.BankDro;
import com.empl.mgr.dto.BankDto;
import com.empl.mgr.model.SsBank;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.StringUtil;

@Component
public class BankConvert {

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private BankDao bankDao;
	public static BankConvert mySelf;

	@PostConstruct
	public void init() {
		mySelf = this;
	}

	public BankDto entity2To(SsBank entity) {
		BankDto dto = new BankDto();
		dto.setId(entity.getId() < 0 ? 0 : entity.getId());
		dto.setAccount(new AccountConvert().entity2To(mySelf.accountDao
				.findById((long) entity.getAccountid())));
		dto.setJe(entity.getJe());
		SsBank fromBank = mySelf.bankDao.findById(entity.getFromBankId());
		if (null == fromBank) {
			dto.setFromBankId(0);
			dto.setFromBankName("--");
		} else {
			dto.setFromBankId(fromBank.getId());
			dto.setFromBankName(fromBank.getDescp());
		}

		dto.setAccounttype(entity.getAccounttype());
		dto.setStatus(entity.getStatus());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity
				.getDescp());
		dto.setC1(StringUtil.empty(entity.getC1()) ? "" : entity.getC1());
		dto.setC2(StringUtil.empty(entity.getC2()) ? "" : entity.getC2());
		dto.setCtime(DateTimeUtil.conversionTime(entity.getCtime(),
				FormatConstant.DATETIMEFORMAT));
		dto.setMtime(DateTimeUtil.conversionTime(entity.getMtime(),
				FormatConstant.DATETIMEFORMAT));
		dto.setD1(DateTimeUtil.conversionTime(entity.getD1(),
				FormatConstant.DATETIMEFORMAT));
		dto.setD2(DateTimeUtil.conversionTime(entity.getD2(),
				FormatConstant.DATETIMEFORMAT));
		dto.setN1(entity.getN1());
		dto.setN2(entity.getN2());
		dto.setDetail(StringUtil.empty(entity.getDetail()) ? "" : entity
				.getDetail());
		return dto;
	}

	public SsBank dro2Entity(BankDro dro) {
		SsBank entity = new SsBank();
		entity.setId(dro.getId() < 0 ? 0 : dro.getId());
		entity.setAccountid(dro.getAccountid());
		entity.setJe(dro.getJe());
		entity.setFromBankId(dro.getFromBankId());
		entity.setAccounttype(dro.getAccounttype());
		entity.setStatus(dro.getStatus());
		entity.setDescp(StringUtil.empty(dro.getDescp()) ? "" : dro.getDescp());
		entity.setC1(StringUtil.empty(dro.getC1()) ? "" : dro.getC1());
		entity.setC2(StringUtil.empty(dro.getC2()) ? "" : dro.getC2());
		entity.setN1(dro.getN1());
		entity.setN2(dro.getN2());
		entity.setDetail(StringUtil.empty(dro.getDetail()) ? "" : dro
				.getDetail());
		return entity;

	}

	public List<BankDto> entityList2ToList(List<SsBank> entityList) {
		List<BankDto> dtoList = new ArrayList<BankDto>();
		for (SsBank entity : entityList) {
			dtoList.add(entity2To(entity));
		}
		return dtoList;
	}

	public List<SsBank> toList2EntityList(List<BankDro> droList) {
		List<SsBank> entityList = new ArrayList<SsBank>();
		for (BankDro dro : droList) {
			entityList.add(dro2Entity(dro));
		}
		return entityList;
	}
}
