package edu.autocar.domain;

import java.util.Date;

import lombok.Data;

/**
 * @FileName : Reply.java
 * 댓글 DTO
 * @author 백상우
 * @Date : 2019. 3. 9. 
 */
@Data
public class Reply {
	private int 	replyId;	// 댓글 번호
	private int 	groupId;	// 글 그룹
	private String	tableName;	// 글 그룹 테이블 명
	private int		level;		// 댓글 수준
	private int		parent;		// 상위 글 번호 
	private String	writer;		// 작성자 ID
	private String 	password;	// 비밀번호
	private String	content;	// 내용
	private int		deleted;	// 삭제여부
	private Date	regDate;
	private Date	updateDate;
}


