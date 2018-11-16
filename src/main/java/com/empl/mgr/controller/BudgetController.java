package com.empl.mgr.controller;

import java.io.UnsupportedEncodingException;

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
import com.empl.mgr.dro.BudgetDro;
import com.empl.mgr.service.BudgetService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "budget")
public class BudgetController extends AbstractController {

	@Autowired
	private BudgetService budgetService;
	@ResponseBody
	@RequestMapping(value = "getBudgetMonthly")
	@SecureValid(code = "06002", desc = "根据月份获取预算", type = MethodType.FIND)
	public JSONReturn getBudgetMonthly(String month) {
		return budgetService.getBudgetMonthly(month);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "list")
	@SecureValid(code = "06002", desc = "根据时间和名称获取预算，模糊查询.", type = MethodType.FIND)
	public JSONReturn getBudgetByTimeAndDescp(String month,String descp) {
		return budgetService.getBudgetByTimeAndDescp(month,descp);
	}
	
	@ResponseBody
	@RequestMapping(value = "getBudgetById")
	@SecureValid(code = "06002", desc = "根据id获取预算，精确查询.", type = MethodType.FIND)
	public JSONReturn getBudgetById(long id) {
		return budgetService.findBudgetById(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "save")
	@SecureValid(code = "06002", desc = "新增预算", type = MethodType.ADD)
	public JSONReturn save(@RequestParam String data, HttpSession httpSession) {
		BudgetDro dro = (BudgetDro) JSONObject.toBean(JSONObject.fromObject(data), BudgetDro.class);
		return budgetService.save(dro, acctID(httpSession));
	}
	
	@ResponseBody
	@RequestMapping(value = "savewithoutsession")
	@SecureValid(code = "06002", desc = "新增预算", type = MethodType.ADD)
	public JSONReturn save(@RequestParam String data) throws UnsupportedEncodingException {
		data = new String(data.getBytes("ISO-8859-1"),"gbk");
		BudgetDro dro = (BudgetDro) JSONObject.toBean(JSONObject.fromObject(data), BudgetDro.class);
		return budgetService.save(dro, 4);
	}
	
	@ResponseBody
	@RequestMapping(value = "modify")
	@SecureValid(code = "06002", desc = "变更预算", type = MethodType.MODIFY)
	public JSONReturn modify(@RequestParam String data, HttpSession httpSession) {
		BudgetDro dro = (BudgetDro) JSONObject.toBean(JSONObject.fromObject(data), BudgetDro.class);
		return budgetService.update(dro, acctID(httpSession));
	}
	
	@ResponseBody
	@RequestMapping(value = "getBudgetLogByBudgetId")
	@SecureValid(code = "06002", desc = "根据预算id获取预算日志", type = MethodType.FIND)
	public JSONReturn getBudgetLog(@RequestParam long budgetId) {
		return budgetService.getBudgetLog(budgetId);
	}
	
	@ResponseBody
	@RequestMapping(value = "delete")
	@SecureValid(code = "06002", desc = "删除预算信息", type = MethodType.MODIFY)
	public JSONReturn deleteBudget(@RequestParam long id, HttpSession httpSession) {
		return budgetService.removeBudget(id, acctID(httpSession));
	}

}
