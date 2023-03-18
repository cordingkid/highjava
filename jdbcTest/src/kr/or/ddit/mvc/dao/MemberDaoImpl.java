package kr.or.ddit.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.util.DBUtil3;
import kr.or.ddit.vo.MemberVO;

public class MemberDaoImpl implements IMemberDAO {
	
	@Override
	public int insertMember(MemberVO memVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "insert into mymember values(?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memVO.getMem_id());
			pstmt.setString(2, memVO.getMem_pass());
			pstmt.setString(3, memVO.getMem_name());
			pstmt.setString(4, memVO.getMem_tel());
			pstmt.setString(5, memVO.getMem_addr());
			
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null)try {pstmt.close();}catch (SQLException e) {}
			if(conn!=null)try {conn.close();}catch (SQLException e) {}
		}
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		try {
			conn = DBUtil3.getConnection();
			String sql = "delete from mymember where MEM_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)try {pstmt.close();}catch (SQLException e) {}
			if(conn!=null)try {conn.close();}catch (SQLException e) {}
		}
		return cnt;
	}

	@Override
	public int updateMember(MemberVO memVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "update mymember "
					+ "set "
					+ "MEM_PASS = ?, "
					+ "MEM_NAME = ?, "
					+ "MEM_TEL = ?, "
					+ "MEM_ADDR = ? "
					+ "where MEM_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVO.getMem_pass());
			pstmt.setString(2, memVO.getMem_name());
			pstmt.setString(3, memVO.getMem_tel());
			pstmt.setString(4, memVO.getMem_addr());
			pstmt.setString(5, memVO.getMem_id());
			
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null)try {pstmt.close();}catch (SQLException e) {}
			if(conn!=null)try {conn.close();}catch (SQLException e) {}
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemeber() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVO> list =new ArrayList<>();
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "select * from mymember";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setMem_id(rs.getString(1));
				vo.setMem_pass(rs.getString(2));
				vo.setMem_name(rs.getString(3));
				vo.setMem_tel(rs.getString(4));
				vo.setMem_addr(rs.getString(5));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null)try {pstmt.close();}catch (SQLException e) {}
			if(rs!=null)try {rs.close();}catch (SQLException e) {}
			if(conn!=null)try {conn.close();}catch (SQLException e) {}
		}
		return list;
	}

	@Override
	public int getMemberCount(String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int cnt = 0;
		try {
			conn = DBUtil3.getConnection();
			String sql = "select count(*) from mymember where MEM_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();} catch (SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch (SQLException e) {}
			if(conn!=null)try {conn.close();}catch (SQLException e) {}
		}
		return cnt;
	}

}

