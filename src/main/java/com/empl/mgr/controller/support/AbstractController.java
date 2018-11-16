package com.empl.mgr.controller.support;

import javax.servlet.http.HttpSession;

import com.empl.mgr.constant.SessionKey;

public class AbstractController {

	public String acctName(HttpSession httpSession) {
		return (String) httpSession.getAttribute(SessionKey.MODULEACCTNAME);
	}
	
	public Long acctID(HttpSession httpSession) {
		return (Long) httpSession.getAttribute(SessionKey.MODULEACCTID);
	}

}
