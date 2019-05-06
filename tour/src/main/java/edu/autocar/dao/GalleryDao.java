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
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import edu.autocar.domain.Gallery;

public interface GalleryDao{
	final static String PAGING_SUBQUERY = "SELECT  ROW_NUMBER() OVER (ORDER BY gallery_id DESC) AS SEQ, gallery.* FROM gallery";;

	@Select("SELECT count(*) FROM gallery")
	int count() throws Exception;

	@Select("SELECT * FROM (" + PAGING_SUBQUERY + ") WHERE SEQ  BETWEEN  #{start} AND #{end}")
	@Results(id="Gallery", value={
			@Result(property="galleryId", column="gallery_id"),
		@Result(property="list"		, column="gallery_id",
				many=@Many(select = "edu.autocar.dao.ImageDao.getGalleryImages"))
	})	
	List<Gallery> getPage(@Param("start") int start, @Param("end") int end) throws Exception;

	@Select("SELECT * FROM GALLERY WHERE gallery_id=#{galleryId}")
	@ResultMap("Gallery")
	Gallery findById(int galleryId) throws Exception;

	@InsertProvider(type=GallerySQL.class, method="insert")
	@Options(useGeneratedKeys=true, keyColumn="gallery_id", keyProperty="galleryId")
	int insert(Gallery gallery) throws Exception;

	@UpdateProvider(type=GallerySQL.class, method="update")
	int update(Gallery gallery) throws Exception;

	@Delete("DELETE FROM gallery WHERE gallery_id=#{galleryId}")	
	int delete(int galleryId) throws Exception;
	
	@Select("SELECT * FROM (SELECT * FROM GALLERY ORDER BY DBMS_RANDOM.RANDOM) WHERE ROWNUM < ${count}")
	List<Gallery> getRandom(int count) throws Exception;
	
	@Select("SELECT * FROM GALLERY WHERE owner=#{owner}")
	List<Gallery> findByOwner(int owner) throws Exception;

	
	public static class GallerySQL{
		public static String insert(Gallery gallery) {
			return new SQL() {{
				INSERT_INTO("gallery");
				VALUES("gallery_id", "GALLERY_SEQ.NEXTVAL");
			    VALUES("owner, password, title, description", 
			    	"#{owner}, #{password}, #{title}, #{description}");			    
			  }}.toString();
		  
		}
		public static String update(Gallery gallery) {
			return new SQL()
				    .UPDATE("gallery")
				    .SET("title = #{title}", "description = #{description}")
				    .SET("update_date = sysdate")
				    .WHERE("gallery_id = #{galleryId}")
				    .toString();
		}
	}
}

