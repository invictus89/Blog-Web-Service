package edu.autocar.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName : FileInfo.java
 *
 * 게시글에 업로드할 파일 정보 DTO
 * @author 백상우
 * @Date : 2018. 3. 8. 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileInfo {
	private String path;
	private String originalName;
	private String mimeType;
	private int size;
	
	public FileInfo(String path, String originalName, String mimeType) 
						throws IOException {
		super();
		this.path = path;
		this.originalName = originalName;
		this.mimeType = mimeType;
		this.size = (int)Files.size(Paths.get(path));
	}
}
