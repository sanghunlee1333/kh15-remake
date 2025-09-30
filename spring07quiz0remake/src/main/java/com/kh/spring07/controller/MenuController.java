package com.kh.spring07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring07.dao.MenuDao;
import com.kh.spring07.dto.MenuDto;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuDao menuDao;
	
	@RequestMapping("/add")
	public String add(@ModelAttribute MenuDto menuDto) {
		menuDao.insert(menuDto);
		
		return "메뉴 등록 완료";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute MenuDto menuDto) {
		boolean success = menuDao.update(menuDto);
		
		return success ? "메뉴 수정 완료" : "존재하지 않은 메뉴 번호";
		
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int menuNo) {
		boolean success = menuDao.delete(menuNo);
		
		return success ? "메뉴 삭제 완료" : "존재하지 않은 메뉴 번호";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(required = false)String column, 
						@RequestParam(required = false)String keyword) {
		boolean isSearch = column != null && keyword != null;
		
		List<MenuDto> list = isSearch ? menuDao.selectList(column, keyword) : menuDao.selectList();
		StringBuffer buffer = new StringBuffer();
		if(list.isEmpty()) {
			buffer.append("데이터가 존재하지 않습니다.");
		}
		else {
			buffer.append("검색 결과 수 : " + list.size() + "<br>");
			for(MenuDto menuDto : list) {
				buffer.append(menuDto);
				buffer.append("<br>");
			}
		}
		return buffer.toString();
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam int menuNo) {
		MenuDto menuDto = menuDao.selectOne(menuNo);
		if(menuDto == null) {
			return "존재하지 않은 메뉴 번호";
		}
		else {
			return menuDto.toString();
		}
	}
}
