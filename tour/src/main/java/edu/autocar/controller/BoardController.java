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

import edu.autocar.domain.Board;
import edu.autocar.domain.Member;
import edu.autocar.domain.PageInfo;
import edu.autocar.service.BoardService;
import lombok.extern.slf4j.Slf4j;

/**
 * @FileName : BoardController.java
 *
 * 게시글 출력 / 생성 / 수정 / 삭제 등을 관리하는 컨트롤러
 * 
 * @author 백상우
 * @Date : 2019. 3. 1. 
 */
@Controller
@RequestMapping("/board")
@Slf4j

public class BoardController {
	@Autowired
	BoardService service;

	// 게시글 목록 출력 요청 메소드
	@GetMapping("/list")
	public void list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws Exception {
		
		PageInfo<Board> pi = service.getPage(page);
		model.addAttribute("pi", pi);
	}

	//게시글 생성 페이지 요청 메소드
	@GetMapping("/create")
	public void getCreate(Board board) throws Exception {

	}

	// 게시글 생성 로직을 처리하는 메소드
	@PostMapping("/create")
	public String postCreate(@Valid Board board, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return "board/create";
		}

		service.create(board);
		return "redirect:list";
	}

	// 특정 게시글의 상세 화면 페이지 요청 처리 메소드
	@GetMapping("/view/{boardId}")
	public String view(@PathVariable int boardId, Model model) throws Exception {
		Board board = service.getBoard(boardId);
		model.addAttribute("board", board);
		return "board/view";
	}

	// 특정 게시물의 수정 화면 요청 처리 메소드
	@GetMapping("/edit/{boardId}")
	public String getEdit(@PathVariable int boardId, Model model) throws Exception {
		Board board = service.getBoard(boardId);
		model.addAttribute("board", board);
		return "board/edit";
	}
	
	// 특정 게시글의 수정 요청 처리 메소드
	@PostMapping("/edit/{boardId}")
	public String postEdit(@RequestParam(value = "page") int page, @Valid Board board, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {			
			service.setAttachmentFiles(board);
			return "board/edit";
		}

		if (service.update(board)) {
			return "redirect:/board/view/" + board.getBoardId() + "?page=" + page;
		} else {
			FieldError fieldError = new FieldError("board", "password", "비밀번호가 일치하지 않습니다");
			result.addError(fieldError);
			return "board/edit";
		}
	}
	
	// 특정 게시글 삭제 요청 처리 메소드
	@DeleteMapping("/delete/{boardId}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> delete(@PathVariable int boardId,
			@RequestParam(value = "password") String password) throws Exception {

		Map<String, String> map = new HashMap<>();
		if (service.delete(boardId, password)) {
			map.put("result", "success");
		} else {
			map.put("result", "비밀번호가 일치하지 않습니다.");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.OK);

	}

}
