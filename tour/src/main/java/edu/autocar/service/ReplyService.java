package edu.autocar.service;

import java.util.List;

import edu.autocar.domain.Reply;

public interface ReplyService {

	List<Reply>	getList(Reply reply) throws Exception;
	
	Reply	findById(long replyId) throws Exception;
	
	Reply create(Reply reply) throws Exception;
	
	Reply update(Reply reply) throws Exception;
	
	void delete(Reply reply) throws Exception;
	
}
