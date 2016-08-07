package com.jcbase.core.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;

/**
 * 资源地址初始化
 */
public class ResourceHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		// 统一设置basePath
		ServletContext context = request.getServletContext();
		if (context.getAttribute("res_url") == null) {
			context.setAttribute("static_url", PropKit.get("static_url"));
			context.setAttribute("res_url", PropKit.get("res_url"));
		}
		next.handle(target, request, response, isHandled);
	}

}
