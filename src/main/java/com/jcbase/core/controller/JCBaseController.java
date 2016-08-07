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
package com.jcbase.core.controller;

import java.util.LinkedHashMap;

import com.jcbase.core.util.CommonUtils;
import com.jfinal.core.Controller;
import com.jfinal.plugin.druid.DruidPlugin;
/**
 * 
 * @author eason
 *
 */
public abstract class JCBaseController extends Controller {
	public static final int BUFFER_SIZE = 1024 * 1024;
	/**
	 * 获取排序对象
	 * @author eason	
	 * @return
	 */
	protected LinkedHashMap<String,String> getOrderby(){
		String sord=this.getPara("sord");
		String sidx=this.getPara("sidx");
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		if(CommonUtils.isNotEmpty(sidx)){
			orderby.put(sidx, sord);
		}
		return orderby;
	}
	/**
	 * 获取排序字符串
	 * @author eason	
	 * @return
	 */
	protected String getOrderbyStr(){
		String sord=this.getPara("sord");
		String sidx=this.getPara("sidx");
		if(CommonUtils.isNotEmpty(sidx)){
			return " order by "+ sidx+" "+sord;
		}
		return "";
	}
	/**
	 * 获取每几页
	 * @author eason	
	 * @return
	 */
	protected int getPage(){
		return this.getParaToInt("page", 1);
	}
	/**
	 * 获取每页数量
	 * @author eason	
	 * @return
	 */
	protected int getRows(){
		int rows=this.getParaToInt("rows", 20);
		if(rows>1000)rows=1000;
		return rows;
	}
	
}





