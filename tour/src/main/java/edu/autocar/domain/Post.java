package edu.autocar.domain;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * @FileName : Post.java
 * 블로그 아이디 별 포스팅 정보 DTO
 * @author 백상우
 * @Date : 2019. 3. 5. 
 */
@Data
public class Post {
	int postId;
	int blogId;
	String title;
	int readCnt;
	String content;
	Date regDate;
	Date updateDate;
	
	List<PostAttachment>fileList;
	
	List<MultipartFile> files;	

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
