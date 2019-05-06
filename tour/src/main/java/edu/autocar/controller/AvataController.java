package edu.autocar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.autocar.domain.Avata;
import edu.autocar.service.AvataService;


public class AvataController {
	@Autowired
	AvataService service;
	
	@GetMapping("/member/avata/{userId}")
	@ResponseBody
	public ResponseEntity<byte[]> getAvata(
						@PathVariable String userId) throws Exception {

		Avata avata = service.getAvata(userId);
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(avata.getImage(), 
							headers, HttpStatus.OK);
	}

}
