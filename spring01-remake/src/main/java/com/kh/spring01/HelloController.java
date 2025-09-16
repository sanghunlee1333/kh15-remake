package com.kh.spring01;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
@RestController = 이 클래스가 Spring MVC의 컨트롤러 역할을 한다는 표시
- @Controller + @ResponseBody 합친 것과 동일
- (참고)@ResponseBody = 데이터(API 응답)를 반환하는 메서드에 붙이는 어노테이션 
	- 메서드 리턴값을 뷰로 해석하지 않고 HTTP 응답 body에 그대로 넣음
	- 주로 문자열, JSON, XML 데이터를 직접 반환할 때 사용
	- 클라이언트가 /hello 요청하면, JSP 찾지 않고 그냥 "Hello Data" 출력
- 즉, 리턴값이 뷰(JSP)가 아니라 HTTP 응답 본문(body)에 그대로 들어감
- 여기서는 "Hello Spring Boot!" 문자열이 그대로 브라우저 화면에 출력됨
-> 만약 @Controller만 썼다면? -> 리턴값 "Hello Spring Boot!"를 뷰 이름으로 해석하려고 해서 오류 발생
 */

@RestController
public class HelloController {
	
	/*
 	- @RequestMapping("/") = 클라이언트가 루트 경로("/")로 요청할 때 이 메서드를 실행하라는 의미
	- GET/POST 등 모든 HTTP 메서드에 대해 매핑됨
	- 브라우저에서 http://localhost:8080/ 접속하면 hello() 메서드 실행
 	-> 좀 더 명확히 하고 싶다면 @GetMapping("/")로도 작성할 수 있음
	 */
	
	@RequestMapping("/")
	public String hello() {
		return "Hello Spring Boot!";
	}
	
}
