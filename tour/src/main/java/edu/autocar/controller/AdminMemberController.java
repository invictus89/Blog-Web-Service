package edu.autocar.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.domain.Member;
import edu.autocar.domain.PageInfo;
import edu.autocar.domain.ResultMsg;
import edu.autocar.domain.UserLevel;
import edu.autocar.service.MemberService;
import edu.autocar.util.ResponseMsg;
import lombok.extern.slf4j.Slf4j;

/**
 * @FileName : AdminMemberController.java
 *
 * 관리자로 로그인 후 회원 관리 / 게시글 관리 / 블로그 관리 요청을 처리하는 컨트롤러
 * @author 백상우
 * @Date : 2019. 6. 5. 
 */
@Controller
@RequestMapping("/admin/member")
@Slf4j
public class AdminMemberController {
	@Autowired
	MemberService service;
	
	@GetMapping("/list")
	public void list(@RequestParam(value = "page", defaultValue = "1") int page, 
					Model model) throws Exception {
		PageInfo<Member> pi = service.getPage(page);
		model.addAttribute("pi", pi);
	}
	
	@GetMapping("/create")
	public void getCreate(Member member, Model model) throws Exception {
		model.addAttribute("userLevels", UserLevel.values());		
	}

	@PostMapping("/create")
	public String postCreate(@Valid Member member, BindingResult result,
							Model model) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("userLevels", UserLevel.values());		
			return "admin/member/create";
		}
		service.create(member);
		return "redirect:list";
	}
	
	@GetMapping("/view/{userId}")
	public String view(@PathVariable String userId, Model model) throws Exception {
		Member member = service.getMember(userId);
		model.addAttribute("member", member);		
		return "admin/member/view";
	}

	@GetMapping("/edit/{userId}")
	public String getEdit(@PathVariable String userId, Model model) throws Exception {
		Member member = service.getMember(userId);
		model.addAttribute("member", member);
		model.addAttribute("userLevels", UserLevel.values());
		return "admin/member/edit";
	}

	@PostMapping("/edit/{userId}")
	public String postEdit(@RequestParam(value = "page") int page,
						@Valid Member member, BindingResult result,
						Model model) throws Exception {

		model.addAttribute("userLevels", UserLevel.values());
		if (result.hasErrors()) {
			return "admin/member/edit";
		}

		if (service.updateByAdmin(member)) {
			return "redirect:/admin/member/view/" + member.getUserId() + "?page=" + page;
		} else {
			FieldError fieldError = new FieldError("member", "password", "비밀번호가 일치하지 않습니다");
			result.addError(fieldError);
			return "admin/member/edit";
		}
	}

	@GetMapping("/id-check/{userId}")
	@ResponseBody
	public ResponseEntity<ResultMsg> checkId(@PathVariable String userId)
			throws Exception {

		if (service.getMember(userId)==null) {
			return ResultMsg.response("ok", "사용가능한 ID 입니다.");
		} else {
			return ResultMsg.response("duplicate", "이미 사용중인 ID 입니다.");
		}
	}
	
	@DeleteMapping("/multi_delete")
	@ResponseBody
	public ResponseEntity<ResultMsg> multiDelete(
			@RequestParam(value="users[]") String[] users, 
			@RequestParam(value="password") String password)
			throws Exception {

		for(String userId : users) {
			System.out.println(userId);
		}
		
		return ResultMsg.response("success", "삭제했습니다.");
		
//		if(service.multiDelete(users, password)) {
//			return ResultMsg.response("success", "삭제했습니다.");
//		} else {
//			return ResultMsg.response("fail", "비밀번호가 일치하지 않습니다.");
//		}
	}
	
	@DeleteMapping("/delete/{userId}")
	@ResponseBody
	public ResponseEntity<ResultMsg> delete(@PathVariable String userId, @RequestParam(value = "password") String password)
			throws Exception {

		if(service.delete(userId, password)) {
			return ResultMsg.response("success", "삭제했습니다.");
		} else {
			return ResultMsg.response("fail", "비밀번호가 일치하지 않습니다.");
		}
	}
}
