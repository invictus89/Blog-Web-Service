package edu.autocar.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.dao.ImageDao;
import edu.autocar.domain.FileInfo;
import edu.autocar.domain.Image;
import edu.autocar.util.ImageUtil;
import edu.autocar.util.SqlUtil;


@Repository
public class ImageServiceImpl implements ImageService {
	final static String IMAGE_PATH = "c:/temp/gallery/%05d";
	final static String THUMB_PATH = "c:/temp/gallery/thumb/%05d.jpg";
	
	@Autowired
	ImageDao dao;
	
	@Override
	public List<Image> getGalleryImages(int galleryId) throws Exception {
		return dao.getGalleryImages(galleryId);
	}

	@Override
	public Image getImage(int imageId) throws Exception {
		return dao.getImage(imageId);
	}

	@Transactional
	@Override
	public void create(Image image) throws Exception {
		dao.insert(image);
	}

	@Transactional
	@Override
	public boolean delete(int imageId) throws Exception {
		return dao.delete(imageId)==1;
	}

	@Override
	public FileInfo getFileInfo(int imageId) throws Exception{
		Image image = getImage(imageId);		
		return image.toFileInfo(IMAGE_PATH);
	}

	@Override
	public FileInfo getThumbFileInfo(int imageId) throws Exception {
		Image image = getImage(imageId);
		return image.toFileInfo(THUMB_PATH, true);
	}


	@Override
	public void saveImage(Image image, MultipartFile file) throws Exception {
		String imagePath = String.format(IMAGE_PATH, image.getImageId());
		String thumbPath = String.format(THUMB_PATH, image.getImageId());
		
		File imageFile = new File(imagePath);
		File thumbFile = new File(thumbPath);
		
		file.transferTo(imageFile);
		ImageUtil.thumb(imageFile, thumbFile);
	}

	@Override
	public boolean deleteByGalleryId(int galleryId) throws Exception {
		return dao.deleteByGalleryId(galleryId) >= 0; 
	}

	@Override
	public void deleteImages(int[] images) throws Exception {
		if(images==null) return;
		String strImages = SqlUtil.toString(images);
		dao.deleteImages(strImages);
	}	
}
