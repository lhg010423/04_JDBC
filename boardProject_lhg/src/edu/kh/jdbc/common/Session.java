package edu.kh.jdbc.common;

import edu.kh.jdbc.member.model.dto.Member;

public class Session {

	// 로그인 : DB에 기록된 회원 정보를 가지고 오는 것.
	//			-> 로그아웃을 할 때 까지 프로그램에서 회원 정보가 유지
	
	// static 한 이유 : 로그인 했다는 기록을 다른곳에서도 쓰기 위해, 안하면 다른페이지 이동할때마다 로그인해야함
	public static Member loginMember = null;
	
	// loginMember == null -> 로그아웃 상태
	// loginMember != null -> 로그인 상태
	
	
	
}
