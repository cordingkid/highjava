package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/*
 *  문제) 사용자로부터 Lprod_id값을 입력받아 
 *  입력한 값보다 lprod_id가 큰 자료들을 출력하시오 
 */
public class JdbcTest02 {

	public static void main(String[] args) {
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt =null;
		Scanner scanner;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe","JJG99","java");
			
			stmt = conn.createStatement();
			
			scanner = new Scanner(System.in);
			System.out.print("숫자 입력>>");
			String sql = "select * from lprod where Lprod_id >";
			sql+=scanner.nextLine();
			rs = stmt.executeQuery(sql);
			System.out.println("== sql문 처리 결과 ==");
			while(rs.next()) {
				System.out.print("Lprod_id : " + rs.getInt("lprod_id"));
				System.out.print("\tLprod_gu : " + rs.getString(2));
				System.out.print("\t\tLprod_nm : " + rs.getString("LPROD_NM"));
				System.out.println();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
