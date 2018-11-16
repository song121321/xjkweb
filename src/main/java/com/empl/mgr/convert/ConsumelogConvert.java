package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dto.AccountListDto;
import com.empl.mgr.dto.ConsumelogDto;
import com.empl.mgr.model.SsConsumelog;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.StringUtil;

@Component
public class ConsumelogConvert {
	@Autowired
	private AccountDao accountDao;
	public static ConsumelogConvert mySelf;

	@PostConstruct
	public void init() {
		mySelf = this;
	}

	public ConsumelogDto entity2Dto(SsConsumelog entity) {
		ConsumelogDto dto = new ConsumelogDto();
		dto.setId(entity.getId());
		AccountListDto account = new AccountConvert().entity2To(mySelf.accountDao.findById((long) entity.getAccountid()));
		dto.setAccountid(account.getId());
		dto.setAccount_nickname(account.getNickname());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity.getDescp());
		dto.setJe(entity.getJe());
		dto.setStatus(entity.getStatus());
		dto.setCtime(DateTimeUtil.conversionTime(entity.getCtime(), FormatConstant.DATETIMEFORMAT));
		dto.setCustomerId(entity.getConsumeid());
		return dto;
	}

	public List<ConsumelogDto> entityList2ToList(List<SsConsumelog> entityList) {
		List<ConsumelogDto> dtoList = new ArrayList<ConsumelogDto>();
		for (SsConsumelog entity : entityList) {
			dtoList.add(entity2Dto(entity));
		}
		return dtoList;
	}

}
