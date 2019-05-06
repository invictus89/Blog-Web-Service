package edu.autocar.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.dao.PostAttachmentDao;
import edu.autocar.dao.PostDao;
import edu.autocar.domain.PageInfo;
import edu.autocar.domain.Post;
import edu.autocar.domain.PostAttachment;
import edu.autocar.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PostServiceImpl implements PostService {

	final static String UPLOAD_PATH = "c:/temp/post/%05d";
	@Autowired
	PostDao dao;	
	
	@Autowired
	PostAttachmentDao attachDao;	
	
	
	@Override
	public PageInfo<Post> getPage(int page) throws Exception {
		int totalCount = dao.count();
		PageInfo<Post> pi = new PageInfo<>(page, totalCount);
		List<Post> list = dao.getPage(pi.getStart(), pi.getEnd());
		pi.setList(list);
		return pi;
	}
	
	@Transactional
	@Override
	public Post getPost(int postId) throws Exception {
		dao.increaseReadCnt(postId);
		return dao.findById(postId);
	}

	@Transactional
	@Override
	public void create(Post post) throws Exception {
		dao.insert(post);
		
		saveFiles(post);
	}

	private void saveFiles(Post post) throws Exception {
		for(MultipartFile file : post.getFiles()) {	
			if(file.isEmpty()) continue;
			
			PostAttachment attachment = PostAttachment.builder()
							.postId(post.getPostId())
							.originalName(file.getOriginalFilename())
							.fileSize((int)file.getSize())
							.build();

			attachDao.insert(attachment);
			saveFile(file, attachment);
		}
	}
	
	private void saveFile(MultipartFile file, PostAttachment attachment) throws Exception {
		String uploadPath = String.format(UPLOAD_PATH, attachment.getAttachmentId());
		
		File uploadFile = new File(uploadPath);
		file.transferTo(uploadFile);

	}
	@Override
	public boolean update(Post post) throws Exception {
		if(dao.update(post) != 1) return false;

		// 파일 삭제 추가
		if(post.getDeleteFiles() !=null) {
			String deleteFiles = SqlUtil.toString(post.getDeleteFiles());
			attachDao.deleteFiles(deleteFiles);
		}
		saveFiles(post);
		return true;
		
	}

	@Override
	public boolean delete(int postId) throws Exception {
		
		// 첨부파일 삭제 
		attachDao.deleteByPostId(postId);	
		return dao.delete(postId) == 1;
	}

	@Override
	public void setAttachmentFiles(Post post) throws Exception {
		post.setFileList(
				attachDao.getPostFiles(post.getPostId()));
		
	}
}
