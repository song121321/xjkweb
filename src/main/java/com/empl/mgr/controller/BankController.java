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
import com.empl.mgr.dro.BankDro;
import com.empl.mgr.service.BankService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "bank")
public class BankController extends AbstractController {
	@Autowired
	private BankService bankService;

	@ResponseBody
	@RequestMapping(value = "banks")
	@SecureValid(code = "06001", desc = "获取所有银行卡", type = MethodType.FIND)
	public JSONReturn findBanks(@RequestParam String zhName, HttpSession httpSession) {
		JSONReturn jr = bankService.getNormalBank(zhName, acctID(httpSession));
		return jr;
	}

	@ResponseBody
	@RequestMapping(value = "getBank")
	@SecureValid(code = "06001", desc = "获取指定银行卡", type = MethodType.FIND)
	public JSONReturn findBank(@RequestParam long id, HttpSession httpSession) {
		JSONReturn jr = bankService.getBank(id);
		return jr;
	}

	@ResponseBody
	@RequestMapping(value = "getBankLogByBankId")
	@SecureValid(code = "06001", desc = "根据银行id获取银行流水日志", type = MethodType.FIND)
	public JSONReturn getBankLog(@RequestParam long bankId) {
		return bankService.getBankLog(bankId);
	}

	@ResponseBody
	@RequestMapping(value = "save")
	@SecureValid(code = "06001", desc = "保存银行信息", type = MethodType.ADD)
	public JSONReturn saveBank(@RequestParam String data, HttpSession httpSession) {
		BankDro dro = (BankDro) JSONObject.toBean(JSONObject.fromObject(data), BankDro.class);
		return bankService.saveBank(dro, acctID(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "modify")
	@SecureValid(code = "06001", desc = "修改银行信息", type = MethodType.MODIFY)
	public JSONReturn updateBank(@RequestParam String data, HttpSession httpSession) {
		BankDro dro = (BankDro) JSONObject.toBean(JSONObject.fromObject(data), BankDro.class);
		return bankService.updateBank(dro, acctID(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "delete")
	@SecureValid(code = "06001", desc = "注销银行信息", type = MethodType.MODIFY)
	public JSONReturn deleteBank(@RequestParam long id, HttpSession httpSession) {
		return bankService.deleteBank(id, acctID(httpSession));
	}

}
