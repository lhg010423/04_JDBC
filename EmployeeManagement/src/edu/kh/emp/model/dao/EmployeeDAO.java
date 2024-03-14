package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.emp.common.JDBCTemplate;
import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public EmployeeDAO() {
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("query.xml"));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public int insert(Connection conn, Employee vo1) {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("insert");
			
			pstmt = conn.prepareStatement(sql);
			
			
			
			
			
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
