package com.empl.mgr.controller;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empl.mgr.annotation.SecureValid;
import com.empl.mgr.constant.MethodType;
import com.empl.mgr.controller.support.AbstractController;
import com.empl.mgr.dro.IncomeDro;
import com.empl.mgr.service.IncomeService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "income")
public class IncomeController extends AbstractController {
	@Autowired
	private IncomeService incomeService;

	@ResponseBody
	@RequestMapping(value = "list")
	@SecureValid(code = "07001", desc = "根据月份，bankid等，摘要查找", type = MethodType.FIND)
	public JSONReturn get(@RequestParam String content,
			@RequestParam String accId, @RequestParam String bankId,
			@RequestParam int page, HttpSession httpSession) {
		return incomeService.getIncome(accId, bankId, content, page);
	}

	@ResponseBody
	@RequestMapping(value = "counts")
	@SecureValid(code = "07001", desc = "根据月份，支付账户id,摘要查找符合条件的数目", type = MethodType.FIND)
	public JSONReturn getCounts(@RequestParam String content,
			@RequestParam String accId, @RequestParam String bankId,
			@RequestParam int page, HttpSession httpSession) {
		return incomeService.getIncomeCounts(accId,bankId,content,page);
	}

	@ResponseBody
	@RequestMapping(value = "findIncomeByid")
	@SecureValid(code = "07001", desc = "根据id获取单条收入记录", type = MethodType.FIND)
	public JSONReturn findIncomeByid(@RequestParam long id,
			HttpSession httpSession) {
		return incomeService.findIncomeByid(id, acctID(httpSession));
	}



	@ResponseBody
	@RequestMapping(value = "save")
	@SecureValid(code = "07001", desc = "保存收入记录", type = MethodType.ADD)
	public JSONReturn saveIncome(@RequestParam String data,
			HttpSession httpSession) {
		IncomeDro dro = (IncomeDro) JSONObject.toBean(
				JSONObject.fromObject(data), IncomeDro.class);
		return incomeService.saveIncome(dro, acctID(httpSession));
	}
	

	@ResponseBody
	@RequestMapping(value = "modify")
	@SecureValid(code = "07001", desc = "修改收入记录", type = MethodType.MODIFY)
	public JSONReturn updateIncome(@RequestParam String data,
			HttpSession httpSession) {
		IncomeDro dro = (IncomeDro) JSONObject.toBean(
				JSONObject.fromObject(data), IncomeDro.class);
		return incomeService.updateIncome(dro, acctID(httpSession));
	}



	@ResponseBody
	@RequestMapping(value = "delete")
	@SecureValid(code = "07001", desc = "删除收入记录", type = MethodType.DELETE)
	public JSONReturn removeIncome(@RequestParam long id,
			HttpSession httpSession) {
		return incomeService.removeIncome(id, acctID(httpSession));
	}

}
