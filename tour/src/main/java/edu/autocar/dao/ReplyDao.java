package edu.autocar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import edu.autocar.domain.Reply;

public interface ReplyDao {
	
	@Select({
		"select count(*) from reply ",
		"where table_name=#{tableName} and group_id=#{groupId}"})
	int count(Reply reply) throws Exception;

	@Select("select * from reply where reply_id=#{replyId} ")
	Reply findById(long replyId) throws Exception;
	
	@Select({
		"select level, r.* ",
		"from reply r",
		"where table_name=#{tableName} and group_id=#{groupId}",
		"start with parent = 0",
		"connect by parent = prior reply_id ", 
		"order siblings by reply_id desc"
	})	
	List<Reply> getList(Reply reply) throws Exception;
	
	@Insert({
		"insert into reply", 
		"	(reply_id, group_id, table_name, password, parent, writer, content)",
		" values(reply_seq.nextval, #{groupId}, #{tableName},  #{password}, ",
		"	 #{parent}, #{writer}, #{content})"
	})
	@Options(useGeneratedKeys=true, 
				keyColumn="reply_id", keyProperty = "replyId")	
	int insert(Reply reply) throws Exception;	

	@Update({
		"update reply set",  
		"	content = #{content}, update_date = sysdate", 
		"where reply_id=#{replyId}  and password = #{password}"
	})
	int update(Reply reply) throws Exception;
	
	@Delete({
		"update reply set", 
		"	content = '삭제된 글', deleted = 1, update_date = sysdate", 
		"where reply_id=#{replyId}  and password = #{password}"
	})
	int delete(Reply reply) throws Exception;

}