package edu.autocar.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
/**
 * @FileName : LoginInterceptor.java
 * 매 페이지 접근 시 로그인 필요 여부 체크를 위한 인터셉터 설정
 * @author 백상우
 * @Date : 2019. 3. 2. 
 */
@Component
public class LoginInterceptor extends BaseInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//System.out.println("로그인 prehandler가 실행됩니다.");
		HttpSession session = request.getSession();
		if (session.getAttribute("USER") == null) {
			System.out.println("로그인이 필요한 서비스입니다.");
			redirect(request, response, "/login", "로그인이 필요한 서비스입니다.");
			return false;
		}
		return super.preHandle(request, response, handler);
	}
}
