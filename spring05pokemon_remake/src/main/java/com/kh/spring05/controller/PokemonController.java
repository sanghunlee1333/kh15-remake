package com.kh.spring05.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring05.dao.PokemonDao;
import com.kh.spring05.dto.PokemonDto;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

	@Autowired
	private PokemonDao pokemonDao;
	
	//@ModelAttribute = HTTP 요청 파라미터 여러 개 -> 객체(DTO) 하나에 자동으로 바인딩
	// 즉, 요청에서 들어온 값들을 DTO 필드와 이름 맞춰서 자동으로 채워줌
	//?pokemonName=피카츄&pokemonType=전기 ->
	//Spring이 알아서 PokemonDto의 setPokemonName("피카츄"), setPokemonType("전기") 호출
	
	//등록 매핑
	@RequestMapping("/insert")
	public String insert(@ModelAttribute PokemonDto pokemonDto) {
		pokemonDao.insert(pokemonDto);
		return "포켓몬 등록 완료";
	}
	
	//수정 매핑
	@RequestMapping("/update")
	public String update(@ModelAttribute PokemonDto pokemonDto) {
		boolean success = pokemonDao.update(pokemonDto);
		return success ? "포켓몬 변경 완료" : "존재하지 않는 포켓몬 번호"; 
	}
	
	//삭제 매핑
	@RequestMapping("/delete")
	public String delete(@RequestParam int pokemonNo) {
		boolean success = pokemonDao.delete(pokemonNo);
		return success ? "포켓몬 삭제 완료" : "존재하지 않는 포켓몬 번호"; 
	}
	
	//목록 조회 매핑
	@RequestMapping("/list")
	public String list() {
		List<PokemonDto> list = pokemonDao.selectList();
		StringBuffer buffer = new StringBuffer();
		for(PokemonDto pokemonDto : list) {
			buffer.append(pokemonDto.toString());
			buffer.append("<br>");
		}
		return buffer.toString();
	}
	
	//검색 매핑
	@RequestMapping("/search")
	public String list(@RequestParam String column, @RequestParam String keyword) {
		List<PokemonDto> list = pokemonDao.selectList(column, keyword);
		StringBuffer buffer = new StringBuffer();
		buffer.append("검색 결과 수 : " + list.size() + "<br>");
		for(PokemonDto pokemonDto : list) {
			buffer.append(pokemonDto.toString());
			buffer.append("<br>");
		}
		return buffer.toString();
	}
	
	//상세 조회 매핑
	@RequestMapping("detail")
	public String detail(@RequestParam int pokemonNo) {
		PokemonDto pokemonDto = pokemonDao.selectOne(pokemonNo);
		if(pokemonDto == null) {
			return "존재하지 않는 포켓몬 번호";
		}
		else {
			return pokemonDto.toString();
		}
	}
	
}
