package edu.autocar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import edu.autocar.domain.Board;
import edu.autocar.domain.Gallery;

public interface BoardDao extends CrudDao<Board, Integer> {
	
	final static String PAGING_SUBQUERY = 
			"select  row_number() over (order by board_id desc) as seq, board.* from board";

	@Select("select count(*) from board")
	int count() throws Exception;

	@Select("select * from (" + PAGING_SUBQUERY + ") where seq  between  #{start} and #{end}")
	@Results(id="Board", value={
		@Result(property="boardId", column="board_id"),
		@Result(property="fileList"		, column="board_id",
				many=@Many(select = "edu.autocar.dao.BoardAttachmentDao.getBoardFiles"))
	})	
	List<Board> getPage(@Param("start") int start, @Param("end") int end) throws Exception;

	@Select("select * from board where board_id=#{boardid}")
	@ResultMap("Board")
	Board findById(int boardId) throws Exception;

	@Update("update board set read_cnt = read_cnt + 1 where board_id=#{boardid}")
	void increaseReadCnt(Integer boardId) throws Exception;
	 	
	@InsertProvider(type=BoardSQL.class, method="insert")
	@Options(useGeneratedKeys=true, keyColumn="board_id", keyProperty="boardId")
	int insert(Board board) throws Exception;

	@Update("UPDATE board SET title = #{title}, content = #{content}, update_date = sysdate" + 
			"	WHERE board_id=#{boardId} and password=#{password}")
	int update(Board board) throws Exception;

	@Delete("delete from board where board_id=#{boardId}")	
	int delete(int boardId) throws Exception;
	
	public static class BoardSQL{
		public static String insert(Board board) {
			return new SQL() {{
				INSERT_INTO("board");
				VALUES("board_id", "BOARD_SEQ.NEXTVAL");
			    VALUES("title, writer, password, content", 
			    	"#{title}, #{writer}, #{password}, #{content}");			    
			  }}.toString();	
		}
	}	
}
