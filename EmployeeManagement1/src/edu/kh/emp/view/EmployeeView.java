package edu.kh.emp.view;

import java.util.Scanner;

import edu.kh.emp.model.service.EmployeeService;

public class EmployeeView {
	
	Scanner sc = new Scanner(System.in);
	
	EmployeeService service = new EmployeeService();
	
	
	public void startMenu() {
		
		System.out.println("사원 정보 관리");
		System.out.println("1. 전체 사원 정보 보기");
		System.out.println("2. 사원 정보 검색");
		System.out.println("3. 사원 신규 등록");
		System.out.println("4. 사원 정보 수정");
		System.out.println("5. 사원 정보 삭제");
		System.out.println("0. 프로그램 종료");
		
		
		
	}
	
	
}
