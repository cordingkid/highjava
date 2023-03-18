package kr.or.ddit.basic;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JdbcTest04 {

	//bankinfo 테이블에 계좌번호 정보를 추가하는 예제
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe","JJG99","java");
			
			System.out.println("계좌 번호 정보 추가하기");
			System.out.print("계좌 번호 입력 >>");
			String bankNo = scanner.next();
			
			System.out.print("은행명 입력>>");
			String bankNmae = scanner.next();
			
			System.out.print("예금주명 입력>>");
			String userName = scanner.next();
			
			//Statement 객체 사용하여 데이터 추가
//			String sql = "insert into bankinfo (bank_no, bank_name, bank_user_name, bank_date) "
//					+ "values ('"+bankNo + "', '"+bankNmae+"', '"+userName+"', sysdate)";
//			
//			System.out.println(sql);
//			
//			stmt = conn.createStatement();
			 
			/*
			 *  select 문을 실행할때는 executeQuery()매서드를 사용하고,
			 *  select  문이 아닌 문장 (insert,update,delete 등)을 
			 *  실행할 때는 executeUpdate() 매서드를 사용
			 *  
			 *  executeUpdate()매서드 의 반환값 -> 작업에 성공한 레코드 수
			 */
//			int update = stmt.executeUpdate(sql);
			
			
			String sql = "insert into bankinfo (bank_no, bank_name, bank_user_name, bank_date) "
					+ "values (?, ?, ?, sysdate)";
			// prepareStatement 객체 생성 -> 사용할 sql 문을 인수값으로 넘겨 준다.
			pstmt = conn.prepareStatement(sql);
			
			// sql의 물음표 자리에 들어갈 데이터를 셋팅한다.
			
			pstmt.setString(1,bankNo);
			pstmt.setString(2,bankNmae);
			pstmt.setString(3,userName);
			
			// 데이터의 셋팅이 완료되면 실행한다
			// select 문일 경우 => executeQuery() 매서드 사용
			// select 문이 아닐 경우 -> executeUpdate() 매서드 사용
			int cnt = pstmt.executeUpdate();
			
			
			
			
			System.out.println("반환값 : " + cnt);
			
			
			
		} catch (SQLException e) {
			// TODO: handle exception
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
		}finally {
			if (stmt != null)try {stmt.close();}catch (Exception e2) {}
			if (pstmt != null)try {pstmt.close();}catch (Exception e2) {}
			if (conn != null)try {conn.close();}catch (Exception e2) {}
		}
	}
}



































