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
package com.jcbase.controller.yue;

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
 * 同城爱约
 */
public class YueController extends Controller {

	public void index() {
		render("userInfo.php.htm");
	}

}





