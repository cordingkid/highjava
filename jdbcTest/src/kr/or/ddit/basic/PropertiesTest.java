package kr.or.ddit.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
	public static void main(String[] args) {
		// 읽어온 정보를 저장할 Properties객체 생성
		Properties properties = new Properties();
		
		// 읽어올 파일명을 이용한 File개겣 생성
		File file = new File("res/kr/or/ddit/config/dbinfo.properties");
		FileInputStream fin = null;
		
		try {
			// 파일 내용을 읽어 올 입력 스트림 객체 생성
			fin = new FileInputStream(file);
			
			// 입력 스트림을 이용하여 파일 내용을 읽어와 Properties객체에 저장
			properties.load(fin);	
			/* 
			   load() > 파일 내용을 읽어와 key값과 value값을 분류한 후
			   분류된 정보를 Properties객체에 추가한다.
			*/
			System.out.println("driver : " +properties.getProperty("driver"));
			System.out.println("url : " +properties.getProperty("url"));
			System.out.println("user : " +properties.getProperty("user"));
			System.out.println("pass : " +properties.getProperty("pass"));
			
		} catch (IOException e) {
			System.out.println("입출력 오류 입니다.");
			e.printStackTrace();
		}finally {
			if(fin!=null)try {fin.close();}catch(IOException e) {}
		}
	}
}
