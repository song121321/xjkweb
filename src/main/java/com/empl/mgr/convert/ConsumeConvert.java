package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dao.BankDao;
import com.empl.mgr.dao.BudgetDao;
import com.empl.mgr.dao.ConsumeTypeDao;
import com.empl.mgr.dao.CurrencyDao;
import com.empl.mgr.dro.ConsumeDro;
import com.empl.mgr.dto.AccountListDto;
import com.empl.mgr.dto.BankDto;
import com.empl.mgr.dto.BudgetDto;
import com.empl.mgr.dto.ConsumeDto;
import com.empl.mgr.dto.ConsumeTypeDto;
import com.empl.mgr.dto.CurrencyDto;
import com.empl.mgr.model.SsConsume;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.StringUtil;

@Component
public class ConsumeConvert {

	@Autowired
	private BudgetDao budgetDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private CurrencyDao currencyDao;
	@Autowired
	private ConsumeTypeDao consumeTypeDao;

	public static ConsumeConvert mySelf;

	@PostConstruct
	public void init() {
		mySelf = this;
	}

	public ConsumeConvert() {
	}

	public ConsumeDto entity2Dto(SsConsume entity) {
		ConsumeDto dto = new ConsumeDto();
		dto.setId(entity.getId());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity
				.getDescp());
		AccountListDto account = new AccountConvert()
				.entity2To(mySelf.accountDao.findById((long) entity
						.getAccountid()));
		dto.setAccount_id(account.getId());
		dto.setAccount_nickname(account.getNickname());
		BankDto bank = new BankConvert().entity2To(mySelf.bankDao
				.findById((long) entity.getBankid()));
		dto.setBank_id(bank.getId());
		dto.setBank_descp(bank.getDescp());
		BudgetDto budget = new BudgetConvert().entity2Dto(mySelf.budgetDao
				.findById((long) entity.getBudgetid()));
		dto.setBudget_id(budget.getId());
		dto.setBudget_descp(budget.getDescp());
		dto.setConsumetype((ConsumeTypeDto) new ConsumetypeConvert()
				.entity2Dto(mySelf.consumeTypeDao.findById(entity
						.getConsumetypeid())));
		CurrencyDto currency = new CurrencyConvert()
				.entity2Dto(mySelf.currencyDao.findById((long) entity
						.getCurrencyid()));
		dto.setCurrency_id(currency.getId());
		dto.setCurrency_icon(currency.getIcon());
		dto.setCurrency_icontext(currency.getIcontext());
		// dto.setAccount(new
		// AccountConvert().entity2To(mySelf.accountDao.findById((long)
		// entity.getAccountid())));
		// dto.setBank((BankDto) new
		// BankConvert().entity2To(mySelf.bankDao.findById((long)
		// entity.getBankid())));
		// dto.setBudget((BudgetDto) new
		// BudgetConvert().entity2Dto(mySelf.budgetDao.findById((long)
		// entity.getBudgetid())));
		// dto.setCurrency((CurrencyDto) new
		// CurrencyConvert().entity2Dto(mySelf.currencyDao.findById((long)
		// entity.getCurrencyid())));

		dto.setJe(entity.getJe());
		dto.setStatus(entity.getStatus());
		dto.setCtime(DateTimeUtil.conversionTime(entity.getCtime(),
				FormatConstant.DATETIMEFORMAT));
		dto.setMtime(DateTimeUtil.conversionTime(entity.getMtime(),
				FormatConstant.DATETIMEFORMAT));
		dto.setN1(entity.getN1());
		dto.setN2(entity.getN2());
		dto.setC1(StringUtil.empty(entity.getC1()) ? "" : entity.getC1());
		dto.setC2(StringUtil.empty(entity.getC2()) ? "" : entity.getC2());
		dto.setD1(DateTimeUtil.conversionTime(entity.getD1(),
				FormatConstant.DATETIMEFORMAT));
		dto.setD2(DateTimeUtil.conversionTime(entity.getD2(),
				FormatConstant.DATETIMEFORMAT));
		dto.setDetail(StringUtil.empty(entity.getDetail()) ? "" : entity
				.getDetail());
		return dto;
	}

	public SsConsume dro2Entity(ConsumeDro dro) {
		SsConsume entity = new SsConsume();
		entity.setId(dro.getId());
		entity.setDescp(StringUtil.empty(dro.getDescp()) ? "" : dro.getDescp());
		entity.setBankid(dro.getBankid());
		entity.setBudgetid(dro.getBudgetid());
		entity.setAccountid(dro.getAccountid());
		entity.setCurrencyid(dro.getCurrencyid());
		entity.setJe(dro.getJe());
		entity.setStatus(dro.getStatus());
		entity.setConsumetypeid(dro.getConsumetypeid());
		entity.setN1(dro.getN1());
		entity.setN2(dro.getN2());
		entity.setC1(StringUtil.empty(dro.getC1()) ? "" : dro.getC1());
		entity.setC2(StringUtil.empty(dro.getC2()) ? "" : dro.getC2());

		entity.setDetail(StringUtil.empty(dro.getDetail()) ? "" : dro
				.getDetail());
		return entity;
	}

	public List<ConsumeDto> entityList2ToList(List<SsConsume> entityList) {
		List<ConsumeDto> dtoList = new ArrayList<ConsumeDto>();
		for (SsConsume entity : entityList) {
			dtoList.add(entity2Dto(entity));
		}
		return dtoList;
	}

	public List<SsConsume> toList2EntityList(List<ConsumeDro> droList) {
		List<SsConsume> entityList = new ArrayList<SsConsume>();
		for (ConsumeDro dro : droList) {
			entityList.add(dro2Entity(dro));
		}
		return entityList;
	}

}
