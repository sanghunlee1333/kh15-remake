package com.kh.spring03.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParameterQuizController {

	@RequestMapping("/cinema")
	public String cinema(@RequestParam int adult, @RequestParam int teen) {
		int adultPrice = 15000, teenPrice = 10000;
		int total = adult * adultPrice + teen * teenPrice;
		return "총 요금 : " + total;
	}
	
	@RequestMapping("/cinema2")
	public String cinema2(
			@RequestParam(required = false, defaultValue = "0") int adult,
			@RequestParam(required = false, defaultValue = "0") int teen) {
		int adultPrice = 15000, teenPrice = 10000;
		int total = adult * adultPrice + teen * teenPrice;
		return "총 요금 : " + total;
	}
	
	@RequestMapping("/coffee")
	public String coffee(
			@RequestParam(required = false, defaultValue = "0") int americano,
			@RequestParam(required = false, defaultValue = "0") int latte) {
		int amePrice = 2500, lattePrice = 3000;
		int total = americano * amePrice + latte * lattePrice;
		return "총 커피 : " + total;
	}
		
	@RequestMapping("/score")
	public String score(
			@RequestParam String name,
			@RequestParam int kor,
			@RequestParam int eng,
			@RequestParam int mat) {
		int total = kor + eng + mat;
		double average = total / 3.0d;
		
		//return "이름 : " + name + "<br>총점 : " + total + "<br>평균 : " + average;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("이름 :  " + name);
		buffer.append("<br>");
		buffer.append("총점 : " + total);
		buffer.append("<br>");
		buffer.append("평균 : " + average);
		
		return buffer.toString();
	}
	
	@RequestMapping("/subway")
	public String subway(@RequestParam(required = false) String birth) {
		int birthValue = Integer.parseInt(birth);
		int year = LocalDate.now().getYear();
		int age = year - birthValue + 1;
		
		int price;
		int free = 0, child = 500, teen = 800, adult = 1400;
		
		if(birth == null) {
			price = adult;
			return "나이 : 비공개, 총 요금 : " + price;
		}
		else if(age < 8 || age >= 65) {
			price = free;
		}
		else if(age <= 13) {
			price = child;
		}
		else if(age <= 19) {
			price = teen;
		}
		else { 
			price = adult;
		}
		return "나이 : " + age + "세, 총 요금 : " + price;
	}
	
	@RequestMapping("/sum")
	public String sum(@RequestParam int begin, @RequestParam int end) {
		int total = 0;
		for(int i = begin; i <= end; i++) {
			total += i;
		}
		return Integer.toString(begin) + "부터 " + end + "까지의 합 : " + total;
	}
	
	//pc방 시작시간과 종료시간을 받아서 이용시간과 분당 요금을 계산
//	@RequestMapping("/pcroom")
//	public String pcroom (
//			@RequestParam String start, //HH:mm 형식으로
//			@RequestParam String finish) { //시작시간과 끝난시간을 쿼리스트링으로 받
//		LocalDate today = LocalDate.now(); //오늘 날짜를 구함(시작 시간만 보면 끝난 시간보다 앞서있을 가능성이 있으므로, 날짜 + 시간 형식으로 만들어서 명확히 비교하기 위함) 
//		LocalTime startTime = LocalTime.parse(start); //받은 시간들을 String에서 LocalTime 객체로 변
//		LocalTime finishTime = LocalTime.parse(finish);
//		
//		LocalDateTime begin, end;
//		if(startTime.isBefore(finishTime)) { //시작시간이 끝난시간보다 이전이라면 (당일)
//			begin = LocalDateTime.of(today, startTime); //오늘날짜 + 시작시간으로
//			end = LocalDateTime.of(today, finishTime); //오늘날짜 + 끝난시간으로
//		}
//		else { //시작시간이 더 크다면, begin의 날짜가 어제(가정)
//			begin = LocalDateTime.of(today.minusDays(1), startTime); //오늘날짜 - 1 + 시작시간으로
//			end = LocalDateTime.of(today, finishTime); //오늘날짜 + 끝난시간으로
//		}
//		Duration duration = Duration.between(begin, end); //둘 시간 사이의 간격을 Duration객체로 구함
//		
//		long minutes = duration.toMinutes(); //분으로 변경해서 분당 요금 계산
//		int price = (int) (minutes * 1000 / 60); //1시간에 1000원인 것을, 60으로 나누면 분당 요금
//		
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("이용시간 : ");
//		buffer.append(duration.toHoursPart()); //둘 시간 사이의 간격에서 시간만 추출
//		buffer.append("시간 ");
//		buffer.append(duration.toMinutesPart()); //둘 시간 사이의 간격에서 분만 추출
//		buffer.append("분");
//		buffer.append("<br>");
//		buffer.append("이용요금 : " + price + "원");
//		
//		return buffer.toString();
//	}
	
	//재연습
	@RequestMapping("/pcroom")
	public String pcroom(
			@RequestParam String start,
			@RequestParam String finish) {
		LocalDate today = LocalDate.now();
		LocalTime startTime = LocalTime.parse(start);
		LocalTime finishTime = LocalTime.parse(finish);
		
		LocalDateTime begin, end;
		if(startTime.isBefore(finishTime)) {
			begin = LocalDateTime.of(today, startTime);
			end = LocalDateTime.of(today, finishTime);
		}
		else {
			begin = LocalDateTime.of(today.minusDays(1), startTime);
			end = LocalDateTime.of(today, finishTime);
		}
		
		Duration duration = Duration.between(begin, end);
		
		long minutes = duration.toMinutes();
		int price = (int)(minutes * 1000 / 60d); 
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("이용시간 : ");
		buffer.append(duration.toHoursPart());
		buffer.append("시간 ");
		buffer.append(duration.toMinutesPart());
		buffer.append("분");
		buffer.append("<br>");
		buffer.append("이용 요금 : " + price + "원");
		
		return buffer.toString();
	}
	
}
