package edu.autocar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
