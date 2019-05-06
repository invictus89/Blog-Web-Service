package edu.autocar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.dao.BlogDao;
import edu.autocar.domain.Blog;

@Repository
public class BlogServiceImpl implements BlogService {
	@Autowired
	BlogDao dao;
	
	@Override
	public List<Blog> getPage(int page) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blog getBlog(int blogId) throws Exception {

		return dao.findById(blogId);
	}

	@Override
	public void create(Blog blog) throws Exception {
		MultipartFile file = blog.getFile();
		if(!file.isEmpty()) {
			blog.setImage(blog.getFile().getBytes());
		}
		dao.insert(blog);

	}

	@Override
	public void update(Blog blog) throws Exception {
		MultipartFile file = blog.getFile();
		if(!file.isEmpty()) {
			blog.setImage(blog.getFile().getBytes());
		}
		dao.update(blog);
	}

	@Override
	public void delete(Blog blog) throws Exception {
		

	}

	@Override
	public Blog getBlogByOwner(String owner) throws Exception {
		return dao.findByOwner(owner);
	}

}
