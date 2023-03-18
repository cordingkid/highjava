package kr.or.ddit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * JDBC 드라이버를 로딩하고 Connection객체를 생성해서 반환하는 매서드
 * 구성된 class 작성
 * (dbinfo.properties 파일의 내용으로 설정하는방법)
 * 
 * @author PC-09
 * 
 * ResourceBundle 객체 이용
 */
public class DBUtil3 {
	private static ResourceBundle BUNDLE;
	static {
		BUNDLE = ResourceBundle.getBundle("kr.or.ddit.config.dbinfo");
		try {
			Class.forName(BUNDLE.getString("driver"));
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!!" + e.getMessage());
		}
	}

	// Connection객체를 반환하는 매서드
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(
					BUNDLE.getString("url"),
					BUNDLE.getString("user"),
					BUNDLE.getString("pass"));
		} catch (SQLException e) {
			System.out.println("DB 연결 실패!!!" + e.getMessage());
		}
		return null;
	}
}
