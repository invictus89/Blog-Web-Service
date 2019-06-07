package edu.autocar.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import edu.autocar.domain.Blog;

/**
 * @FileName : BlogDao.java
 *
 * 블로그 생성/삭제/수정/페이징 처리 및 리스트 출력을 위한 DB와의 연동
 * 
 * @author student
 * @Date : 2019. 6. 7. 
 */
public interface BlogDao {
	final static String PAGING_SUBQUERY = "SELECT  ROW_NUMBER() OVER (ORDER BY gallery_id DESC) AS SEQ, blog.* FROM blog";

	@Select("SELECT count(*) FROM blog")
	int count() throws Exception;

	@Select("SELECT * FROM (" + PAGING_SUBQUERY + ") WHERE SEQ  BETWEEN  #{start} AND #{end}")
	public Blog getPage(@Param("start") int start, @Param("end") int end );
	
	@Select("select * from blog where blog_id=#{blogId}")
	public Blog findById(int blogId);
	
	@Select("select * from blog where owner=#{owner}")
	Blog findByOwner(String owner);
	
	
	@InsertProvider(type=BlogDaoSQL.class, method="insert")
	public int insert(Blog blog);
	
	@UpdateProvider(type=BlogDaoSQL.class, method="update")
	public int update(Blog blog);
	
	@Delete("delete from blog where blog_id = #{blogId}")
	public int delete(int blogId);

	public static class BlogDaoSQL {
		public static String insert(Blog blog) {
			return new SQL() {{
				INSERT_INTO("blog");
				VALUES("blog_id", "blog_seq.nextval");
				VALUES("owner, title, description", "#{owner}, #{title}, #{description}");
				if(blog.getImage()!=null) {
					VALUES("image", "#{image}");
				}
			}}.toString();
		}
		
		public static String update(Blog blog) {
			return new SQL() {{
				UPDATE("blog");
				SET("title=#{title}, description=#{description}, update_date=sysdate");
				if(blog.getImage()!=null) {
					SET("image=#{image}");
				}
				WHERE("blog_id = #{blogId}");
			}}.toString();
		}
	}
	
}
