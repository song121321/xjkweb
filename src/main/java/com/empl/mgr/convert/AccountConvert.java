package com.empl.mgr.convert;

import com.empl.mgr.dto.AccountListDto;
import com.empl.mgr.model.TeAccount;
import com.empl.mgr.utils.StringUtil;

public class AccountConvert {
	public AccountListDto entity2To(TeAccount entity) {
		AccountListDto dto = new AccountListDto();
		dto.setCreator(StringUtil.empty(entity.getCreator()) ? "" : entity.getCreator());
		dto.setId(entity.getAcctId() < 0 ? 0 : entity.getAcctId());
		dto.setInitAccount(entity.getAcctDeleteState());
		dto.setName(StringUtil.empty(entity.getAcctName()) ? "" : entity.getAcctName());
		dto.setNickname(StringUtil.empty(entity.getAcctNickname()) ? "" : entity.getAcctNickname());
		return dto;
	}

	public TeAccount to2Entity(AccountListDto dto) {
		TeAccount entity = new TeAccount();
		entity.setAcctId(dto.getId() < 0 ? 0 : dto.getId());
		entity.setAcctName(StringUtil.empty(dto.getName()) ? "" : dto.getNickname());
		entity.setAcctNickname(StringUtil.empty(dto.getNickname()) ? "" : dto.getNickname());
		return entity;
	}
}
