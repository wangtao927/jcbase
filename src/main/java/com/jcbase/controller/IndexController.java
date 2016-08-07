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
package com.jcbase.controller;

import java.io.UnsupportedEncodingException;

import com.jcbase.core.auth.anno.RequiresPermissions;
import com.jcbase.core.auth.interceptor.AuthorityInterceptor;
import com.jcbase.core.auth.interceptor.SysLogInterceptor;
import com.jcbase.core.util.IWebUtils;
import com.jcbase.core.util.MyDigestUtils;
import com.jcbase.core.view.InvokeResult;
import com.jcbase.model.SysUser;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
/**
 * 首页、登陆、登出
 * @author eason
 */
@Clear(SysLogInterceptor.class)
public class IndexController extends Controller {
	
	@RequiresPermissions(value={"/"})
	public void index() {
		render("index.jsp");
	}
	
	@RequiresPermissions(value={"/"})
	public void home() {
		render("home.jsp");
	}
	
	@Clear(AuthorityInterceptor.class)
	public void login() {
		this.setAttr("url", this.getPara("url"));
		render("login.jsp");
	}
	
	public void dologin() {
		InvokeResult result=SysUser.me.login(this.getPara("username"),this.getPara("password")
				,this.getResponse(),this.getSession(),StrKit.notBlank(this.getPara("url"))?this.getPara("url"):"/");
		this.renderJson(result);
	}
	
	public void loginOut() {
		IWebUtils.removeCurrentSysUser(getRequest(), getResponse());
		this.redirect("/login");
	}
	@RequiresPermissions(value={"/"})
	public void pwdSetting(){
		try {
			SysUser sysUser = IWebUtils.getCurrentSysUser(getRequest());
			this.setAttr("sysUser", sysUser);
			render("sys/pwd_setting.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@RequiresPermissions(value={"/"})
	public void savePwdUpdate(){
		String oldPwd=MyDigestUtils.shaDigestForPasswrod(getPara("oldPwd"));
		String newPwd=MyDigestUtils.shaDigestForPasswrod(getPara("newPwd"));
		String pwdRepeat=MyDigestUtils.shaDigestForPasswrod(getPara("pwdRepeat"));
		try {
			SysUser sysUser = IWebUtils.getCurrentSysUser(getRequest());
			if(!sysUser.get("pwd").equals(oldPwd)){
				this.renderJson(InvokeResult.failure(-3, "旧密码不正确"));
			}else{
				if(newPwd.equals(pwdRepeat)){
					InvokeResult result=SysUser.me.savePwdUpdate(sysUser.getInt("id"), newPwd);
					this.renderJson(result);
				}else{ 
					this.renderJson(InvokeResult.failure(-1, "两次密码不一致"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}





