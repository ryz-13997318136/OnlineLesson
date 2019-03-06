package com.ryz.cn.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CasInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		String url = arg0.getRequestURL().toString();
		System.out.println(url);
		if(url.contains("user/openLogin")||url.contains("user/login")){
			return true;
		}
		Object user = arg0.getSession().getAttribute("user");
		if(user == null){
			System.out.println("当前用户未登录！");
			arg1.sendRedirect("../user/openLogin");
			return false;
		}else{
			
			return true;
		}
	}

}
