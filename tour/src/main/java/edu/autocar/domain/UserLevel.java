package edu.autocar.domain;

import lombok.Getter;

/**
 * @FileName : UserLevel.java
 * 회원 정보 등급 DTO
 * @author 백상우
 * @Date : 2019. 3. 2. 
 */
@Getter
public enum UserLevel {
	ADMIN("ADMIN", "관리자"),
	NORMAL("NORMAL", "일반"),
	SILVER("SILVER", "실버"),
	GOLD("GOLD", "골드");

	private String value;
	private String label;
	
    private UserLevel(String value, String label) {
    	this.value = value;
    	this.label = label;
    }
}
