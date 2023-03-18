package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;

/*
 *  lprod 테이블에 새로운 데이터 추가하기
 *  
 *  Lprod_gu와 lprod_nm 은 직접 입력 받아서 처리하고,
 *  lprod_id는 현재의 lprod_id 중에서 제일 큰 값보다 1크게 한다.
 *  
 *  입력받은 lprod_gu가 이미 등록되어 있으면 다시 입력받아서 처리한다.
 */
public class JdbcTest05 {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		Scanner scanner = new Scanner(System.in);
		try {
			conn = DBUtil.getConnection();
			
			
			while(true) {
				System.out.print("Lprod_gu (ex: P102) 입력 >>");
				String gu = scanner.next();
				System.out.print("Lprod_nm (제품이름) 입력 >>");
				String nm = scanner.next();
				
				String sql = "select count(*) from lprod where lprod_gu = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, gu);
				
				rs = pstmt.executeQuery();
				int cnt = 0;
				while (rs.next()) {
					cnt = rs.getInt(1);
				}
				if (cnt == 0) {
					String sql2 = "insert into lprod values((select max(lprod_id)+1 from lprod),?,?)";
					pstmt = conn.prepareStatement(sql2);
					pstmt.setString(1, gu);
					pstmt.setString(2, nm);
					
					int i = pstmt.executeUpdate();
					System.out.println("데이터 삽입 성공 : " + i );
					String all = "select * from lprod order by 1";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(all);
					System.out.println("===전체 데이터 출력==");
					while(rs.next()) {
						System.out.print(rs.getInt(1) +"\t"); 
						System.out.print(rs.getString(2)+"\t"); 
						System.out.println(rs.getString(3)); 
					}
					break;
				}
				System.out.print("Lprod_gu (ex: P102) 가 ");
				System.out.println("중복됩니다 다시 입력해주세요.");
				System.out.println();
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
		}finally {
			if(rs!=null)try {rs.close();} catch (Exception e2) {}
			if(stmt!=null)try {stmt.close();} catch (Exception e2) {}
			if(pstmt!=null)try {pstmt.close();} catch (Exception e2) {}
			if(conn!=null)try {conn.close();} catch (Exception e2) {}
		}
		
	}
}





