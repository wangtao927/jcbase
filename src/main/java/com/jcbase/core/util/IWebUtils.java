/**
 * Copyright (c) 2011-2016, Eason Pan(pylxyhome@vip.qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jcbase.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jcbase.core.model.Condition;
import com.jcbase.core.model.Operators;
import com.jcbase.model.SysUser;
/**
 * 
 * @author eason
 */
public class IWebUtils {
	
	/**
	 * 获取系统用户
	 * @author eason
	 * @param HttpSession
	 * @return
	 */
	public static SysUser getCurrentSysUser(HttpSession  httpSession ) {
		SysUser sysUser=(SysUser)httpSession.getAttribute("sysUser");
		return sysUser;
	}
	/**
	 * 保存登陆用户
	 * @author eason
	 * @param HttpSession
	 * @param user
	 */
	public static void setCurrentLoginSysUserToSession(HttpSession httpSession ,SysUser sysUser){
		httpSession.setAttribute("sysUser",sysUser);
	}
	public static void setCurrentLoginSysUser(HttpServletResponse response ,HttpSession httpSession,SysUser sysUser){
		String token=RandomUtils.getRandomString(64);
		sysUser.setToken(token);
		sysUser.update();
		CookieUtils.addCookie(response, "token", token, 60*60*24);
		setCurrentLoginSysUserToSession(httpSession, sysUser);
	}
	/**
	 * 获取当前登陆的用户
	 * @author eason
	 * @param request
	 * @return
	 */
	public static SysUser getCurrentSysUser(HttpServletRequest request){
		SysUser sysUser=getCurrentSysUser(request.getSession());
		if(sysUser==null){
			String token=CookieUtils.getCookieByName(request, "token");
			if(CommonUtils.isNotEmpty(token)){
				sysUser=SysUser.me.get(CommonUtils.getConditions(new Condition("token",Operators.EQ,token)));
				setCurrentLoginSysUserToSession(request.getSession(), sysUser);
				return sysUser;
			}
		}
		return sysUser; 
	}
	/**
	 * 清除session中的系统用户
	 * @param HttpSession
	 */
	public static void removeCurrentSysUserFromSession(HttpSession httpSession){
		httpSession.removeAttribute("sysUser");
	}
	
	public static void removeCurrentSysUser(HttpServletRequest request,HttpServletResponse response){
		CookieUtils.delCookie("", request, response);
		removeCurrentSysUserFromSession(request.getSession());
	}
	
}
