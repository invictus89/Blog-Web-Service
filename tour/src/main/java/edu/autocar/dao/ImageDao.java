package edu.autocar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import edu.autocar.domain.Image;

public interface ImageDao {
	
	@Select("SELECT * FROM image WHERE gallery_id=#{galleryId}")
	List<Image> getGalleryImages(int galleryId) throws Exception;
	
	@Results(id="image", value={
		@Result(column="image_id", property="imageId")
	})
	
	@Select("SELECT * FROM image WHERE image_id=#{imageId}")
	Image getImage(int imageId) throws Exception;

	@Insert(
		"INSERT INTO image (image_id, gallery_id, original_name, file_size, mime_type) " + 
		"VALUES(IMAGE_SEQ.NEXTVAL, #{galleryId}, #{originalName}, #{fileSize}, #{mimeType})"
	)
	@Options(useGeneratedKeys = true, keyColumn="image_id", keyProperty = "imageId")
	int insert(Image image) throws Exception;
	
	@Delete("DELETE FROM image WHERE image_id=#{imageId}")
	int delete(int imageId) throws Exception;
	
	@Delete("DELETE FROM image WHERE gallery_id=#{galleryId}")
	int deleteByGalleryId(int galleryId) throws Exception;

	@Delete("DELETE FROM image WHERE image_id IN(${images})")
	void deleteImages(@Param("images") String images);
}
