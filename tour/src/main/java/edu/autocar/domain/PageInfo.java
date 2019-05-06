package edu.autocar.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PageInfo<T> {
	public int perPage;		// 1페이지당 데이터 건수
	public int perBlock;		// 1블럭당 페이지 수

	private int page;				// 현재 페이지
	private int totalCount;		// 전체 데이터 건 수

	private int start;				// 현재 페이지 시작 번호
	private int end;					// 현재 페이지 끝 번호
	private int totalPage;			// 전체 페이지 수
	private int startPage;			// 현재 페이지 블럭에서 시작 페이지 번호
	private int endPage;			// 현재 페이지 블럭에서 마지막 페이지 번호
	
	private int currentBlock;		// 현재 페이지 블럭 
	private int totalBlock;		// 전체 페이지 블럭 수
	private int prevBlockPage;	// 이전 페이지 블록 시작 페이지  
	private int nextBlockPage;	// 다음 페이지 블록 시작 페이지 

	List<T> list;					// 해당 페이지 목록 

	public PageInfo(int currentPage, int total) {
		this(currentPage, total, 10, 10);
	}
	
	public PageInfo(int currentPage, int total, int perPage, int perBlock) {
		page = currentPage;
		this.totalCount = total;
		this.perPage = perPage;
		this.perBlock = perBlock;
		
		start= (page-1)*perPage+1;
		end = start + perPage-1;
		totalPage = (int)Math.ceil((float)total/perPage);	

		currentBlock= (int)Math.ceil((float)currentPage/perBlock);
		totalBlock = (int)Math.ceil((float)totalPage/perBlock); 
		
		startPage = (currentBlock-1)* perBlock + 1;	// 블록 시작 페이지
		endPage = startPage + perBlock - 1; 		//블록 마지막 페이지
		
		if(endPage > totalPage) endPage = totalPage;

		prevBlockPage = page - perBlock;
		nextBlockPage = page + perBlock;	
	}
}
