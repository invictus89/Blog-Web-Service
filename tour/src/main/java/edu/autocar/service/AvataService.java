package edu.autocar.service;

import edu.autocar.domain.Avata;
import edu.autocar.domain.Member;

public interface AvataService {
	
	Avata getAvata(String userId) throws Exception;

	void create(Member member)throws Exception;

	boolean update(Member member)throws Exception;

	boolean delete(String userId) throws Exception;


}
