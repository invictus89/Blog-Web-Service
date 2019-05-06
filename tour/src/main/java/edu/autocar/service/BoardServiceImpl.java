package edu.autocar.service;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.dao.BoardAttachmentDao;
import edu.autocar.dao.BoardDao;
import edu.autocar.domain.Board;
import edu.autocar.domain.BoardAttachment;
import edu.autocar.domain.PageInfo;
import edu.autocar.util.ImageUtil;
import edu.autocar.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BoardServiceImpl implements BoardService {

	final static String UPLOAD_PATH = "c:/temp/board/%05d";
	@Autowired
	BoardDao dao;	
	
	@Autowired
	BoardAttachmentDao attachDao;	
	
	
	@Override
	public PageInfo<Board> getPage(int page) throws Exception {
		int totalCount = dao.count();
		PageInfo<Board> pi = new PageInfo<>(page, totalCount);
		List<Board> list = dao.getPage(pi.getStart(), pi.getEnd());
		pi.setList(list);
		return pi;
	}
	
	@Transactional
	@Override
	public Board getBoard(int boardId) throws Exception {
		dao.increaseReadCnt(boardId);
		return dao.findById(boardId);
	}

	@Transactional
	@Override
	public void create(Board board) throws Exception {
		dao.insert(board);
		
		saveFiles(board);
	}

	private void saveFiles(Board board) throws Exception {
		for(MultipartFile file : board.getFiles()) {	
			if(file.isEmpty()) continue;
			
			BoardAttachment attachment = BoardAttachment.builder()
							.boardId(board.getBoardId())
							.originalName(file.getOriginalFilename())
							.fileSize((int)file.getSize())
							.build();

			attachDao.insert(attachment);
			saveFile(file, attachment);
		}
	}
	
	private void saveFile(MultipartFile file, BoardAttachment attachment) throws Exception {
		String uploadPath = String.format(UPLOAD_PATH, attachment.getAttachmentId());
		
		File uploadFile = new File(uploadPath);
		file.transferTo(uploadFile);

	}
	@Override
	public boolean update(Board board) throws Exception {
		if(dao.update(board) != 1) return false;

		// 파일 삭제 추가
		if(board.getDeleteFiles() !=null) {
			String deleteFiles = SqlUtil.toString(board.getDeleteFiles());
			attachDao.deleteFiles(deleteFiles);
		}
		saveFiles(board);
		return true;
		
	}

	@Override
	public boolean delete(int boardId, String password) throws Exception {
		Board board = dao.findById(boardId);
		if(!board.getPassword().equals(password)) return false;
		
		// 첨부파일 삭제 
		attachDao.deleteByBoardId(boardId);	
		return dao.delete(boardId) == 1;
	}

	@Override
	public void setAttachmentFiles(Board board) throws Exception {
		board.setFileList(
				attachDao.getBoardFiles(board.getBoardId()));
		
	}
}
