package common;

import java.sql.Connection;

public class JDBCTemplate {

	private static Connection conn = null;
	
	public static Connection getConnection() {
		
		try {
			
			if(conn == null || conn.isClosed()) {
				
				
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
