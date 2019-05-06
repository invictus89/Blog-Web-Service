package edu.autocar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.autocar.dao.ReplyDao;
import edu.autocar.domain.Reply;

@Repository
@Transactional
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	ReplyDao dao;
	
	@Override
	public List<Reply> getList(Reply reply) throws Exception {
		return dao.getList(reply);
	}

	@Override
	public Reply findById(long replyId) throws Exception {
		return dao.findById(replyId);
	}

	@Override
	public Reply create(Reply reply) throws Exception {
		dao.insert(reply);
		return dao.findById(reply.getReplyId());
	}
	@Override
	public Reply update(Reply reply) throws Exception {
		dao.update(reply);
		if(dao.update(reply)==0)
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		return dao.findById(reply.getReplyId());
	}

	@Override
	public void delete(Reply reply) throws Exception {
		if(dao.delete(reply)==0)
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
	}
}
