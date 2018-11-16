package com.empl.mgr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empl.mgr.annotation.SecureValid;
import com.empl.mgr.constant.MethodType;
import com.empl.mgr.controller.support.AbstractController;
import com.empl.mgr.service.ComsumeTypeService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "consume/consume-type")
public class ConsumeTypeController extends AbstractController {
	@Autowired
	private ComsumeTypeService comsumeTypeService;

	@ResponseBody
	@RequestMapping(value = "getConsumeType")
	@SecureValid(code = "05002", desc = "获取所有消费类型记录", type = MethodType.FIND)
	public JSONReturn findConsumeByid() {
		JSONReturn jr = comsumeTypeService.getConsumeType();
		return jr;
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	@SecureValid(code = "05002", desc = "根据类型名称模糊获取消费类型记录", type = MethodType.FIND)
	public JSONReturn get(String typeName) {
		JSONReturn jr = comsumeTypeService.getByName(typeName);
		return jr;
	}
	

	@ResponseBody
	@RequestMapping(value = "addConsumeType")
	@SecureValid(code = "05002", desc = "添加消费类型", type = MethodType.ADD)
	public JSONReturn addPosition(@RequestParam String desp,
			@RequestParam String detail, HttpSession httpSession) {
		return comsumeTypeService.addConsumeType(desp, detail,
				acctID(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "deleteConsumeType")
	@SecureValid(code = "03002", desc = "删除职位", type = MethodType.DELETE)
	public JSONReturn deleteConsumeType(@RequestParam long id,
			HttpSession httpSession) {
		return comsumeTypeService.deleteConsumeType(id);
	}

}
