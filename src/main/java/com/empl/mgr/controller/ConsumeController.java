package com.empl.mgr.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.empl.mgr.dro.ConsumeDro;
import com.empl.mgr.service.ComsumeService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "consume")
public class ConsumeController extends AbstractController {
	@Autowired
	private ComsumeService comsumeService;

	@ResponseBody
	@RequestMapping(value = "list")
	@SecureValid(code = "05001", desc = "根据月份，预算id,bankid等，摘要查找", type = MethodType.FIND)
	public JSONReturn get(@RequestParam String content,
			@RequestParam String budgetId, @RequestParam String month,
			@RequestParam String consumeTypeId, @RequestParam String bankId,
			@RequestParam int page, HttpSession httpSession) {
		return comsumeService.getConsume(month, budgetId,  consumeTypeId, bankId,content, page);
	}

	@ResponseBody
	@RequestMapping(value = "counts")
	@SecureValid(code = "05001", desc = "根据月份，预算id,摘要查找符合条件的数目", type = MethodType.FIND)
	public JSONReturn getCounts(@RequestParam String content,
			@RequestParam String budgetId, @RequestParam String month,
			@RequestParam int page, HttpSession httpSession) {
		return comsumeService.getConsumeCounts(month, budgetId, content, page);
	}

	@ResponseBody
	@RequestMapping(value = "findConsumeByid")
	@SecureValid(code = "05001", desc = "根据id获取单条消费记录", type = MethodType.FIND)
	public JSONReturn findConsumeByid(@RequestParam long id,
			HttpSession httpSession) {
		return comsumeService.findConsumeByid(id, acctID(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "getComsumeByDate")
	@SecureValid(code = "05001", desc = "根据时间段获取消费记录", type = MethodType.FIND)
	public JSONReturn getComsumeByDate(@RequestParam String d1,
			@RequestParam String d2) {
		Date date1 = null, date2 = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			date1 = dateFormat.parse(d1);
			date2 = dateFormat.parse(d2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return comsumeService.getConsume(date1, date2);
	}

	@ResponseBody
	@RequestMapping(value = "getConsumeMonthly")
	@SecureValid(code = "05001", desc = "根据月份获取消费记录", type = MethodType.FIND)
	public JSONReturn getConsumeMonthly(@RequestParam String month) {
		return comsumeService.getConsumeMonthly(month);
	}

	@ResponseBody
	@RequestMapping(value = "test")
	@SecureValid(code = "05003", desc = "测试", type = MethodType.FIND)
	public JSONReturn test() {
		return comsumeService.Test();
	}

	@ResponseBody
	@RequestMapping(value = "save")
	@SecureValid(code = "05001", desc = "保存消费记录", type = MethodType.ADD)
	public JSONReturn saveConsume(@RequestParam String data,
			HttpSession httpSession) {
		ConsumeDro dro = (ConsumeDro) JSONObject.toBean(
				JSONObject.fromObject(data), ConsumeDro.class);
		return comsumeService.saveConsume(dro, acctID(httpSession));
	}
	
	@ResponseBody
	@RequestMapping(value = "saveForImport")
	@SecureValid(code = "05001", desc = "保存消费记录", type = MethodType.ADD)
	public JSONReturn saveForImport(@RequestParam String data,
			HttpSession httpSession) {
		try {
			data = new String(data.getBytes("ISO-8859-1"),"gbk");
		} catch (Exception e) {
		}
		
		ConsumeDro dro = (ConsumeDro) JSONObject.toBean(
				JSONObject.fromObject(data), ConsumeDro.class);
		return comsumeService.saveForImport(dro, acctID(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "modify")
	@SecureValid(code = "05001", desc = "修改消费记录", type = MethodType.MODIFY)
	public JSONReturn updateConsume(@RequestParam String data,
			HttpSession httpSession) {
		ConsumeDro dro = (ConsumeDro) JSONObject.toBean(
				JSONObject.fromObject(data), ConsumeDro.class);
		return comsumeService.updateConsume(dro, acctID(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "getConsumeLogByConsumeId")
	@SecureValid(code = "05001", desc = "根据消费记录id获取消费日志", type = MethodType.FIND)
	public JSONReturn getConsumeLog(@RequestParam long consumeId) {
		return comsumeService.getConsumeLog(consumeId);
	}

	@ResponseBody
	@RequestMapping(value = "delete")
	@SecureValid(code = "05001", desc = "删除消费记录", type = MethodType.DELETE)
	public JSONReturn removeConsume(@RequestParam long id,
			HttpSession httpSession) {
		return comsumeService.removeConsume(id, acctID(httpSession));
	}

}
