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
import com.jcbase.controller.sys.*;
import com.jcbase.controller.yue.YueController;
import com.jfinal.config.Routes;
/**
 * 前端Routes配置
 * @author eason
 *
 */
public class FrontRoutes extends Routes{

	@Override
	public void config() {
		add("/yue", YueController.class,"/WEB-INF/view/yue");
	}

}
