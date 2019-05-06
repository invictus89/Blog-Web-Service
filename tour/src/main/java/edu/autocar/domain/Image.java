package edu.autocar.domain;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
	private int		imageId;
	private int		galleryId;
	private String 	originalName;// 원본 파일명 
	private int 	fileSize;	// 파일 크기
	private String	mimeType;	// 파일의 mime 타입
	private Date	regDate;	// 등록일

	public FileInfo toFileInfo(String format) throws Exception{
		return toFileInfo(format, false);
	}

	public FileInfo toFileInfo(String format, boolean fromFile) throws Exception {
		String path = String.format(format, imageId);
		int size = fromFile ? (int)Files.size(Paths.get(path)) : fileSize;
		return new FileInfo(path, originalName, mimeType, size);
	}
}
