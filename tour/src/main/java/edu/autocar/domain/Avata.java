package edu.autocar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName : Avata.java
 *
 * 회원 아이디와 회원의 이미지를 관리할 DTO
 * 
 * @author 백상우
 * @Date : 2019. 3. 7. 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avata {
	private String userId;
	private byte[] image;
}
