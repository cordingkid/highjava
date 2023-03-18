package kr.or.ddit.mvc.controller;

import java.util.List;
import java.util.Scanner;

import kr.or.ddit.mvc.service.IMemberService;
import kr.or.ddit.mvc.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberController {
	private IMemberService service;
	private Scanner scan;

	public MemberController() {
		scan = new Scanner(System.in);
		service = new MemberServiceImpl();
	}
	
	public static void main(String[] args) {
		new MemberController().start();
	}

	public void start() {
		while (true) {
			int choice = show();
			switch (choice) {
			case 1: insertMember(); break;
			case 2: delete(); break;
			case 3: update(); break;	//수정 1
			case 4: printAll(); break;
//			case 5: updateMember(); break;	//수정 2
//			case 6: updateMember3(); break;	//수정 3
			case 0: exit(); break;
			}
		}
	}
	
	private void delete() {
		System.out.print("삭제할 사람의 id 입력>>");
		String id = scan.nextLine().trim();
		if (service.getMemberCount(id) == 0) {
			System.out.println("해당 아이디는 존재하지 않습니다.");
			return;
		}
		int cnt = service.deleteMember(id);
		if(cnt==1) {
			System.out.println("성공적으로 삭제하엿습니다.");
		}else {
			System.out.println("삭제를 실패 했습니다.");
			return;
		}
	}

	private void update() {
		System.out.print("수정할 사람의 id 입력>>");
		String id = scan.nextLine().trim();
		if (service.getMemberCount(id) == 0) {
			System.out.println("해당 아이디는 존재하지 않습니다.");
			return;
		}
		MemberVO vo = new MemberVO();
		vo.setMem_id(id);
		
		System.out.print("수정할 비밀번호 입력>>");
		vo.setMem_pass(scan.nextLine().trim());
		
		System.out.print("수정할 이름 입력>>");
		vo.setMem_name(scan.nextLine().trim());
		
		System.out.print("수정할 전화번호 입력>>");
		vo.setMem_tel(scan.nextLine().trim());
		
		System.out.print("수정할 주소 입력>>");
		vo.setMem_addr(scan.nextLine().trim());
		
		int cnt = service.updateMember(vo);
		if (cnt == 1) {
			System.out.println("성공적으로 변경하엿습니다.");
		}else {
			System.out.println("변경에 실패하엿습니다.");
			return;
		}
	}

	private void printAll() {
		List<MemberVO> list = service.getAllMemeber();
		System.out.println("===================== 회원 전체 목록 ================");
		System.out.println("  아이디    비밀번호  이름  번호    주소");
		System.out.println("=====================================================");
		for (MemberVO vo : list) {
			System.out.print(vo.getMem_id()+"   ");
			System.out.print(vo.getMem_pass()+"   ");
			System.out.print(vo.getMem_name()+"   ");
			System.out.print(vo.getMem_tel()+"   ");
			System.out.print(vo.getMem_addr()+"\n");
		}
	}

	private void exit() {
		System.out.println("프로그램 종료");
		System.exit(0);
	}

	private void insertMember() {
		int cnt;
		String id;
		do {
			System.out.print("아이디 입력>>");
			id = scan.nextLine().trim();
			cnt = service.getMemberCount(id);
			if (cnt != 0) {
				System.out.println("중복되는 아이디 입니다 다시입력해주세여.");
			}
		} while (cnt != 0);
		
		System.out.print("비밀번호 입력>>");
		String pass = scan.nextLine().trim();
		System.out.print("이름 입력>>");
		String name = scan.nextLine().trim();
		System.out.print("전화번호 입력>>");
		String tel = scan.nextLine().trim();
		System.out.print("주소 입력>>");
		String addr = scan.nextLine().trim();
		
		MemberVO vo = new MemberVO();
		vo.setMem_id(id);
		vo.setMem_pass(pass);
		vo.setMem_name(name);
		vo.setMem_tel(tel);
		vo.setMem_addr(addr);
		service.insertMember(vo);
		System.out.println("회원이 저장되었습니다.");
	}

	public int show() {
		System.out.println("=== 회원 관리 프로그램 ===");
		System.out.println("1. 자료 추가");
		System.out.println("2. 자료 삭제");
		System.out.println("3. 자료 수정(전체항목수정)");
		System.out.println("4. 전체 자료 출력");
//		System.out.println("5. 자료 수정(수정항목선택)");
//		System.out.println("6. 자료 수정(입력항목선택)");
		System.out.println("0. 종료");
		System.out.print("선택>>");
		return Integer.parseInt(scan.nextLine().trim());
	}
}
