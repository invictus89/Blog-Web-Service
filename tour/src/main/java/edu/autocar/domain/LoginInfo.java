package edu.autocar.domain;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName : LoginInfo.java
 * 특정 서비스 이용 시 로그인 필요 여부와
 * 로그인 필요 시 로그인 후 원래 가고 하였던 페이지 이동을 위한 로그인 정보 DTO
 * @author 백상우
 * @Date : 2019. 3. 2. 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
	@NotEmpty(message="사용자 ID는 필수 항목입니다.")
	private String userId;
	@NotEmpty(message="비밀번호는 필수 항목입니다.")
	private String password;
	private String reason;	// 리다이렉트 된 이유
	private String target;	// 로그인 전 원래 가고자 했던 url  

}
