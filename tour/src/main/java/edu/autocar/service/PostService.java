package edu.autocar.service;

import edu.autocar.domain.Post;
import edu.autocar.domain.PageInfo;

public interface PostService {
	PageInfo<Post> getPage(int page) throws Exception;
	Post getPost(int postId) throws Exception;
	void create(Post post) throws Exception;
	boolean update(Post post) throws Exception;
	boolean delete(int postId) throws Exception;
	void setAttachmentFiles(Post post) throws Exception;
	
}
