package edu.autocar.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMsg {
	public static <T> ResponseEntity<T> getResponseEntity(T t) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<>(
						t, headers, HttpStatus.OK);
	}
}
