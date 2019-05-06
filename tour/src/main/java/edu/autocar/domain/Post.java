package edu.autocar.domain;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

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
