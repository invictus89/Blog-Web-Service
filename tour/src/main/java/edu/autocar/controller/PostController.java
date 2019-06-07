package edu.autocar.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.autocar.domain.Post;
import edu.autocar.domain.Member;
import edu.autocar.domain.PageInfo;
import edu.autocar.service.PostService;

/**
 * @FileName : PostController.java
 * 
 * 특정 회원의 블로그 리스트 출력 / 블로그 작성 / 
 * @author 백상우
 * @Date : 2019. 3. 14. 
 */
@Controller
@RequestMapping("/blogs/{blogUser}")
public class PostController {

	@Autowired
	PostService service;

	// 테스트를 위한 로그인 자동화 메소드
	@ModelAttribute("USER")
	public Member testUser(HttpSession session) {
		Member m = (Member) session.getAttribute("USER");
		if (m == null) {
			m = new Member();
			m.setUserId("hong2");
			session.setAttribute("USER", m);
		}
		return m;
	}
	
	// 로그인 된 회원의 블로그 리스트 출력 요청을 처리하는 메소드
	@GetMapping("/list")
	public String list(@PathVariable String blogUser, @RequestParam(value = "page", defaultValue = "1") int page, Model model) throws Exception {
		PageInfo<Post> pi = service.getPage(page);
		model.addAttribute("pi", pi);
		return "post/list";
	}

	// 블로그 작성을 위한 페이지 요청
	@GetMapping("/create")
	public String getCreate(@PathVariable String blogUser, Post post, Model model) throws Exception {
		model.addAttribute("blogUser", blogUser);
		return "post/create";
	}

	// 블로그 작성 처리
	@PostMapping("/create")
	public String postCreate(
			@PathVariable String blogUser,
			@Valid Post post, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			System.out.println("error occured");
			return "post/create";
		}
		service.create(post);
		return "redirect:/blogs/" + blogUser + "/list";
	}

	// 작성된 블로그를 보여주는 메소드
	@GetMapping("/view/{postId}")
	public String view(@PathVariable int postId, Model model) throws Exception {
		Post post = service.getPost(postId);
		model.addAttribute("post", post);
		return "post/view";
	}

	// 작성된 블로그의 수정을 위한 페이지 요청을 처리하는 메소드
	@GetMapping("/edit/{postId}")
	public String getEdit(@PathVariable int postId, Model model) throws Exception {
		Post post = service.getPost(postId);
		model.addAttribute("post", post);
		return "post/edit";
	}

	// 작성된 블로그의 수정을 처리하는 메소드
	@PostMapping("/edit/{postId}")
	public String postEdit(
			@PathVariable String blogUser,
			@RequestParam(value = "page") int page, @Valid Post post, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {			
			service.setAttachmentFiles(post);
			return "post/edit";
		}

		if (service.update(post)) {
			return "redirect:/blogs/" + blogUser+ "/view/" + post.getPostId() + "?page=" + page;
		} else {
			FieldError fieldError = new FieldError("post", "password", "비밀번호가 일치하지 않습니다");
			result.addError(fieldError);
			return "post/edit";
		}
	}

	// 작성된 블로그 글의 삭제를 처리하는 메소드
	@DeleteMapping("/delete/{postId}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> delete(@PathVariable int postId,
			@RequestParam(value = "password") String password) throws Exception {

		Map<String, String> map = new HashMap<>();
		if (service.delete(postId)) {
			map.put("result", "success");
		} else {
			map.put("result", "비밀번호가 일치하지 않습니다.");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.OK);
	}
	
}
