package edu.autocar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import edu.autocar.domain.Board;
import edu.autocar.domain.BoardAttachment;

public interface BoardAttachmentDao {
	
	@Select("SELECT * FROM board_attachment WHERE board_id=#{boardId}")
	List<BoardAttachment> getBoardFiles(int boardId) throws Exception;
	
	@Results(id="attachment", value={
		@Result(column="attachment_id", property="attachmentId")
	})	
	@Select("SELECT * FROM board_attachment WHERE attachment_id=#{attachmentId}")
	BoardAttachment getBoardAttachment(int attachmentId) throws Exception;

	@Insert(
		"INSERT INTO board_attachment (attachment_id, board_id, original_name, file_size) " + 
		"VALUES(BOARD_ATTACH_SEQ.NEXTVAL, #{boardId}, #{originalName}, #{fileSize})"
	)
	@Options(useGeneratedKeys = true, keyColumn="attachment_id", keyProperty = "attachmentId")
	int insert(BoardAttachment attachment) throws Exception;
	
	@Delete("DELETE FROM board_attachment WHERE attachment_id=#{attachmentId}")
	int delete(int attachmentId) throws Exception;
	
	
	@Delete("DELETE FROM board_attachment WHERE board_id=#{boardId}")
	int deleteByBoardId(int boardId) throws Exception;

	@Delete("DELETE FROM board_attachment WHERE attachment_id IN(${attachments})")
	void deleteFiles(@Param("attachments") String attachments);
}
