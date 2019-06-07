package edu.autocar.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @FileName : BaseInterceptor.java
 * flash 를 통한 로그인된 회원 정보 세션 관리를 위한 인터셉터 기본 설정
 * @author 백상우
 * @Date : 2019. 3. 2. 
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	ServletContext context;

	public void redirect(HttpServletRequest request, HttpServletResponse response, 
						String target, String reason) throws Exception {
		String url = getUrl(request);
		saveFlash(request, response, url, reason);
		response.sendRedirect(context.getContextPath() + target);
	}
	
	String getUrl(HttpServletRequest request) {
		String url = request.getRequestURI().substring(context.getContextPath().length());
		String query = request.getQueryString();
		if (query != null) {
			url = url + "?" + query;
		}
		return url;
	}
	
	void saveFlash(HttpServletRequest request, 
			HttpServletResponse response, 
			String target, String reason) {

		FlashMap flashMap = new FlashMap();
		flashMap.put("target", target);
		flashMap.put("reason", reason);
		FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
		flashMapManager.saveOutputFlashMap(flashMap, request, response);	
	}
	
}