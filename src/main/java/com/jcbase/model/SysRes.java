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
package com.jcbase.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jcbase.core.cache.CacheClearUtils;
import com.jcbase.core.cache.CacheName;
import com.jcbase.core.model.Condition;
import com.jcbase.core.model.Operators;
import com.jcbase.core.view.ZtreeView;
import com.jcbase.model.base.BaseSysRes;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
/**
 * @author eason
 * 系统资源
 */
public class SysRes extends BaseSysRes<SysRes>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7944471090213271965L;
	public static SysRes me = new SysRes();
	
	public Map<String,Object> getTreeGridView(){
		Map<String,Object> rows=new HashMap<String,Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String,String>();
		orderby.put("seq", "asc");
		Set<Condition> conditions=new HashSet<Condition>();
		conditions.add(new Condition("pid",Operators.EQ,""));
		List<SysRes> allTopMenuList=this.getList(1,10000,conditions,orderby); 
		for(SysRes item : allTopMenuList){
			Map<String,Object> mapItem=new HashMap<String,Object>();
			mapItem.put("id", item.getInt("id"));
			mapItem.put("name", item.getStr("name"));
			mapItem.put("pid", item.getInt("pid"));
			mapItem.put("des", item.getStr("des"));
			mapItem.put("url", item.getStr("url"));
			mapItem.put("loaded","true");
			mapItem.put("expanded","true");
			mapItem.put("seq", item.getInt("seq"));
			mapItem.put("isLeaf", item.getInt("type")==1?"false":"true");
			mapItem.put("level", 0);
			mapItem.put("type", item.getInt("type"));
			mapItem.put("enabled", item.getInt("enabled"));
			conditions=new HashSet<Condition>();
			conditions.add(new Condition("pid",Operators.EQ,item.getInt("id")));
			List<SysRes> allSubMenuList=this.getList(1,10000,conditions,orderby);
			if(allSubMenuList.size()==0){
				mapItem.put("isLeaf", "true");
			}else{
				mapItem.put("isLeaf", "false");
			}
			list.add(mapItem);
			for(SysRes subItem : allSubMenuList){
				mapItem=new HashMap<String,Object>();
				mapItem.put("id", subItem.getInt("id"));
				mapItem.put("name", subItem.getStr("name"));
				mapItem.put("pid", subItem.getInt("pid"));
				mapItem.put("des", subItem.getStr("des"));
				mapItem.put("url", subItem.getStr("url"));
				mapItem.put("loaded","true");
				mapItem.put("expanded","true");
				mapItem.put("seq", subItem.getInt("seq"));
				mapItem.put("type", subItem.getInt("type"));
				mapItem.put("level", 1);
				mapItem.put("enabled", subItem.getInt("enabled"));
				conditions=new HashSet<Condition>();
				conditions.add(new Condition("pid",Operators.EQ,subItem.getInt("id")));
				List<SysRes> menuList=this.getList(1,10000,conditions,orderby);
				if(menuList.size()==0){
					mapItem.put("isLeaf", "true");
				}else{
					mapItem.put("isLeaf", "false");
				}
				list.add(mapItem);
				for(SysRes itemlast : menuList){
					mapItem=new HashMap<String,Object>();
					mapItem.put("id", itemlast.getInt("id"));
					mapItem.put("name", itemlast.getStr("name"));
					mapItem.put("pid", itemlast.getInt("pid"));
					mapItem.put("des", itemlast.getStr("des"));
					mapItem.put("url", itemlast.getStr("url"));
					mapItem.put("loaded","true");
					mapItem.put("expanded","true");
					mapItem.put("seq", itemlast.getInt("seq"));
					mapItem.put("isLeaf", "true");
					mapItem.put("type", itemlast.getInt("type"));
					mapItem.put("level", 2);
					mapItem.put("enabled", itemlast.getInt("enabled"));
					list.add(mapItem);
				}
			}
		}
		rows.put("rows", list);
		return rows;
	}
	/**
	 * 获取用户的所有资源
	 * @author eason	
	 * @param uid
	 * @return
	 */
	public Set<String> getSysUserAllResUrl(int uid){
		Set<String> sets=CacheKit.get(CacheName.userMenuCache, "getSysUserAllResUrl_"+uid);
		if(sets==null){
			List<SysRes> list=getSysResList(uid,null);
			 sets=new HashSet<String>();
			for(SysRes item : list){
				sets.add(item.getStr("url"));
			}
			CacheKit.put(CacheName.userMenuCache, "getSysUserAllResUrl_"+uid, sets);
		}
		return sets;
	}
	/**
	 * 获取系统用户资源
	 * @author eason	
	 * @param uid 访问的用户ID
	 * @param uri 当前访问的资源地址
	 * @return
	 */
	public String getSysUserMenuView(int uid, String url) {
			String menuhtml=CacheKit.get(CacheName.userMenuCache, "getSysUserMenuView_"+uid);
			if(StrKit.notBlank(menuhtml)){
				return menuhtml;
			}
			StringBuilder sbBuilder=new StringBuilder("<ul class=\"nav nav-list\">");
			List<SysRes> allMenuList=getSysResList(uid,1);
			Map<Integer,List<SysRes>> subMapList=new HashMap<Integer,List<SysRes>>();
			for(SysRes menu : allMenuList){
				if(menu.getInt("pid")!=null){
					List<SysRes> list=subMapList.get(menu.getInt("pid"));
					if(list==null){
						list=new ArrayList<SysRes>();
					}
					list.add(menu);
					subMapList.put(menu.getInt("pid"), list);
				}
			}
			SysRes selectSysRes=this.getByUrl(url);
			for(SysRes menu : allMenuList){
				if(menu.getInt("pid")==null){
					if(StrKit.notBlank(menu.getStr("url"))&&menu.getStr("url").equals(selectSysRes.getStr("url"))){
						sbBuilder.append("<li class=\"active\">");
					}else if(selectSysRes.getInt("pid")!=null&&selectSysRes.getInt("pid").equals(menu.getInt("id"))){
						sbBuilder.append("<li class=\"active open hsub\">");
					}else{
						sbBuilder.append("<li class=\"\">");
					}
					if(StrKit.notBlank(menu.getStr("url"))&&!menu.getStr("url").equals("#")){
						sbBuilder.append("<a url=\""+menu.getStr("url")+"\" href=\"javascript:;\" data-index=\""+menu.getId()+"\">");
					}else{
						sbBuilder.append("<a href=\"#\" class=\"dropdown-toggle\">");
					}
					sbBuilder.append("<i class=\"menu-icon fa "+menu.getStr("iconCls")+"\"></i>");
					sbBuilder.append("<span class=\"menu-text\">"+menu.getStr("name")+"</span>");
					
					if(StrKit.notBlank(menu.getStr("url"))&&!menu.getStr("url").equals("#")){
						sbBuilder.append("</a>");
					}else{
						sbBuilder.append("<b class=\"arrow fa fa-angle-down\"></b></a>");
					}
					sbBuilder.append("<b class=\"arrow\"></b>");
				}
				List<SysRes> childs=subMapList.get(menu.getInt("id"));
				if(childs!=null&&childs.size()>0){//二级
					sbBuilder.append("<ul class=\"submenu\">");
					for(SysRes subMenu : childs){
						if(selectSysRes!=null&&subMenu.getStr("url").equals(selectSysRes.getStr("url"))){
							sbBuilder.append("<li class=\"active\">");
						}else{
							sbBuilder.append("<li class=\"\">");
						}
						sbBuilder.append("<a url=\""+subMenu.getStr("url")+"\" data-index=\""+menu.getId()+"\" href=\"javascript:;\">");
						sbBuilder.append("<i class=\"menu-icon fa fa-caret-right\"></i>");
						sbBuilder.append(subMenu.getStr("name")+"</a><b class=\"arrow\"></b></li>");
					}
					sbBuilder.append("</ul>");
				}
				
				sbBuilder.append("</li>");
			}
			 sbBuilder.append("</>");
			 CacheKit.put(CacheName.userMenuCache, "getSysUserMenuView_"+uid,sbBuilder.toString());
		return sbBuilder.toString();
	}

	/**
	 * 
	 * @author eason	
	 * @param uid 用户ID
	 * @param type 菜单|功能
	 * @return
	 */
	public List<SysRes> getSysResList(int uid,Integer type){
		List<SysRes> resList=CacheKit.get(CacheName.userMenuCache, "getSysResList_"+uid+"_"+type);
		if(resList==null||resList.size()==0){
			List<SysRole> sysRoleIds=SysRole.me.getSysRoleIdList(uid);
			if(sysRoleIds.size()==0)return null;
			StringBuffer roleIds=new StringBuffer();
			boolean isAdmin=false;
			for(SysRole sysRole : sysRoleIds){
				if(sysRole.getId().equals(1))isAdmin=true;
				roleIds.append(sysRole.getInt("id")).append(",");
			}
				roleIds.deleteCharAt(roleIds.length()-1);
				if(type!=null){
					if(!isAdmin){
						resList= this.find("select * from sys_res where id in(select res_id from sys_role_res where role_id in("+roleIds.toString()+")) and type="+type+" and enabled=1  order by seq asc");
					}else{
						resList= this.find("select * from sys_res where type="+type+" and enabled=1 order by seq asc");
					}
				}else{
					if(!isAdmin){
						resList= this.find("select * from sys_res where id in(select res_id from sys_role_res where role_id in("+roleIds.toString()+")) and enabled=1 order by seq asc");
					}else{
						resList= this.find("select * from sys_res where enabled=1 order by seq asc");
					}
				}
			CacheKit.put(CacheName.userMenuCache, "getSysResList_"+uid+"_"+type, resList);
		}
		return resList;
	}
	public List<SysRes> getTopSysResMenu(List<SysRole> sysRoleIds){
		if(sysRoleIds.size()==0)return null;
		StringBuffer roleIds=new StringBuffer();
		for(SysRole sysRole : sysRoleIds){
			roleIds.append(sysRole.getInt("id")).append(",");
		}
		roleIds.deleteCharAt(roleIds.length()-1);
		return this.paginate(1, 10000, "select *", "from sys_res where id in(select res_id from sys_role_res where role_id in("+roleIds.toString()+")) and pid is null order by seq asc").getList();
	}

	public List<SysRes> getSysResMenuByPid(List<SysRole> sysRoleIds,int pid){
		if(sysRoleIds.size()==0)return null;
		StringBuffer roleIds=new StringBuffer();
		for(SysRole sysRole : sysRoleIds){
			roleIds.append(sysRole.getInt("id")).append(",");
		}
		roleIds.deleteCharAt(roleIds.length()-1);
		return this.paginate(1, 10000, "select *", "from sys_res where id in(select res_id from sys_role_res where role_id in("+roleIds.toString()+")) and pid =? and type=1 order by seq asc",pid).getList();
	}
	
	public SysRes getByUrl(String url){
		return this.findFirst("select * from sys_res where url=?", url);
	}
	public SysRes getById(int id){
		return this.findFirst("select * from sys_res where id=?", id);
	}
	public String getBreadcrumbView(String uri) {
		SysRes curSysMenu=this.getByUrl(uri);
		StringBuilder sbBuilder=new StringBuilder("<ul class=\"breadcrumb\">");
		sbBuilder.append("<li><i class=\"ace-icon fa fa-home home-icon\"></i><a href=\"#\">首页</a></li>");
		if(curSysMenu!=null&&curSysMenu.getInt("pid")!=null){
			SysRes pSysMenu=this.getById(curSysMenu.getInt("pid"));
			if(pSysMenu.getInt("pid")!=null){
				SysRes ppSysMenu=this.getById(pSysMenu.getInt("pid"));
				sbBuilder.append("<li><a href=\"#\">"+ppSysMenu.getStr("name")+"</a></li>");
				sbBuilder.append("<li class=\"active\">"+pSysMenu.getStr("name")+"</li>");
			}else{
				sbBuilder.append("<li><a href=\"#\">"+pSysMenu.getStr("name")+"</a></li>");
				sbBuilder.append("<li class=\"active\">"+curSysMenu.getStr("name")+"</li>");
			}
		}
		if(curSysMenu!=null&&curSysMenu.getInt("pid")==null){
			sbBuilder.append("<li class=\"active\">"+curSysMenu.getStr("name")+"</li>");
		}
		sbBuilder.append("</ul>");
		return sbBuilder.toString();
	}
	
	/**
	 * 获取上级资源
	 * @return
	 */
	public List<ZtreeView> getZtreeViewList() {
		List<ZtreeView> ztreeViews=new ArrayList<ZtreeView>();
		List<SysRes> sysResType=this.getTopList();
		if(sysResType.size()>0){
			int i=1;
			for(SysRes cate:sysResType){
				ztreeViews.add(new ZtreeView(cate.getInt("id"), cate.getInt("pid"), cate.getStr("name"), i==1?true:false));
			}
			i++;
		}
		return ztreeViews;
	}
	
	public List<SysRes> getTopList() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String,String>();
		orderby.put("seq", "asc");
		Set<Condition> conditions=new HashSet<Condition>();
		conditions.add(new Condition("type",Operators.EQ,1));
		List<SysRes> allTopMenuList=this.getList(1,10000,conditions,orderby); 
		return allTopMenuList;
	}
	
	/**
	 * 保存资源
	 * @param id
	 * @param name
	 * @param seq
	 * @param url
	 * @param iconCls
	 * @param type
	 * @param pId
	 * @return
	 */
	public int saveRes(Integer id,String name,String seq,String url,String iconCls,Integer type,Integer pId){
		if(id==null){
			SysRes res=new SysRes();
			res.set("name", name).set("seq", seq).set("url", url).set("iconCls", iconCls).
			set("type", type).set("pid", pId).save();
			Integer resId=res.get("id");//获取刚保存Id
			//保存角色资源权限res_id role_id （角色默认为管理员）
			Record roleRes=new Record().set("res_id", resId).set("role_id", 1);
			Db.save("sys_role_res",roleRes);
			return 1;
		}else{
			SysRes sysRes=SysRes.me.getById(id);
			sysRes.set("name", name).set("seq", seq).set("url", url).set("iconCls", iconCls).
			set("type", type).set("pid", pId).update();
			CacheClearUtils.clearUserMenuCache();
			return 2;
		}
	}
	
	/**
	 * 修改资源启用状态
	 * @param resId
	 * @param status
	 * @return
	 */
	public int setEnabled(Integer resId,Integer status){
		SysRes sysRes=SysRes.me.findById(resId);
		if(sysRes==null){
			return -1;
		}else{
			sysRes.set("enabled", status).update();
			CacheClearUtils.clearUserMenuCache();
			return 0;
		}
	}
	
}
