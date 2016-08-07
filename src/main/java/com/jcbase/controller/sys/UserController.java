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
package com.jcbase.controller.sys;

import java.util.List;

import com.jcbase.core.auth.anno.RequiresPermissions;
import com.jcbase.core.controller.JCBaseController;
import com.jcbase.core.util.JqGridModelUtils;
import com.jcbase.core.view.InvokeResult;
import com.jcbase.model.SysRole;
import com.jcbase.model.SysUser;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 系统用户管理.
 * @author eason
 */
public class UserController extends JCBaseController {
	
	@RequiresPermissions(value={"/sys/user"}) 
	public void index() {
		render("user_index.jsp");
	}

	@RequiresPermissions(value={"/sys/user"})
	public void getListData() {
		String keyword=this.getPara("name");
		Page<SysUser> pageInfo=SysUser.me.getSysUserPage(getPage(), this.getRows(),keyword,this.getOrderbyStr());
		this.renderJson(JqGridModelUtils.toJqGridView(pageInfo)); 
	}
	

	@RequiresPermissions(value={"/sys/user"})
	public void setVisible(){
		Integer visible=this.getParaToInt("visible");
		String ids=this.getPara("ids");
		InvokeResult result=SysUser.me.setVisible(ids, visible);
		this.renderJson(result);
	}

	@RequiresPermissions(value={"/sys/user"})
	public void add() {
		Integer id=this.getParaToInt("id");
		if(id!=null){
			this.setAttr("item", SysUser.me.findById(id));
		}
		List<SysRole> list=SysRole.me.getSysRoleNamelist();
		this.setAttr("roleList", list);
		this.setAttr("id", id);
		render("user_add.jsp");
	}
	
	@RequiresPermissions(value={"/sys/user"})
	public void save(){
		String username=this.getPara("name");
		String password=this.getPara("password");
		String phone=this.getPara("phone");
		String email=this.getPara("email");
		Integer id=this.getParaToInt("id");
		String des=this.getPara("des");
		InvokeResult result=SysUser.me.save(id, username, password, des,phone, email);
		this.renderJson(result); 
	}
	
	
	@RequiresPermissions(value={"/sys/user"})
	public void userRoleSetting() {
		Integer uid=this.getParaToInt("uid");
		this.setAttr("item", SysUser.me.findById(uid));
		InvokeResult result=SysRole.me.getRoleZtreeViewList(uid);
		this.setAttr("jsonTree", result);
		render("user_role_setting.jsp");
	}
	
	
	@RequiresPermissions(value={"/sys/user"})
	@Before(Tx.class)
	public void saveUserRoles(){
		Integer uid=this.getParaToInt("id");
		String roleIds=this.getPara("roleIds");
		InvokeResult result=SysUser.me.changeUserRoles(uid, roleIds);
		this.renderJson(result);
	}
}





