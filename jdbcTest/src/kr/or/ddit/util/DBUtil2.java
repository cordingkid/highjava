package kr.or.ddit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC 드라이버를 로딩하고 Connection객체를 생성해서 반환하는 매서드
 * 구성된 class 작성
 * (dbinfo.properties 파일의 내용으로 설정하는방법)
 * 
 * @author PC-09
 * 
 */
public class DBUtil2 {
	private static Properties PROP; //Properties 객체 변수 선언
	static {
		PROP = new Properties();
		File file = new File("res/kr/or/ddit/config/dbinfo.properties");
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream(file);
			PROP.load(fin);
			Class.forName(PROP.getProperty("driver"));
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!!" + e.getMessage());
		} catch (IOException e) {
			System.out.println("입출력 오류... : 드라이 로딩 실패!!");
			e.printStackTrace();
		}finally {
			if(fin!=null)try {fin.close();}catch(IOException e) {}
		}
	}

	// Connection객체를 반환하는 매서드
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(
					PROP.getProperty("url"), 
					PROP.getProperty("user"), 
					PROP.getProperty("pass"));
		} catch (SQLException e) {
			System.out.println("DB 연결 실패!!!" + e.getMessage());
		}
		return null;
	}
}
