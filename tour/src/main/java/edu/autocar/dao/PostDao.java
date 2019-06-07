package edu.autocar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

import edu.autocar.domain.Post;

/**
 * @FileName : PostDao.java
 *
 * 특정 회원의 블로그에 글 등록 / 삭제 / 수정 을 처리하는 DAO
 *
 * @author student
 * @Date : 2019. 6. 7. 
 */
public interface PostDao extends CrudDao<Post, Integer> {
	
	final static String PAGING_SUBQUERY = 
			"select  row_number() over (order by post_id desc) as seq, post.* from post";

	@Select("select count(*) from post")
	int count() throws Exception;

	@Select("select * from (" + PAGING_SUBQUERY + ") where seq  between  #{start} and #{end}")
	@Results(id="Post", value={
		@Result(property="postId", column="post_id"),
		@Result(property="fileList"		, column="post_id",
				many=@Many(select = "edu.autocar.dao.PostAttachmentDao.getPostFiles"))
	})	
	List<Post> getPage(@Param("start") int start, @Param("end") int end) throws Exception;

	@Select("select * from post where post_id=#{postid}")
	@ResultMap("Post")
	Post findById(int postId) throws Exception;

	@Update("update post set read_cnt = read_cnt + 1 where post_id=#{postId}")
	void increaseReadCnt(Integer postId) throws Exception;
	 	
	@InsertProvider(type=PostSQL.class, method="insert")
	@Options(useGeneratedKeys=true, keyColumn="post_id", keyProperty="postId")
	int insert(Post post) throws Exception;

	@Update("UPDATE post SET title = #{title}, content = #{content}, update_date = sysdate WHERE post_id=#{postId}")
	int update(Post post) throws Exception;

	@Delete("delete from post where post_id=#{postId}")	
	int delete(int postId) throws Exception;
	
	public static class PostSQL{
		public static String insert(Post post) {
			return new SQL() {{
				INSERT_INTO("post");
				VALUES("post_id", "POST_SEQ.NEXTVAL");
			    VALUES("title,  content", "#{title},  #{content}");			    
			  }}.toString();	
		}
	}	
}
