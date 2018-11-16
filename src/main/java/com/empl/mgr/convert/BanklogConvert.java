package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dto.AccountListDto;
import com.empl.mgr.dto.BanklogDto;
import com.empl.mgr.model.SsBanklog;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.StringUtil;

@Component
public class BanklogConvert {
	@Autowired
	private AccountDao accountDao;
	public static BanklogConvert mySelf;

	@PostConstruct
	public void init() {
		mySelf = this;
	}

	public BanklogDto entity2Dto(SsBanklog entity) {
		BanklogDto dto = new BanklogDto();
		dto.setId(entity.getId());
		AccountListDto account = new AccountConvert().entity2To(mySelf.accountDao.findById((long) entity.getAccountid()));
		dto.setAccountid(account.getId());
		dto.setAccount_nickname(account.getNickname());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity.getDescp());
		dto.setJe(entity.getJe());
		dto.setStatus(entity.getStatus());
		dto.setCtime(DateTimeUtil.conversionTime(entity.getCtime(), FormatConstant.DATETIMEFORMAT));
		dto.setLeftje(entity.getLeftje());
		dto.setBeforeje(entity.getBeforeje());
		dto.setBankid(entity.getBankid());
		return dto;
	}

	public List<BanklogDto> entityList2ToList(List<SsBanklog> entityList) {
		List<BanklogDto> dtoList = new ArrayList<BanklogDto>();
		for (SsBanklog entity : entityList) {
			dtoList.add(entity2Dto(entity));
		}
		return dtoList;
	}

}
