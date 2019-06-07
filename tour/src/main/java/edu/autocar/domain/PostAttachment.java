package edu.autocar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @FileName : PostAttachment.java
 * 특정 블로그의 게시글에 업로드될 정보 DTO
 * @author 백상우
 * @Date : 2019. 3. 6. 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAttachment {
	private int attachmentId;
	private int postId;
	private String originalName;
	private int fileSize;
	private int updateDate;
}
