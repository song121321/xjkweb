package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dto.AccountListDto;
import com.empl.mgr.dto.BudgetlogDto;
import com.empl.mgr.model.SsBudgetlog;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.StringUtil;

@Component
public class BudgetlogConvert {
	@Autowired
	private AccountDao accountDao;
	public static BudgetlogConvert mySelf;

	@PostConstruct
	public void init() {
		mySelf = this;
	}

	public BudgetlogDto entity2Dto(SsBudgetlog entity) {
		BudgetlogDto dto = new BudgetlogDto();
		dto.setId(entity.getId());
		AccountListDto account = new AccountConvert().entity2To(mySelf.accountDao.findById((long) entity.getAccountid()));
		dto.setAccountid(account.getId());
		dto.setAccount_nickname(account.getNickname());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity.getDescp());
		dto.setJe(entity.getJe());
		dto.setStatus(entity.getStatus());
		dto.setCtime(DateTimeUtil.conversionTime(entity.getCtime(), FormatConstant.DATETIMEFORMAT));
		dto.setLeftje(entity.getLeftje());
		dto.setConsumeid(entity.getConsumeid());
		dto.setBudgetid(entity.getBudgetid());
		return dto;
	}

	public List<BudgetlogDto> entityList2ToList(List<SsBudgetlog> entityList) {
		List<BudgetlogDto> dtoList = new ArrayList<BudgetlogDto>();
		for (SsBudgetlog entity : entityList) {
			dtoList.add(entity2Dto(entity));
		}
		return dtoList;
	}

}
