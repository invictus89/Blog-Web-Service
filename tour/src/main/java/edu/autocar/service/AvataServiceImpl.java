package edu.autocar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.dao.AvataDao;
import edu.autocar.domain.Avata;
import edu.autocar.domain.Member;
import edu.autocar.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AvataServiceImpl implements AvataService{

	@Autowired
	AvataDao dao;

	Avata createAvata(Member member) throws Exception {
		MultipartFile file = member.getAvata();
		if(file== null || file.isEmpty()) return null;
		
		Avata avata = new Avata(member.getUserId(), 									
								ImageUtil.thumb(file.getBytes()));
		return avata;
	}
	
	@Override
	public void create(Member member) throws Exception {
		Avata avata = createAvata(member);
		if(avata != null) {			
			dao.insert(avata);
		}
	}


	@Override
	public Avata getAvata(String userId) throws Exception {
		Avata avata = dao.findById(userId);
		if(avata==null) {		// 아바타 이미지가 없는 경우
			// 디폴트 아바타 사용 - anonymous 사용자 미리 등록
			avata = dao.findById("anonymous");			
		}	
		return avata;
	}

	@Override
	public boolean update(Member member) throws Exception {
		Avata avata = createAvata(member);
		if(avata == null) return true;

		if(dao.update(avata) == 1) {
			return true;
		} else { // 존재하지 않는 경우 
			return dao.insert(avata) == 1;
		}
	}

	@Override
	public boolean delete(String userId) throws Exception {
		return dao.delete(userId) == 1;
	}


}
