package kr.or.ddit.mvc.service;

import java.util.List;

import kr.or.ddit.mvc.dao.IMemberDAO;
import kr.or.ddit.mvc.dao.MemberDaoImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {
	// 일을 시킬 DAO 객체 변수 선언
	private IMemberDAO dao;
	
	// 생성자
	public MemberServiceImpl() {
		dao = new MemberDaoImpl(); // DAO객체 생성
	}
	
	@Override
	public int insertMember(MemberVO memVO) {
		return dao.insertMember(memVO);
	}

	@Override
	public int deleteMember(String memId) {
		return dao.deleteMember(memId);
	}

	@Override
	public int updateMember(MemberVO memVO) {
		return dao.updateMember(memVO);
	}

	@Override
	public List<MemberVO> getAllMemeber() {
		return dao.getAllMemeber();
	}

	@Override
	public int getMemberCount(String memId) {
		return dao.getMemberCount(memId);
	}

}
