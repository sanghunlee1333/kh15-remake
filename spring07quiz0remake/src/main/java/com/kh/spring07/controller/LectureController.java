package com.kh.spring07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring07.dao.LectureDao;
import com.kh.spring07.dto.LectureDto;

@RestController
@RequestMapping("/lecture")
public class LectureController {

	@Autowired
	private LectureDao lectureDao;
	
	@RequestMapping("/add")
	public String add(@ModelAttribute LectureDto lectureDto) {
		lectureDao.insert(lectureDto);
		
		return "강의 등록 완료";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute LectureDto lectureDto) {
		boolean success = lectureDao.update(lectureDto);
		
		return success ? "강의 수정 완료" : "존재하지 않은 강의 번호";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int lectureNo) {
		boolean success = lectureDao.delete(lectureNo);
		
		return success ? "강의 삭제 완료" : "존재하지 않은 강의 번호";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(required = false)String column, 
						@RequestParam(required = false)String keyword) {
		boolean isSearch = column != null && keyword != null;
		
		List<LectureDto> list = isSearch ? lectureDao.selectList(column, keyword) : lectureDao.selectList();
		StringBuffer buffer = new StringBuffer();
		
		if(list.isEmpty()) {
			buffer.append("데이터가 존재하지 않습니다.");
		}
		else {
			for(LectureDto lectureDto : list) {
				buffer.append(lectureDto);
				buffer.append("<br>");
			}
		}
		return buffer.toString();
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam int lectureNo) {
		LectureDto lectureDto = lectureDao.selectOne(lectureNo);
		
		return lectureDto == null ? "존재하지 않은 강의 번호" : lectureDto.toString();
	}
	
}
