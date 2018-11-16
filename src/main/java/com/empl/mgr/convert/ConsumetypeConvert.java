package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;

import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dro.ConsumetypeDro;
import com.empl.mgr.dto.ConsumeTypeDto;
import com.empl.mgr.model.SsConsumetype;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.StringUtil;

public class ConsumetypeConvert {
	public ConsumeTypeDto entity2Dto(SsConsumetype entity) {
		ConsumeTypeDto dto = new ConsumeTypeDto();
		dto.setId(entity.getId());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity.getDescp());
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

	public SsConsumetype dro2Entity(ConsumetypeDro dro) {
		SsConsumetype entity = new SsConsumetype();
		entity.setIcon(StringUtil.empty(dro.getIcon()) ? "" : dro.getIcon());

		return entity;
	}

	public List<ConsumeTypeDto> entityList2ToList(List<SsConsumetype> entityList) {
		List<ConsumeTypeDto> dtoList = new ArrayList<ConsumeTypeDto>();
		for (SsConsumetype entity : entityList) {
			dtoList.add(entity2Dto(entity));
		}
		return dtoList;
	}

	public List<SsConsumetype> toList2EntityList(List<ConsumetypeDro> droList) {
		List<SsConsumetype> entityList = new ArrayList<SsConsumetype>();
		for (ConsumetypeDro dro : droList) {
			entityList.add(dro2Entity(dro));
		}
		return entityList;
	}
}
