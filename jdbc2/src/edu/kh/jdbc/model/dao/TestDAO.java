package edu.kh.jdbc.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.vo.TestVO;

public class TestDAO {
	// DAO (Date Access Object) : 데이터가 저장된 DB에 접근하는 객체
	//				-> SQL 수행, 결과 반환 받는 기능을 수행
	
	// JDBC 객체를 참조하기 위한 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// xml로 SQL을 다룰것이다 -> Properties 객체 사용
	private Properties prop;
	
	
	// 기본생성자
	public TestDAO() {
		// testDAO 객체 생성시
		// test-query.xml 파일의 내용을 읽어와
		// Properties 객체에저장
		
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("test-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public int insert(Connection conn, TestVO vo1) throws SQLException {
		
		// 1. 결과 저장용 변수 선언
		// DML은 결과값을 1행, 0행 처럼 int형으로 나온다
		int result = 0;
		
		try {
			
			// 2. SQL 작성(test-query.xml에 작성된 SQL 얻어오기)
			String sql = prop.getProperty("insert");
			// INSERT INTO TB_TEST
			// VALUES(?, ?, ?)
			
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// conn.createStatement();
			// conn.prepareStatement(); 차이점 외우기
			
			// 4. 위치 홀더(?)에 알맞은 값 세팅
			pstmt.setInt(1, vo1.getTestNo()); // 1
			pstmt.setString(2, vo1.getTestTitle()); // "제목1"
			pstmt.setString(3, vo1.getTestContent()); // "내용1"
			
			// 5. SQL(INSERT) 수행 후 결과 반환받기
			result = pstmt.executeUpdate(); // DML 수행, 반영된 행의 개수(int) 반환
			
			
			//---------------------------------------------
			
			// stmt = conn.createStatement();
			// stmt.executeQuery(sql);
			
			//---------------------------------------------
			
			// pstmt = conn.prepareStatement(sql);
			// pstmt.executeUpdate(); // sql 다시 넣으면 안된다!!
			
			//---------------------------------------------
			
			
		} finally {
			// 6. 사용한 JDBC 객체 자원 반환
			JDBCTemplate.close(pstmt);
			// 여기서 Connection 닫으면 안됨 Service에서 더 써야함
			
		}
		
		// 7. SQL 수행 결과 반환
		return result;
		
		
		
		
	}



	/** 번호가 일치하는 행이 제모ㄱ, 내용을 수정 DAO
	 * @param conn
	 * @param vo
	 * @return result
	 */
	public int update(Connection conn, TestVO vo) throws Exception{
		
		// 결과 저장용 변수
		int result = 0;
		
		try {
			String sql = prop.getProperty("update");
			
			/*
			UPDATE TB_TEST SET
			TEST_TITLE = ?,
			TEST_CONTENT = ?
			WHERE TEST_NO = ?
			*/
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTestTitle());
			pstmt.setString(2, vo.getTestContent());
			pstmt.setInt(3, vo.getTestNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			JDBCTemplate.close(pstmt);
			
		}
		
		return result;
	}



	public int delete(Connection conn, int testNo) throws Exception{

		int result = 0;
		
		try {
			String sql = prop.getProperty("delete");
			/*	DELETE FROM TB_TEST
			  	WHERE TEST_NO = ?
			*/
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, testNo);
			
			result = pstmt.executeUpdate(); // 결과값 1 / 0
			
			
		} finally {
			JDBCTemplate.close(pstmt);
			
			
		}
		
		
		
		
		
		
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
