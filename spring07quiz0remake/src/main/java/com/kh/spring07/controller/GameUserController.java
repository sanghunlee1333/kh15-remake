package com.kh.spring07.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring07.dao.GameUserDao;
import com.kh.spring07.dto.GameUserDto;

@RestController
@RequestMapping("/game-user")
public class GameUserController {
	
	@Autowired
	private GameUserDao gameUserDao;
	
	@RequestMapping("/add")
	public String add(@ModelAttribute GameUserDto gameUserDto) {
		gameUserDao.insert(gameUserDto);
		
		return "유저 등록 완료";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute GameUserDto gameUserDto) {
		boolean success = gameUserDao.update(gameUserDto);
		
		return success ? "유저 수정 완료" : "존재하지 않은 유저 정보";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int gameUserNo) {
		boolean success = gameUserDao.delete(gameUserNo);
		return success ? "유저 삭제 완료" : "존재하지 않은 유저 정보";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(required = false)String column,
						@RequestParam(required = false)String keyword) {
		boolean isSearch = column != null && keyword != null;
		
		List<GameUserDto> list =  isSearch ? gameUserDao.selectList(column, keyword) : gameUserDao.selectList();
		StringBuffer buffer = new StringBuffer();
		if(list.isEmpty()) {
			buffer.append("데이터가 존재하지 않습니다.");
		}
		else {
			for(GameUserDto gameUserDto : list) {
				buffer.append(gameUserDto);
				buffer.append("<br>");
			}
		}
		return buffer.toString();
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam int gameUserNo) {
		GameUserDto gameUserDto = gameUserDao.selectOne(gameUserNo);
		if(gameUserDto == null) {
			return "데이터가 존재하지 않습니다.";
		}
		else {
			return gameUserDto.toString();
		}
	}

}
