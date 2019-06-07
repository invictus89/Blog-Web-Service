package edu.autocar.domain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName : ResultMsg.java
 * 
 * 각 종 데이터 처리 요청 시 결과 데이터와 메시지를 담을 DTO
 * 
 * @author 백상우
 * @Date : 2019. 3. 4. 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMsg {
	private String result;
	private String message;
	
	public static ResponseEntity<ResultMsg> response(String result, String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<>(
					new ResultMsg(result, message),
						headers, HttpStatus.OK);
	}
}
