package edu.autocar.domain;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
