package edu.kh.emp.model.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

public class EmployeeService {
	
	private EmployeeDAO dao = new EmployeeDAO();

	/** 전체 사원 정보 조회 서비스
	 * @return list
	 */
	public List<Employee> selectAll() throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectAll(conn); // conn으로 sptmt 같은거 3개 만듬
		
		close(conn);
		
		
		
		return list;
	}

	/** 사원 정보 추가 서비스
	 * @param emp
	 * @return result (1/0)
	 */
	public int insertEmployee(Employee emp) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.insertEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		
		return result;
	}

	/** 사번이 일치하는 사원 정보 조회 서비스
	 * @param empId
	 * @return emp<Emplopyee>
	 * @throws Exception
	 */
	public Employee selectEmpId(int empId) throws Exception {
		
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpId(conn, empId);
		
		close(conn);
		
		
		return emp;
	}

	/** 사번이 일치하는 사원 정보 수정 서비스
	 * @param emp
	 * @return result
	 */
	public int updateEmployee(Employee emp) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.updateEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 사번이 일치하는 사원 정보 삭제 서비스
	 * @param empId
	 * @return
	 */
	public int deleteEmployee(int empId) throws Exception{

		Connection conn = getConnection();
		
		int result = dao.deleteEmployee(conn, empId);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

	
	/** 입력 받은 부서와 일치하는 모든 사원 정보 조회
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public List<Employee> selectDeptEmp(String input) throws Exception{
		
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectDeptEmp(conn, input); // conn으로 sptmt 같은거 3개 만듬
		
		close(conn);
		
		return list;
	}

	/** 입력 받은 급여 이상을 받는 모든 사원 정보 조회
	 * @param input
	 * @return
	 */
	public List<Employee> selectSalaryEmp(int input) throws Exception{
		
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectSalaryEmp(conn, input);
		
		close(conn);
		
		return list;
	}

	/** 부서별 급여 합 전체 조회
	 * @return
	 */
	public Map<String, Integer> selectDeptTotalSalary() throws Exception{

		Connection conn = getConnection();
		
		Map<String, Integer> map = dao.selectDeptTotalSalary(conn);
		
		close(conn);
		
		return map;
	}

	/** 주민등록번호가 일치하는 사원 정보 조회
	 * @param input
	 * @return
	 */
	public List<Employee> selectEmpNo(String input) throws Exception{
		
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectEmpNo(conn, input);
		
		close(conn);
		
		
		return list;
	}

	/** 직급별 급여 평균 조회
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Integer> selectJobAvgSalary() throws Exception {
		
		Connection conn = getConnection();
		
		Map<String, Integer> map = dao.selectJobAvgSalary(conn);
		
		close(conn);
		
		return map;
	}
	
	
}
