package edu.autocar.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int		boardId;
	@NotEmpty(message="제목은 필수 항목입니다.")
	private String 	title;
	@NotEmpty(message="작성자는 필수 항목입니다.")
	private String 	writer;
	@NotEmpty(message="비밀번호는 필수 항목입니다.")
	@Length(min=6, message="비밀번호는 6글자 이상이어야 합니다.")
	private String 	password;
	private String	content;
	private int		readCnt;
	private Date	regDate;
	private Date	updateDate;
	
	private List<BoardAttachment> fileList;
	
	private List<MultipartFile> files;
	
	// 테스트 용 생성자
	public Board(int boardId) {
		super();
		this.boardId = boardId;
		title = "제목" + boardId;
		writer = "홍길동 " + boardId;
		content = "내용 " + boardId;
		password ="123456";
		readCnt = 0;
		regDate = new Date();
		updateDate = new Date();
	}	
	
	private int[] deleteFiles;
	
	public boolean isDeleteFile(int attachmentId) {
		if(deleteFiles != null) { 
			for(int id : deleteFiles) {
				if(attachmentId == id) return true;
			}
		}
		return false;
	}

	
}