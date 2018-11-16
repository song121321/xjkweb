package com.empl.mgr.convert;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.empl.mgr.constant.FormatConstant;
import com.empl.mgr.dao.ConsumeDao;
import com.empl.mgr.dro.BudgetDro;
import com.empl.mgr.dto.BudgetDto;
import com.empl.mgr.model.SsBudget;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.NumberUtil;
import com.empl.mgr.utils.StringUtil;

@Component
public class BudgetConvert {


	public static BudgetConvert mySelf;
	
	@Autowired
	private ConsumeDao consumeDao;

	@PostConstruct
	public void init() {
		mySelf = this;
	}

	public BudgetDto entity2Dto(SsBudget entity) {
		if(null==entity){
			return new BudgetDto();
		}
		BudgetDto dto = new BudgetDto();
		dto.setId(entity.getId());
		dto.setDescp(StringUtil.empty(entity.getDescp()) ? "" : entity.getDescp());
		dto.setJe(NumberUtil.roundDouble2(entity.getJe()));
		dto.setLeftje(NumberUtil.roundDouble2(entity.getLeftje()));
		dto.setUseJe(NumberUtil.roundDouble2(entity.getJe()-entity.getLeftje()));
		int number =mySelf. consumeDao.getConsumeCountsByBudgetId(""+entity.getId());
		dto.setConsumeNumber(number);
		dto.setYear(entity.getYear());
		dto.setMonth(entity.getMonth());
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

	public SsBudget dro2Entity(BudgetDro dro) {
		SsBudget entity = new SsBudget();
		entity.setId(dro.getId());
		entity.setDescp(StringUtil.empty(dro.getDescp()) ? "" : dro.getDescp());
		entity.setJe(NumberUtil.roundDouble2(dro.getJe()));
		entity.setLeftje(NumberUtil.roundDouble2(dro.getLeftje()));
		entity.setYear(dro.getYear());
		entity.setMonth(dro.getMonth());
		entity.setStatus(dro.getStatus());
		entity.setN1(dro.getN1());
		entity.setN2(dro.getN2());
		entity.setC1(StringUtil.empty(dro.getC1()) ? "" : dro.getC1());
		entity.setC2(StringUtil.empty(dro.getC2()) ? "" : dro.getC2());
		entity.setDetail(StringUtil.empty(dro.getDetail()) ? "" : dro.getDetail());
		return entity;
	}

	public List<BudgetDto> entityList2ToList(List<SsBudget> entityList) {
		List<BudgetDto> dtoList = new ArrayList<BudgetDto>();
		for (SsBudget entity : entityList) {
			dtoList.add(entity2Dto(entity));
		}
		return dtoList;
	}

	public List<SsBudget> toList2EntityList(List<BudgetDro> droList) {
		List<SsBudget> entityList = new ArrayList<SsBudget>();
		for (BudgetDro dro : droList) {
			entityList.add(dro2Entity(dro));
		}
		return entityList;
	}

}
