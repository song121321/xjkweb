package com.empl.mgr.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.empl.mgr.model.TeAccount;
import com.empl.mgr.support.JSONReturn;

public abstract interface AccountService {

	/**
	 * @author Ryan
	 * @param name
	 * @param pass
	 * @param request
	 * @return
	 */
	public abstract JSONReturn login(String name, String pass, HttpServletRequest request);

	/**
	 * @author Ryan
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findAccountInfo(String acctName);

	/**
	 * @author Ryan
	 * @param httpSession
	 * @return
	 */
	public abstract JSONReturn exit(HttpSession httpSession);

	/**
	 * @author Ryan
	 * @param user
	 * @param nick
	 * @param pass
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn addAccount(String user, String nick, String pass, String acctName);

	/**
	 * @author Ryan
	 * @param page
	 * @param searchValue
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findAccountList(int page, String searchValue, String acctName);

	/**
	 * @author Ryan
	 * @param page
	 * @param searchValue
	 * @return
	 */
	public abstract JSONReturn findAccountPage(int page, String searchValue);

	/**
	 * @author Ryan
	 * @param id
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn delAccount(long id, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn initPassword(long id, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @param nickname
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn modifyNickname(long id, String nickname, String acctName);

	/**
	 * @author Ryan
	 * @param id
	 * @return
	 */
	public abstract JSONReturn findAccountById(long id);

	/**
	 * @author Ryan
	 * @param id
	 * @param account
	 * @param add
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn addAccountRole(long id, String account, boolean add, String acctName);

	/**
	 * @author Ryan
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn findRole(String acctName);

	/**
	 * @author Ryan
	 * @param userName
	 * @return
	 */
	public abstract TeAccount findAccountByName(String userName);

	/**
	 * @author Ryan
	 * @param password
	 * @param acctName
	 * @return
	 */
	public abstract JSONReturn mdoifyPass(String password, String acctName);

}
