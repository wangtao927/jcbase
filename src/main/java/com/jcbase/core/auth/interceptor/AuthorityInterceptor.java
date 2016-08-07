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
package com.jcbase.core.auth.interceptor;

import com.jcbase.core.auth.anno.RequiresPermissions;
import com.jcbase.core.auth.common.Logical;
import com.jcbase.core.util.IWebUtils;
import com.jcbase.model.SysUser;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;

/**
 * 权限拦截器
 */
public class AuthorityInterceptor implements Interceptor {
	
	public void intercept(Invocation inv) {
		RequiresPermissions requiresPermissions=inv.getMethod().getAnnotation(RequiresPermissions.class);
		boolean hasPermissions=true; 
		if(requiresPermissions==null){
			requiresPermissions=inv.getController().getClass().getAnnotation(RequiresPermissions.class);
		}
		if(requiresPermissions!=null){
			try {
				SysUser sysUser=IWebUtils.getCurrentSysUser(inv.getController().getRequest());
				if(sysUser==null){//没有登陆
						hasPermissions=false;
						if(StrKit.notBlank(inv.getActionKey())&&!inv.getActionKey().equals("/")){
							inv.getController().redirect("/login?url="+inv.getActionKey());
						}else{
							inv.getController().redirect("/login");
						}
					}else{
					//判断是否有该资源的访问权限
						String[] values=requiresPermissions.value();
						if(values.length==1){
							if(StrKit.isBlank(values[0])){
								values[0]=inv.getActionKey();
							}
							if(!sysUser.getPermissionSets().contains(values[0])){//没有权限
								hasPermissions=false;
								inv.getController().renderJson("没有权限");
							}
						}else{
							if(requiresPermissions.logical().equals(Logical.AND)){
								for(String value : values){
										if(!sysUser.getPermissionSets().contains(value)){
											hasPermissions=false;
											break;
										}
								}
							}else{
								hasPermissions=false;
								for(String value : values){
									if(sysUser.getPermissionSets().contains(value)){
										hasPermissions=true;
										break;
									}
								}
							}
							if(!hasPermissions){
								inv.getController().renderJson("没有权限,请联系管理员");
							}
						}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(hasPermissions){
			inv.invoke();
		}
	}
}
