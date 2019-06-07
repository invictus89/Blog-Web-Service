package edu.autocar.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.autocar.domain.Reply;
import edu.autocar.service.ReplyService;

/**
 * @FileName : ReplyController.java
 *		
 *	게시판의 계층형 댓글을 구현하기 위한 컨트롤러 입니다.	
 *
 * @author 백상우
 * @Date : 2019. 3. 12. 
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	ReplyService service;

	// Restful API 방식의 정상적 응답을 처리하는 메소드 
	public <T> ResponseEntity<T> getResult(T t) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<T>(t, headers, HttpStatus.OK);
	}
	
	// Restful API 방식의 비정상적 응답을 처리하는 메소드 
	public <T> ResponseEntity<T> handleError(Exception e) {
		e.printStackTrace();	
		final HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-Type", "application/json;charset=UTF-8"); 		
		 return new ResponseEntity<T>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}	

	
	/**
	 * 댓글 리스트 출력 요청을 처리하는 메소드
	 * @param reply
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Reply>> list(Reply reply){
		try {
			List<Reply> list = service.getList(reply);
			return getResult(list);
		 } catch (Exception e) {return handleError(e);} 	
	}	
	
	/**
	 * 대댓글 출력 요청 처리를 위해 댓글 id 를 통해 상위 댓글 정보 요청을 처리하는 메소드
	 * @param replyId
	 * @return
	 */
	@GetMapping("/{replyId}")
	public ResponseEntity<Reply> replyId(
										@PathVariable long replyId){
		try {
			Reply reply= service.findById(replyId);
			return getResult(reply);
		 } catch (Exception e) {return handleError(e);}
	}

	/**
	 * 댓글 생성 요청을 처리하는 메소드
	 * @param reply
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Reply> create(@RequestBody Reply reply){
		// 데이터가 json으로 전송되므로 @RequestBody 사용
		try {
			Reply r= service.create(reply);
			return getResult(r);
		 } catch (Exception e) {return handleError(e);} 		
	}
	
	/**
	 * 댓글 수정 요청을 처리하는 메소드
	 * @param reply
	 * @return
	 */
	@PutMapping("/{replyId}")
	public ResponseEntity<Reply> update(@RequestBody Reply reply){
		// 데이터가 json으로 전송되므로 @RequestBody 사용
		try {
			Reply r= service.update(reply);
			return getResult(r);
		 } catch (Exception e) {return handleError(e);} 		
	}
	
	/**
	 * 댓글 삭제 요청을 처리하는 메소드
	 * @param reply
	 * @return
	 */
	@DeleteMapping("/{replyId}")
	public ResponseEntity<Reply> delete(Reply reply){
		// password가 쿼리 파라미터로 전송됨
		// @RequestBody 사용하지 않음
		try {
			service.delete(reply);
			return getResult(reply);
		} catch (Exception e) {return handleError(e);}		
	}
	
}
