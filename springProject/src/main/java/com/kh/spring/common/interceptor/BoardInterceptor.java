package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BoardInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// preHandler 메소드가 실행되는 시점
		// DispatcherServlet ----------낚ㅇㅋㅎ-------> Controller
		
		// postHandle 메소드가 실행되는 시점
		// DispatcherServlet <----------zz----------- Controller
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser") == null) { // 로그인이 안 된 상태
			
			session.setAttribute("msg", "로그인 먼저 해주세요");
			response.sendRedirect(request.getContextPath());
			
			return false;
		} else { // 로그인 되어있는 상태
			
			return true;
		}

		// return true: Controller 제대로 실행하는
		// return false: Controller 실행 안 하게
	}
}
