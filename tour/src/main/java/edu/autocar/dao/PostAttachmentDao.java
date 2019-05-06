package edu.autocar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import edu.autocar.domain.PostAttachment;

public interface PostAttachmentDao {
	
	@Select("select * from post_attachment where post_id=#{postId}")
	List<PostAttachment> getPostFiles(int postId) throws Exception;
	
	@Results(id="attachment", value={
		@Result(column="attachment_id", property="attachmentId")
	})	
	@Select("select * from post_attachment where attachment_id=#{attachmentId}")
	PostAttachment getPostAttachment(int attachmentId) throws Exception;

	@Insert(
		"insert into post_attachment (attachment_id, post_id, original_name, file_size) " + 
		"values(board_attach_seq.nextval, #{postId}, #{originalName}, #{fileSize})"
	)
	@Options(useGeneratedKeys = true, keyColumn="attachment_id", keyProperty = "attachmentId")
	int insert(PostAttachment attachment) throws Exception;
	
	@Delete("delete from post_attachment WHERE attachment_id=#{attachmentId}")
	int delete(int attachmentId) throws Exception;
	
	
	@Delete("delete from post_attachment WHERE post_id=#{postId}")
	int deleteByPostId(int postId) throws Exception;

	@Delete("delete from post_attachment WHERE attachment_id IN(${attachments})")
	void deleteFiles(@Param("attachments") String attachments);
}
