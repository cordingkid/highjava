package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/*
 *  문제) Lprod_id 값을 2개를 입력 받아서 두 값 중 작은값 부터 
 */
public class JdbcTest03 {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Scanner scanner = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe","JJG99","java");
			String sql = "select * from lprod where Lprod_id between ? and ? order by 1 desc";
			pstmt = conn.prepareStatement(sql);
			
			scanner = new Scanner(System.in);
			System.out.print("첫번째 숫자>>");
			int num1 = scanner.nextInt();
			System.out.print("두번째 숫자>>");
			int num2 = scanner.nextInt();
			int min = Math.min(num1, num2);
			int max = Math.max(num1, num2);
			
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			
			rs=pstmt.executeQuery();
			System.out.println("결과 출력!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			while(rs.next()){
				System.out.print(rs.getInt("LPROD_ID")+"    ");
				System.out.print(rs.getString("LPROD_GU")+"    ");
				System.out.print(rs.getString("LPROD_NM"));
				System.out.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
