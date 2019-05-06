package edu.autocar.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.autocar.domain.Avata;
import edu.autocar.domain.Member;
import edu.autocar.domain.ResultMsg;
import edu.autocar.service.AvataService;
import edu.autocar.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {
	@Autowired
	MemberService service;
	
	@GetMapping("/join")
	public String getJoin(Member member)  throws Exception {
		return "member/join";
		//return "redirect:/member/join" 와의 차이점을 반드시 알 것!
		//redirect는 서버가 클라이언트에게 다시 해당  url로 다시 요청하라고 전달한다. 따라서 연결이 한 번 끊긴 후 재접속을 시도한다.
		//이후 접속할 시 getmapping("/member/join")을 찾아서 url접속을 시도한다.
		//반면 return "member/join"은 서버는  타일즈 설정에 맞게 member/join.jsp 페이지를 출력하고
		//이를 클라이언트는 알지 못한다. 클라이언트는 단순히 /join으로만 요청한 사실밖에 모른다.
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
	
	@PostMapping("/join")
	public String postJoin(@Valid Member member, BindingResult result,
			RedirectAttributes ra) throws Exception {
		if (result.hasErrors()) {
			return "member/join";
		}
		
		service.create(member);
		
		ra.addFlashAttribute("member", member);	
		return "redirect:/member/join_success";			
	}
	
	@GetMapping("/member/join_success")
	public void joinSuccess(){}

	
	@GetMapping("/member/profile/view")
	public void view(Model model, HttpSession session) throws Exception {
		Member user = (Member)session.getAttribute("USER");
		Member member = service.getMember(user.getUserId());
		model.addAttribute("member", member);
	}

	@GetMapping("/member/profile/edit")
	public void getEdit(Model model, HttpSession session) throws Exception {
		view(model, session);
	}

	@PostMapping("/member/profile/edit")
	public String postEdit(@Valid Member member, BindingResult result,
			HttpSession session)
			throws Exception {
		if (result.hasErrors()) {
			return "member/edit";
		}

		if (service.update(member)) {
			// 수정된 회원 정보로 세션 수정 
			member = service.getMember(member.getUserId());
			session.setAttribute("USER", member);			
			return "redirect:/member/view";
		} else {
			FieldError fieldError = new FieldError("member", "password", "비밀번호가 일치하지 않습니다");
			result.addError(fieldError);
			return "member/edit";
		}
	}
	
	@Autowired
	AvataService avataService;
	
	@GetMapping("/member/avata/{userId}")
	@ResponseBody
	public ResponseEntity<byte[]> getAvata(
						@PathVariable String userId)  {
		byte[] images = null;
		HttpStatus status = HttpStatus.OK;
		
		try {
			Avata avata = avataService.getAvata(userId);
			images = avata.getImage();
		} catch(Exception e) {
			log.info(userId + "아바타 이미지 없음");
			status = HttpStatus.NOT_FOUND;
		}
			
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(images, headers, status);		
	}
}
