package kr.or.ddit.mvc.service;

import java.util.List;

import kr.or.ddit.vo.MemberVO;

/**
 * Service객체는 DAO에 만들어진 메서드를 원하는 작업에 맞게 호출하여
 * 결과를 받아오고, 받아온 결과를 Controller에게 보내주는 역할을 한다.
 * 
 * (자바고급과정에서는 보통 DAO와 매서드의 구조가 같게 한다.)
 * 
 * @author PC-09
 *
 */
public interface IMemberService {
	/**
	 * MemberVO에 담겨진 자료를 DB에 insert하는 매서드
	 * 
	 * @param memVO insert할 데이터가 저장된 MemberVO객체
	 * @return 작업 성공: 1, 작업 실패 : 0
	 */
	public int insertMember(MemberVO memVO);
	
	/**
	 * 회원ID를 매개변수로 받아 해당 회원 정보를 삭제하는 매서드
	 * 
	 * @param memId 삭제할 회원ID
	 * @return 삭제 성공 : 1, 삭제 실패 : 0
	 */
	public int deleteMember(String memId);
	
	/**
	 * MemberVO객체에 담겨진 자료를 이용하여 DB에 update하는 매서드
	 * 
	 * @param memVO update할 회원 정보가 저장된 MemberVO객체
	 * @return 작업 성공 : 1 , 작업 실패 : 0
	 */
	public int updateMember(MemberVO memVO);
	
	/**
	 * DB 전체 회원 정보를 가져와서 List에 담아서 반환하는메서드
	 * @return MemberVO객체가 저장된 List 객체
	 */
	public List<MemberVO> getAllMemeber();
	
	/**
	 * 회원ID를 매개변수로 받아서 해당 회원의 수를 반환
	 * @param memId 검색할 회원ID
	 * @return 검색된 회원의 수
	 */
	public int getMemberCount(String memId);
}
