package edu.autocar.service;

import org.springframework.web.multipart.MultipartFile;

import edu.autocar.domain.Member;
import edu.autocar.domain.PageInfo;

public interface MemberService {
	PageInfo<Member> getPage(int page) throws Exception;
	Member getMember(String userId) throws Exception;
	void create(Member member) throws Exception;
	boolean update(Member member) throws Exception;
	boolean updateByAdmin(Member member) throws Exception;
	boolean delete(String userId, String password) throws Exception;
	
	// 비밀번호 체크 
	Member checkPassword(String userId, String password) throws Exception;
	boolean multiDelete(String[] users, String password) throws Exception;
}
