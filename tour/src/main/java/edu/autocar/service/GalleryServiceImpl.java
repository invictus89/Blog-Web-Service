package edu.autocar.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.dao.GalleryDao;
import edu.autocar.domain.Gallery;
import edu.autocar.domain.Image;
import edu.autocar.domain.PageInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class GalleryServiceImpl implements GalleryService {
	
	@Autowired
	GalleryDao dao;	
	
	@Autowired
	ImageService imageService;
	
	@Override
	public PageInfo<Gallery> getPage(int page) throws Exception {
		int totalCount = dao.count();
		PageInfo<Gallery> pi = new PageInfo<>(page, totalCount);
		List<Gallery> list = dao.getPage(pi.getStart(), pi.getEnd());
		pi.setList(list);
		return pi;
	}
	
	@Override
	public Gallery getGallery(int galleryId) throws Exception {
		return dao.findById(galleryId);
	}

	
	@Transactional
	@Override
	public void create(Gallery gallery) throws Exception {
		dao.insert(gallery);
		
		saveImages(gallery);
	}

	private void saveImages(Gallery gallery) throws Exception {
		for(MultipartFile file : gallery.getFiles()) {	
			if(file.isEmpty()) continue;
			Image image = Image.builder()
							.galleryId(gallery.getGalleryId())
							.originalName(file.getOriginalFilename())
							.fileSize((int)file.getSize())
							.mimeType(file.getContentType())
							.build();
			
			imageService.create(image);
			imageService.saveImage(image, file);
		}
	}
	
	@Override
	public List<Gallery> findByOwner(String userId) throws Exception {
		return null;
	}

	@Override
	public boolean update(Gallery gallery) throws Exception {
		if(dao.update(gallery) ==0) return false;
		
		// 이미지 추가
		saveImages(gallery);
		
		// 이미지 삭제
		imageService.deleteImages(gallery.getDeleteImages());
		
		return true;
	}

	@Override
	public boolean delete(int galleryId, String password) throws Exception {
		Gallery gallery =  dao.findById(galleryId);
		if(gallery.getPassword().equals(password)) {
			imageService.deleteByGalleryId(galleryId);
			return dao.delete(galleryId) == 1;
		}
		return false;
	}
}






