package com.kh.spring07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring07.dao.PhoneDao;
import com.kh.spring07.dto.PhoneDto;

@RestController
@RequestMapping("/phone")
public class PhoneController {

	@Autowired
	private PhoneDao phoneDao;
	
	@RequestMapping("/add")
	public String add(@ModelAttribute PhoneDto phoneDto) {
		phoneDao.insert(phoneDto);
		return "휴대폰 등록 완료"; 
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute PhoneDto phoneDto) {
		boolean success = phoneDao.update(phoneDto);
		return success ? "휴대폰 수정 완료" : "존재하지 않은 휴대폰 번호";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int phoneNo) {
		boolean success = phoneDao.delete(phoneNo);
		return success ? "휴대폰 삭제 완료" : "존재하지 않은 휴대폰 번호";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(required = false) String column,
						@RequestParam(required = false) String keyword) {
		boolean search = column != null && keyword != null;
		
		List<PhoneDto> list = search ? phoneDao.selectList(column, keyword) : phoneDao.selectList();
		StringBuffer buffer = new StringBuffer();
		if(list.isEmpty()) {
			buffer.append("데이터가 존재하지 않습니다");
		}
		else {
			for(PhoneDto phoneDto : list) {
				buffer.append(phoneDto);
				buffer.append("<br>");
			}
		}
		return buffer.toString();
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam int phoneNo) {
		PhoneDto phoneDto = phoneDao.selectOne(phoneNo);
		if(phoneDto == null) {
			return "존재하지 않은 휴대폰 번호";
		}
		else {
			return phoneDto.toString();
		}
	}
	
}
