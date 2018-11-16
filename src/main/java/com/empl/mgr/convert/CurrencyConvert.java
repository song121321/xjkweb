package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dto.CurrencyDto;
import com.empl.mgr.model.SsCurrency;
import com.empl.mgr.dro.CurrencyDro;
import com.empl.mgr.utils.StringUtil;

public class CurrencyConvert {
	public CurrencyDto entity2Dto(SsCurrency entity) {
		CurrencyDto dto = new CurrencyDto();
		dto.setId(entity.getId());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity.getDescp());
		dto.setIcontext(StringUtil.empty(entity.getIcontext()) ? "" : entity.getIcontext());
		dto.setIcon(StringUtil.empty(entity.getIcon()) ? "" : entity.getIcon());
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

	public SsCurrency dro2Entity(CurrencyDro dro) {
		SsCurrency entity = new SsCurrency();
		entity.setId(dro.getId());
		entity.setDescp(StringUtil.empty(dro.getDescp()) ? "" : dro.getDescp());
		entity.setIcontext(StringUtil.empty(dro.getIcontext()) ? "" : dro.getIcontext());
		entity.setIcon(StringUtil.empty(dro.getIcon()) ? "" : dro.getIcon());

		entity.setN1(dro.getN1());
		entity.setN2(dro.getN2());
		entity.setC1(StringUtil.empty(dro.getC1()) ? "" : dro.getC1());
		entity.setC2(StringUtil.empty(dro.getC2()) ? "" : dro.getC2());

		entity.setDetail(StringUtil.empty(dro.getDetail()) ? "" : dro.getDetail());
		return entity;
	}

	public List<CurrencyDto> entityList2ToList(List<SsCurrency> entityList) {
		List<CurrencyDto> dtoList = new ArrayList<CurrencyDto>();
		for (SsCurrency entity : entityList) {
			dtoList.add(entity2Dto(entity));
		}
		return dtoList;
	}

	public List<SsCurrency> toList2EntityList(List<CurrencyDro> droList) {
		List<SsCurrency> entityList = new ArrayList<SsCurrency>();
		for (CurrencyDro dro : droList) {
			entityList.add(dro2Entity(dro));
		}
		return entityList;
	}
}
