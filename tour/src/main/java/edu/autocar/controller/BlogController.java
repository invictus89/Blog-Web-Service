package edu.autocar.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.autocar.domain.Blog;
import edu.autocar.domain.Member;
import edu.autocar.service.BlogService;

@Controller
@RequestMapping("/member/blog")
public class BlogController {
	@Autowired
	BlogService service;
	
	@GetMapping("/manage")
	public void getManage(Model model, HttpSession session) throws Exception {
		Member member = (Member)session.getAttribute("USER");
		Blog blog = service.getBlogByOwner(member.getUserId());
		if(blog == null) {
			model.addAttribute("blog", new Blog());
		}
		else {
			model.addAttribute("blog", blog);
		}
	}
	
	@PostMapping("/view")
	public String postManage(@Valid Blog blog, BindingResult result,
			RedirectAttributes ra) throws Exception {
		if(result.hasErrors()) {
			return "member/blog/view";
		}
		
		if(blog.getBlogId() == 0) {	// 생성
			service.create(blog);
			ra.addAttribute("result", "블러그를 생성했습니다.");
		}
		else {	// 수정
			service.update(blog);			
			ra.addAttribute("result", "블러그를 수정했습니다.");
		}
		return "redirect:/member/blog/view";
	}
}
