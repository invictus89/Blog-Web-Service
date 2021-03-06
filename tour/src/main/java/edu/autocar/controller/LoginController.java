package edu.autocar.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.autocar.domain.LoginInfo;
import edu.autocar.domain.Member;
import edu.autocar.service.MemberService;
import lombok.extern.slf4j.Slf4j;

/**
 * @FileName : LoginController.java
 *
 * 로그인 요청 처리 컨트롤러
 * 
 * @author 백상우
 * @Date : 2019. 3. 2. 
 */
@Controller
@Slf4j
public class LoginController {
	@Autowired
	MemberService service;
	
	/**
	 * 로그인이 필요한 서비스 요청의 경우 페이징 처리 거절 이유와 로그인 후 이동할 페이지 주소를 저장할
	 * 객체를 관리하는 메소드
	 * 
	 * @param loginInfo
	 * @param target (로그인 후 자동 이동할 경로)
	 * @param reason (페이지 요청 시 거절 이유)
	 * @return
	 */
	@GetMapping("/login")
	public String login(LoginInfo loginInfo,
			@ModelAttribute("target") String target,
			@ModelAttribute("reason") String reason) {
		
		loginInfo.setTarget(target);
		loginInfo.setReason(reason);
		
		return "member/login";
		
	}

	
	@PostMapping("/login")
	public String postLogin(
			@Valid LoginInfo loginInfo,
			BindingResult result,
			HttpSession session) throws Exception {
		
		if(result.hasErrors()) return "member/login";
		
		Member member = service.checkPassword(
				loginInfo.getUserId(), loginInfo.getPassword());
		if(member != null) {
			session.setAttribute("USER", member);
			String target = loginInfo.getTarget();
			if(target !=null && ! target .isEmpty()) 
						return "redirect:"+ target;

			if(member.isAdmin()) {	// 관리자 인경우 
				return "redirect:/admin/member/list";
			} else {	// 
				return "redirect:/";	
			}
		} else {
			result.reject("fail", "사용자 ID 또는 비밀번호가 일치하지 않습니다.");
			return "member/login";
		}	
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
