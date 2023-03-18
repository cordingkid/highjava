package kr.or.ddit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC 드라이버를 로딩하고 Connection객체를 생성해서 반환하는 매서드
 * 구성된 class 작성
 * 
 * @author PC-09
 */
public class DBUtil {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!!" + e.getMessage());
		}
	}

	// Connection객체를 반환하는 매서드
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "JJG99", "java");
		} catch (SQLException e) {
			System.out.println("DB 연결 실패!!!" + e.getMessage());
		}
		return null;
	}
}
