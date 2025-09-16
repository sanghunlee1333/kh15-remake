package com.kh.spring02;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //특정 주소에 대해서 상호작용(요청/응답)을 할 수 있는 도구(메뉴판)
public class HelloController {
	
	@RequestMapping("/hello") //원하는 주소를 지정하는 도구(메뉴명) -> http://localhost:8080/hello
	//http - 프로토콜 *https는 인증서가 필요 
	//localhost - 서버의 위치(ip) - 아파트의 위치
	//8080 - 서버의 포트 - 아파트의 호수
	public String hello() {
		return "Hello Spring Boot!";
	}
	
	@RequestMapping("/goodbye")
	public String goodbye() {
		//System.out.println("기존 출력 명령"); //혼자할 때만 앞으로 의미 있고, 이젠 의미 없음
		return "Goodbye!"; //이제 대부분 숫자도 문자열로 변경해서 출력해야 됨
	}
	
	//(Q) '/dice'라는 주소로 들어오면 랜덤 주사위 값 1개 출력
	@RequestMapping("/dice")
	public String dice() {
		Random r = new Random();
		int dice = r.nextInt(6) + 1;
		return "주사위 : " + dice;
	}
	
	//(Q) Scanner를 쓸 수 없다면 사용자가 전송하는 데이터를 어떻게 받을까?
	//- 주소가 http://localhost:8080/plus?left=10&right=20 일 경우
	@RequestMapping("/plus")
	//public String plus(int left, int right) { //약식
	public String plus(@RequestParam int left, @RequestParam int right) { //정식 //RequestParam - 요청 파라미터 *안써도 되지만 쓰자
		// @RequestParam = Spring MVC에서 클라이언트가 보낸 요청 파라미터를 메서드 매개변수에 매핑할 때 쓰는 어노테이션
		int total = left + right;
		return "덧셈 결과 : " + total;
	}
	
}
