package com.kh.spring07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring07.dao.ItemDao;
import com.kh.spring07.dto.ItemDto;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemDao itemDao;
	
	@RequestMapping("/add")
	public String add(@ModelAttribute ItemDto itemDto) {
		itemDao.insert(itemDto);
		
		return "아이템 등록 완료";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute ItemDto itemDto) {
		boolean success = itemDao.update(itemDto);
		
		return success ? "아이템 수정 완료" : "존재하지 않은 아이템 번호";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int itemNo) {
		boolean success = itemDao.delete(itemNo);
		
		return success ? "아이템 삭제 완료" : "존재하지 않은 아이템 번호";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(required = false)String column, 
						@RequestParam(required = false)String keyword) {
		boolean isSearch = column != null && keyword != null;
		
		List<ItemDto> list = isSearch ? itemDao.selectList(column, keyword) : itemDao.selectList();
		StringBuffer buffer = new StringBuffer();
		if(list.isEmpty()) {
			buffer.append("데이터가 존재하지 않습니다.");
		}
		else {
			for(ItemDto itemDto : list) {
				buffer.append(itemDto);
				buffer.append("<br>");
			}
		}
		return buffer.toString();
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam int itemNo) {
		ItemDto itemDto = itemDao.selectOne(itemNo);
		if(itemDto == null) {
			return "존재하지 않은 아이템 번호";
		}
		else {
			return itemDto.toString();
		}
	}
	
}
