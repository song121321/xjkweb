package com.empl.mgr.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dto.ConsumeTypeDto;
import com.empl.mgr.field.SsBaseModelField;
import com.empl.mgr.model.SsConsumetype;
import com.empl.mgr.model.TeAccount;

public class ConvertUtil {

	@Autowired
	private AccountDao accountDao;

	public ConsumeTypeDto ConsumeType2Dto(SsConsumetype inObj) {

		ConsumeTypeDto outDto = new ConsumeTypeDto();

		outDto.setId(inObj.getId());

		outDto.setDescp(inObj.getDescp());

		outDto.setCtime(inObj.getCtime() == null ? "" : DateTimeUtil
				.conversionTime(inObj.getCtime(), "yyyy-MM-dd hh:mm:ss"));

		outDto.setMtime(inObj.getMtime() == null ? "" : DateTimeUtil
				.conversionTime(inObj.getMtime(), "yyyy-MM-dd hh:mm:ss"));

		outDto.setN1(inObj.getN1());

		outDto.setN2(inObj.getN2());

		outDto.setC1(inObj.getC1());

		outDto.setC2(inObj.getC2());

		outDto.setD1(inObj.getD1() == null ? "" : DateTimeUtil.conversionTime(
				inObj.getD1(), "yyyy-MM-dd hh:mm:ss"));

		outDto.setD2(inObj.getD1() == null ? "" : DateTimeUtil.conversionTime(
				inObj.getD2(), "yyyy-MM-dd hh:mm:ss"));

		outDto.setDetail(inObj.getDetail());

		TeAccount account = accountDao.findUniqueByProperty(
				SsBaseModelField.ID, (int) inObj.getMuser());
		if (null != account) {
			
			//outDto.setAccountId((int) account.getAcctId());
			
			//outDto.setAccountNickName(account.getAcctNickname());
		}
		return outDto;
	}

}
