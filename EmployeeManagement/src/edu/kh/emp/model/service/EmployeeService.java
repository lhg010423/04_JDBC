package edu.kh.emp.model.service;

import java.sql.Connection;
import java.sql.SQLException;

import edu.kh.emp.common.JDBCTemplate;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

public class EmployeeService {

	private EmployeeDAO dao = new  EmployeeDAO();

	public int insert(Employee vo1) throws SQLException {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.insert(conn, vo1);
		
		if(result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
		
		
	}
	
	
	
}
