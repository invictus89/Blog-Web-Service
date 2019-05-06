package edu.autocar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
