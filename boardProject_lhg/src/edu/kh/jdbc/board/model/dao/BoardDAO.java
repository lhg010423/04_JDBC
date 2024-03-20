package edu.kh.jdbc.board.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.board.model.dto.Board;

public class BoardDAO {
	
	// JDBC 객체 참조 변수
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// XML에 작성된 SQL을 읽어와 저장할 객체를 참조하는 변수
	private Properties prop;
	
	public BoardDAO() { // 기본생성자
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("board-sql.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 게시글 목록 조회 SQL 수행 DAO
	 * @param conn
	 * @return boardList
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception{
		
		// 결과 저장용 객체 생성
		List<Board> boardList = new ArrayList<Board>();
		
		try {
			// SQL 작성
			String sql = prop.getProperty("selectAllBoard");
			
			// SQL 수행 후 결과 반환받기
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			// 1행씩 접근하여 컬럼값을 얻어와 옮겨담기
			while(rs.next()) {
				
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String memberName = rs.getString("MEMBER_NM");
				String createDate = rs.getString("CREATE_DT");
				int readCount = rs.getInt("READ_COUNT");
				int commentCount = rs.getInt("COMMENT_COUNT");
				
				Board board = new Board();
				
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setMemberName(memberName);
				board.setReadCount(readCount);
				board.setCreateDate(createDate);
				board.setCommentCount(commentCount);
				
				boardList.add(board);
				
			}
			
		} finally {
			close(rs);
			close(stmt);
			
		}
		
		
		
		return boardList;
	}

	/** 게시글 상세 조회 SQL 수행 DAO
	 * @param conn
	 * @param input
	 * @return board
	 */
	public Board selectBoard(Connection conn, int input) throws Exception{
		
		Board board = null;
		
		try {
			String sql = prop.getProperty("selectBoard");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// int boardNo = rs.getInt("BOARD_NO");
				
				board = new Board();
				
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("Board_TITLE"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCreateDate(rs.getString("CREATE_DT"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setMemberNo(rs.getInt("MEMBER_NO"));
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return board;
	}

	/** 조회수 증가 SQL 수행 DAO
	 * @param conn
	 * @param input
	 * @return result
	 */
	public int updateReadCount(Connection conn, int input) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateReadCount");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return 0;
	}

	
	/** 게시글 수정 SQL 수행
	 * @param conn
	 * @param boardTitle
	 * @param boardContent
	 * @param boardNo
	 * @return
	 */
	public int updateBoard(Connection conn, String boardTitle, String boardContent, int boardNo) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateBoard");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	/** 다음 게시글 번호 조회 SQL 수행 DAO
	 * @param conn
	 * @return boardNo
	 */
	public int nextBoardNo(Connection conn) throws Exception{
		
		int boardNo = 0;
		
		try {
			String sql = prop.getProperty("nextBoardNo");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				boardNo = rs.getInt(1);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return boardNo;
	}

	
	/** 게시글 삽입 DAO
	 * @param conn
	 * @param boardTitle
	 * @param boardContent
	 * @param memberNo
	 * @param boardNo
	 * @return result
	 */
	public int inserBoard(Connection conn, String boardTitle,
			String boardContent, int memberNo, int boardNo) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertBoard");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardContent);
			pstmt.setInt(4, memberNo);
			// VALUES(?, ?, ?, DEFAULT, DEFAULT, DEFAULT, ?)
			//
			/*
			COMMENT ON COLUMN "BOARD"."BOARD_NO" IS '게시글 번호 (시퀀스 SEQ_BOARD_NO)';
			COMMENT ON COLUMN "BOARD"."BOARD_TITLE" IS '게시글 제목';
			COMMENT ON COLUMN "BOARD"."BOARD_CONTENT" IS '게시글 내용';
			COMMENT ON COLUMN "BOARD"."CREATE_DT" IS '작성일';
			COMMENT ON COLUMN "BOARD"."READ_COUNT" IS '조회수';
			COMMENT ON COLUMN "BOARD"."DELETE_FL" IS '삭제여부(Y/N)';
			COMMENT ON COLUMN "BOARD"."MEMBER_NO" IS '회원번호(FK)';
			 */
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
}
