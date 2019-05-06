package edu.autocar.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import edu.autocar.domain.Member;

@Component
public class AdminInterceptor extends BaseInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("USER");
		
		if(member == null || !member.isAdmin()) {
			redirect(request, response, "/login", "관리자 권한이 필요합니다.");
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}
}