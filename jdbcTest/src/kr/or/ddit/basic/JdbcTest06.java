package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.util.DBUtil3;

/*
 *  회원을 관리하는 프로그램을 작성
 *  (mymember 테이블 이용) 
 *  
 *  아래의 메뉴를 모두 구현하시오 (CRUD 기능 구현하기)
 *  1. 자료 추가			c
 *  2. 자료 삭제			d
 *  3. 자료 수정			u
 *  4. 전체 자료 출력		r
 *  0. 종료
 *  
 *  조건)
 *  1) 자료 추가에서 회원아이디는 중복되지 않는다.(중복되면 다시 입력 받기)
 *  2) 자료 삭제는 회원아이디로 처리
 *  3) 자료 수정은 회원아이디만 빼고 모두 변경 
 */
public class JdbcTest06 {
	Scanner scan;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		new JdbcTest06().start();
	}
	
	public JdbcTest06() {
		scan = new Scanner(System.in);
	}
	private void disConnect() {
		if(rs!=null)try {rs.close();} catch (SQLException e) {}
		if(pstmt!=null)try {pstmt.close();} catch (SQLException e) {}
		if(conn!=null)try {conn.close();} catch (SQLException e) {}
	}
	public void start() {
		while (true) {
			int choice = show();
			switch (choice) {
			case 1: insert(); break;
			case 2: delete(); break;
			case 3: update(); break;	//수정 1
			case 4: printAll(); break;
			case 5: updateMember(); break;	//수정 2
			case 6: updateMember3(); break;	//수정 3
			case 0: exit(); break;
			}
		}
	}
	private void updateMember3() {
		try {
			conn = DBUtil.getConnection();
			System.out.print("수정할 사람의 id 입력>>");
			String id = scan.nextLine().trim();
			if (getMemberCount(id) == 0) {
				System.out.println("해당 아이디는 존재하지 않습니다.");
				return;
			}
			String sql = "update mymember set ";
			Map<String,String> map = new HashMap<>();
			
			System.out.print("수정할 비밀번호 입력>>");
			String pass = scan.nextLine().trim();
			if(!"".equals(pass)) {
				map.put("MEM_PASS",pass);
			}
			
			System.out.print("수정할 이름 입력>>");
			String name = scan.nextLine().trim();
			if(!"".equals(name)) {
				map.put("MEM_NAME",name);
			}
			
			System.out.print("수정할 전화번호 입력>>");
			String tel = scan.nextLine().trim();
			if(!"".equals(tel)) {
				map.put("MEM_TEL",tel);
			}
			
			System.out.print("수정할 주소 입력>>");
			String addr = scan.nextLine().trim();
			if(!"".equals(addr)) {
				map.put("MEM_ADDR",addr);
			}
			String temp = "";
			for (String key : map.keySet()) {
				if(!"".equals(temp)) {
					temp +=",";
				}
				temp += key + " = '" + map.get(key) + "' ";
			}
			if(temp.equals("")) {
				System.out.println("변경될 데이터가 없습니다.");
				return;
			}
			sql+=temp+ " where MEM_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("변경 성공!");
			} else {
				System.out.println("변경 실패!");
			}
		} catch (SQLException e) {e.printStackTrace();}
		finally {disConnect();}
	}

	private void updateMember() {
		try {
			conn = DBUtil.getConnection();
			System.out.print("수정할 사람의 id 입력>>");
			String id = scan.nextLine().trim();
			if (getMemberCount(id) == 0) {
				System.out.println("해당 아이디는 존재하지 않습니다.");
				return;
			}
			while (true) {
				System.out.println("1. 패스워드 변경");
				System.out.println("2. 이름 변경");
				System.out.println("3. 전화번호 변경");
				System.out.println("4. 주소 변경");
				System.out.println("0. 변경 그만하기");
				System.out.print("선택>>");
				int choice = Integer.parseInt(scan.nextLine().trim());
				
				String sql = "update mymember set ";
						
				String update = "";
				switch (choice) {
				case 1:
					sql+="MEM_PASS = ? ";
					System.out.print("변경할 패스워드 입력>");
					update = scan.nextLine().trim();
					break;
				case 2:
					sql+="MEM_NAME = ? ";
					System.out.print("변경할 이름 입력>");
					update = scan.nextLine().trim();
					break;
				case 3:
					sql+="MEM_TEL = ? ";
					System.out.print("변경할 전화번호 입력>");
					update = scan.nextLine().trim();
					break;
				case 4:
					sql+="MEM_ADDR = ? ";
					System.out.print("변경할 주소 입력>");
					update = scan.nextLine().trim();
					break;
				case 0:
					return;
				}
				sql +=" where MEM_ID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, update);
				pstmt.setString(2, id);
				int cnt = pstmt.executeUpdate();
				if(cnt>0) {
					System.out.println("성공적으로 변경되었습니다.");
				}else {
					System.out.println("변경 실패");
				}
			}
			
		} catch (SQLException e) {}
		finally {disConnect();}
	}
	private void exit() {
		System.out.println("프로그램 종료");
		System.exit(0);
	}
	private void printAll() {
		try {
//			conn = DBUtil.getConnection();	그냥 연결
//			conn = DBUtil2.getConnection();	Properties객체 사용해서 연결
			conn = DBUtil3.getConnection();	// ResourceBundle객체 사용해서 연결 제일간단함...
			
			String sql = "select * from mymember";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("===================== 회원 전체 목록 ================");
			System.out.println("  아이디    비밀번호  이름  번호    주소");
			System.out.println("=====================================================");
			while(rs.next()) {
				System.out.print(rs.getString(1)+"   ");
				System.out.print(rs.getString(2)+"   ");
				System.out.print(rs.getString(3)+"   ");
				System.out.print(rs.getString(4)+"   ");
				System.out.print(rs.getString(5)+"\n");
			}
		} catch (SQLException e) {}
		finally {disConnect();}
	}

	private void update() {
		try {
			conn = DBUtil.getConnection();
			System.out.print("수정할 사람의 id 입력>>");
			String id = scan.nextLine().trim();
			if (getMemberCount(id) == 0) {
				System.out.println("해당 아이디는 존재하지 않습니다.");
				return;
			}
			System.out.print("수정할 비밀번호 입력>>");
			String pass = scan.nextLine().trim();
			System.out.print("수정할 이름 입력>>");
			String name = scan.nextLine().trim();
			System.out.print("수정할 전화번호 입력>>");
			String tel = scan.nextLine().trim();
			System.out.print("수정할 주소 입력>>");
			String addr = scan.nextLine().trim();
			
			String sql = "update mymember "
					+ "set "
					+ "MEM_PASS = ?, "
					+ "MEM_NAME = ?, "
					+ "MEM_TEL = ?, "
					+ "MEM_ADDR = ? "
					+ "where MEM_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pass);
			pstmt.setString(2, name);
			pstmt.setString(3, tel);
			pstmt.setString(4, addr);
			pstmt.setString(5, id);
			
			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("변경 성공!");
			} else {
				System.out.println("변경 실패!");
			}
		} catch (SQLException e) {e.printStackTrace();}
		finally {disConnect();}

	}

	private void delete() {
		try {
			conn = DBUtil.getConnection();
			System.out.print("삭제할 사람의 id를 입력해주세요>>");
			String id = scan.nextLine().trim();
			if (getMemberCount(id) == 0) {
				System.out.println("해당 아이디는 존재하지 않습니다.");
				return;
			}
			String sql = "delete from mymember where MEM_ID = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("성공적으로 삭제하엿습니다.");
			} else {
				System.out.println("삭제 실패");
			}
		} catch (SQLException e) {}
		finally {disConnect();}

	}

	private void insert() {
		try {
			conn = DBUtil.getConnection();
			while (true) {
				System.out.print("아이디 입력>>");
				String id = scan.nextLine().trim();
				if (getMemberCount(id) != 0) {
					System.out.println("중복되는 아이디 입니다 다시입력해주세여.");
					continue;
				}
				System.out.print("비밀번호 입력>>");
				String pass = scan.nextLine().trim();
				System.out.print("이름 입력>>");
				String name = scan.nextLine().trim();
				System.out.print("전화번호 입력>>");
				String tel = scan.nextLine().trim();
				System.out.print("주소 입력>>");
				String addr = scan.nextLine().trim();

				String sql = "insert into mymember values(?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, pass);
				pstmt.setString(3, name);
				pstmt.setString(4, tel);
				pstmt.setString(5, addr);
				if (pstmt.executeUpdate() == 1) {
					System.out.println("성공적으로 저장되었습니다");
					break;
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		finally {disConnect();}
	}
	
	private int getMemberCount(String id) throws SQLException{
		conn = DBUtil.getConnection();
		int cnt = 0;
		String sql = "select count(*) from mymember where MEM_ID = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			cnt = rs.getInt(1);
		}
		return cnt;
	}

	public int show() {
		System.out.println("=== 회원 관리 프로그램 ===");
		System.out.println("1. 자료 추가");
		System.out.println("2. 자료 삭제");
		System.out.println("3. 자료 수정(전체항목수정)");
		System.out.println("4. 전체 자료 출력");
		System.out.println("5. 자료 수정(수정항목선택)");
		System.out.println("6. 자료 수정(입력항목선택)");
		System.out.println("0. 종료");
		System.out.print("선택>>");
		return Integer.parseInt(scan.nextLine().trim());
	}
}
