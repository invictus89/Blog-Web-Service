package edu.autocar.service;

import java.util.List;

import edu.autocar.domain.Blog;

public interface BlogService {
	List<Blog> getPage(int page) throws Exception;
	Blog getBlog(int blogId) throws Exception;
	Blog getBlogByOwner(String owner) throws Exception;
	void create(Blog blog) throws Exception;
	void update(Blog blog) throws Exception;
	void delete(Blog blog) throws Exception;
	
}
