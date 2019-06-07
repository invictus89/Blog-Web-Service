package edu.autocar.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * @FileName : Blog.java
 * 
 * 블로그 관리 DTO
 * 
 * @author 백상우
 * @Date : 2019. 3. 9. 
 */
@Data
public class Blog {
	private int blogId;
	private String owner;
	private String title;
	private String description;
	private byte[] image;
	private Date regDate;
	private Date updateDate;
	
	MultipartFile file;
}
