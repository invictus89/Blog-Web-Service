package edu.autocar.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import edu.autocar.domain.Board;
import edu.autocar.domain.PageInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Service
public class BoardServiceImpl__ implements BoardService {
	private List<Board> boardList;
	int maxId=0;

	public BoardServiceImpl__() {
		boardList = Collections.synchronizedList(new LinkedList<>());
		for(maxId=1; maxId<=45; maxId++) {
			boardList.add(0, new Board(maxId));
		}
	}
	
	@Override
	public PageInfo<Board> getPage(int page) throws Exception {
		int totalCount = boardList.size(); 
		PageInfo<Board> pi = new PageInfo<>(page, totalCount);
		
		List<Board> list = new ArrayList<>();
		int size = boardList.size(); 
		for(int i=pi.getStart(); i < pi.getEnd() && i < size; i++) {
			list.add(boardList.get(i));
		}	
		
		pi.setList(list);
		return pi;
		
	}
	
	@Override
	public Board getBoard(int boardId) throws Exception {
		for(Board board : boardList) {
			if(board.getBoardId() == boardId) {
				int cnt = board.getReadCnt();
				board.setReadCnt(cnt+1);
				return board;
			}
		}
		return null;
	}


	
	@Override
	public void create(Board board) throws Exception {
		board.setBoardId(maxId);
		board.setRegDate(new Date());
		board.setUpdateDate(new Date());
		maxId++;
		boardList.add(0, board);
	}

	
	@Override
	public boolean update(Board board) throws Exception {
		for(Board b : boardList) {
			if(b.getBoardId() == board.getBoardId()) {
				if(b.getPassword().contentEquals(board.getPassword())) {
					b.setTitle(board.getTitle());
					b.setWriter(board.getWriter());
					b.setContent(board.getContent());
					b.setUpdateDate(new Date());
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public boolean delete(int boardId, String password) throws Exception {
		for(int ix=0; ix < boardList.size(); ix++) {
			Board board = boardList.get(ix);
			if(board.getBoardId() == boardId) {
				if(board.getPassword().contentEquals(password)) {
					boardList.remove(ix);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public void setAttachmentFiles(Board board) {
		// TODO Auto-generated method stub
		
	}

}
