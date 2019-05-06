package edu.autocar.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

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
