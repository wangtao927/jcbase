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
package com.jcbase.conf;

import com.jcbase.controller.IndexController;
import com.jcbase.controller.app.AppVersionController;
import com.jcbase.controller.sys.DictController;
import com.jcbase.controller.sys.LogController;
import com.jcbase.controller.sys.ResController;
import com.jcbase.controller.sys.RoleController;
import com.jcbase.controller.sys.UserController;
import com.jfinal.config.Routes;
/**
 * 后台管理Routes配置
 * @author eason
 *
 */
public class AdminRoutes extends Routes{

	@Override
	public void config() {
		add("/", IndexController.class,"/WEB-INF/view");
		add("/sys/log", LogController.class,"/WEB-INF/view/sys");
		add("/sys/res", ResController.class,"/WEB-INF/view/sys");
		add("/sys/user", UserController.class,"/WEB-INF/view/sys");
		add("/sys/role", RoleController.class,"/WEB-INF/view/sys");
		add("/dict", DictController.class,"/WEB-INF/view/sys/dict");
		add("/app", AppVersionController.class,"/WEB-INF/view/app");
	}

}
