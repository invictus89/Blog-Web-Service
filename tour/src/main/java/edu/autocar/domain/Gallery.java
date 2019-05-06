package edu.autocar.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Gallery {
	
	private int		galleryId;
	private String	owner;
	@NotEmpty(message="비밀번호는 필수 항목입니다.")
	private String	password;
	@NotEmpty(message="제목은 필수 항목입니다.")
	private String 	title;		// 이미지 타이틀
	private String 	description;// 설명
	private int 	readCnt;	// 조회수
	private Date	regDate;	// 등록일
	private Date	updateDate;
	private List<Image> list;
	
	private List<MultipartFile> files;

	public int getRandom() {
		if(!list.isEmpty()) {
			int ix =(int)(Math.random() * list.size());
			return list.get(ix).getImageId();
		}
		return -1;
	}	
	
	private int[] deleteImages;
	
	public boolean isDeleteImage(int imageId) {
		if(deleteImages != null) { 
			for(int id : deleteImages) {
				if(imageId == id) return true;
			}
		}
		return false;
	}
	
}




