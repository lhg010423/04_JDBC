package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.commit;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;
import static edu.kh.jdbc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.dto.Board;
import edu.kh.jdbc.board.model.dto.Comment;

public class BoardService {
	
	private BoardDAO dao = new BoardDAO();

	private CommentDAO commentDao = new CommentDAO();
	
	
	
	/** 게시글 목록 조회 서비스
	 * @return boardList
	 */
	public List<Board> selectAllBoard() throws Exception{
		
		// 커넥션 생성
		Connection conn = getConnection();
		
		// DAO 메서드 호출
		List<Board> boardList = dao.selectAllBoard(conn);
		
		// 커넥션 반환
		close(conn);
		
		// 결과 반환
		return boardList;
	}


	/** 게시글 상세 조회 서비스
	 * @param input
	 * @param memberNo
	 * @return
	 */
	public Board selectBoard(int input, int memberNo) throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		// 2. 게시글 상세 조회 DAO 메서드 호출
		Board board = dao.selectBoard(conn, input);
		
		// 3. 게시글이 조회된 경우
		if(board != null) {
			
			// *************************************************************
			// ** 해당 게시글에 대한 댓글 목록 조회 DAO 호출 **
			List<Comment> commentList = commentDao.selectCommentList(conn, input);
																// input == 게시글 번호
			// board에 댓글 목록 세팅
			board.setCommentList(commentList);
			
			// 위 코드 2줄로 이 메서드 맨아래 return board에 댓글목록 조회할수있는 기능이 추가된다
			
			// *************************************************************
			
			
			
			// 4. 조회수 증가
			// 단, 게시글 작성자와 로그인한 회원이 다를 경우에만 증가
			if(board.getMemberNo() != memberNo) {
			// 조회한 게시글 작성한 회원번호 != 뷰에서가져온 로그인한 회원번호
				
				// 5. 조회 수 증가 DAO 메서드 호출 (UPDATE)
				int result = dao.updateReadCount(conn, input);
				
				// DML썼으니 트랜잭션 제어 처리를 한다
				// 6. 트랜잭션 제어 처리 + 데이터 동기화 처리
				if(result > 0) {
					commit(conn);
					
					// 조회된 board의 조회수 0
					// DB의 조회수는 1
					// -> 조회 결과인 board의 조회수도 1증가
					board.setReadCount( board.getReadCount() + 1);
					
				} else {
					rollback(conn);
				}
				
			}
			
		}
		
		// 7. 커넥션 반환
		close(conn);
		
		// 8. 결과 반환
		return board;
	}


	/** 게시글 수정 서비스
	 * @param boardTitle
	 * @param string
	 * @param boardNo
	 * @return
	 */
	public int updateBoard(String boardTitle, String boardContent, int boardNo) throws Exception{
		
		Connection conn = getConnection();
		
		// 게시글 수정 DAO 호출
		int result = dao.updateBoard(conn, boardTitle, boardContent, boardNo);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}


	/** 게시글 삽입 서비스
	 * @param boardTitle
	 * @param string
	 * @param memberNo
	 * @return result
	 */
	public int insertBoard(String boardTitle, String boardContent, int memberNo) throws Exception{
		
		Connection conn = getConnection();
		
		// 다음 게시글 번호 생성 dao 호출
		int boardNo = dao.nextBoardNo(conn);
		
		// 제목, 내용, 회원번호 + 다음 게시글번호
		int result = dao.inserBoard(conn, boardTitle, boardContent, memberNo, boardNo);
		
		if(result > 0) {
			commit(conn);
			result = boardNo; // insert 삽입 성공 시 현재 삽입된 게시글 번호를 반환
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result; // 삽입 성공 시 현재 삽입된 게시글 번호
						// 실패 시 0
	}

}
