package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// JDBC(Java DataBase Connectivity)라이브러리를 이용한 DB자료 처리하기
public class JdbcTest01 {
	/*
	 *  JDBC를 이용한 DB처리 순서
	 *  
	 *  1. 드라이버 로딩 -> 라이브러리를 사용할 수 있게 메모리로 읽어 들이는 작업
	 *  	-> JDBC API버전 4이상에서는 getConnection()매서드에서 장동으로 로딩해주기 때문에 
	 *  		생량 가능 
	 *  	Class.forName("oracle.jdbc.driver.OracleDriver");
	 *  
	 *  2. DB에 접속하기 -> 접속이 완료되면 Connection객체가 반환
	 *  	DriverManager.getConnection()매서드 이용.
	 *  
	 *  3. 질의 -> SQL문장을 DB서버로 보내서 처리를 하고 그 결과를 얻어온다.
	 *  
	 *  4. 결과 처리 -> 질의 결과를 받아서 원하는 작업을 수행한다.
	 *  	1) SQL문 select문일 경우에는 select한 결과가 ResultSet객체에 저장되어 반환
	 *  	2) SQL문 select문이 아닐경우 (insert,delete.update문 등)에는 정수값을 반환
	 *  
	 *  5. 사용한 자원을 반납 -> close()매서드 이용
	 */
	
	public static void main(String[] args) {
		// DB작업에 필요한 객체 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. DB 연결
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe","JJG99","java");
			// 3.질의
			// 3-1) sql문 작성하기
			String sql = "select * from lprod";
			
			// 3-2) Statement객체 또는 PreparedStatement 객체 생성
			stmt = conn.createStatement();
			
			// 3-3) sql문을 서버로 보내서 결과를 얻어온다.
			//		(실행할 sql문이 select문이기 때문에 결과가 ResultSet객체에 저장되어 반환)
			rs = stmt.executeQuery(sql);
			
			// 4. 결과 처리
			// 한 레코드 씩 처리
			
			System.out.println("== sql문 처리 결과 ==");
			
			//ResultSet객체에 저장된 데이터를 꺼내오려면 반복문과 next()매서드를 이용한다.
			
			/*
			 * rs.next() -> ResultSet 객체의 데이터를 가리키는 포인터를 다음번째 레코드 위치로
			 * 				이동 시키고 그곳에 데이터가 있으면 true 없으면 false 를 반환한다.
			 */
			while(rs.next()) {
				/*
				 *  ResultSet객체의 포인터가 가리키는 곳의 데이터를 가져오는 방법
				 *  형식1) rs.get자료형 이름("컬럼명 또는 alias명")
				 *  형식2) rs.get자료형이름(컬럼번호) => 컬럼번호는 1부터 시작
				 */
				System.out.print("Lprod_id : " + rs.getInt("lprod_id"));
				System.out.print("\tLprod_gu : " + rs.getString(2));
				System.out.print("\t\tLprod_nm : " + rs.getString("LPROD_NM"));
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
		}finally {
			// 자원 반납
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
			if(stmt!=null)try {stmt.close();}catch(SQLException e) {}
			if(conn!=null)try {conn.close();}catch(SQLException e) {}
		}
	}
}
































