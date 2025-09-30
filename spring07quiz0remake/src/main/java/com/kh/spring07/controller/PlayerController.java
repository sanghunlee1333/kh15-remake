package com.kh.spring07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring07.dao.PlayerDao;
import com.kh.spring07.dto.PlayerDto;

@RestController
@RequestMapping("/player")
public class PlayerController {
	
	@Autowired
	PlayerDao playerDao;
	
	@RequestMapping("/add")
	public String insert(@ModelAttribute PlayerDto playerDto) {
		playerDao.insert(playerDto);
		return "선수 등록 완료";
	}
	
	@RequestMapping("/edit")
	public String update(@ModelAttribute PlayerDto playerDto) {
		boolean success = playerDao.update(playerDto);
		return success ? "선수 수정 완료" : "존재하지 않은 선수 번호";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int playerNo) {
		boolean success = playerDao.delete(playerNo);
		return success ? "선수 삭제 완료" : "존재하지 않은 선수 번호";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(required = false) String column, 
						@RequestParam(required = false) String keyword){
		
		boolean search = column != null && keyword != null;
		
		List<PlayerDto> list = search ? playerDao.selectList(column, keyword) : playerDao.selectList();
		
		StringBuffer buffer = new StringBuffer();
		if(list.isEmpty()) {
			buffer.append("데이터가 존재하지 않습니다");
		}
		else {
			buffer.append("검색 결과 수 : " + list.size() + "<br>");
			for(PlayerDto playerDto : list) {
				buffer.append(playerDto.toString());
				buffer.append("<br>");
			}
		}
		return buffer.toString();
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam int playerNo) {
		PlayerDto playerDto = playerDao.selectOne(playerNo);
		if(playerDto == null) {
			return "존재하지 않은 선수 번호";
		}
		else {
			return playerDto.toString();
		}
	}
	
	
	

}
