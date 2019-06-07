package edu.autocar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName : BoardAttachment.java
 * 
 * 특정 게시글의 파일 업로드 관리를 위한 DTO
 * 
 * @author 백상우
 * @Date : 2018. 3. 5. 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAttachment {
	private int attachmentId;
	private int boardId;
	private String originalName;
	private int fileSize;
	private int updateDate;
}
