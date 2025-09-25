package com.kh.spring06.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring06.dao.CountryDao;
import com.kh.spring06.dto.CountryDto;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	private CountryDao countryDao;
	
	@RequestMapping("/add")
	public String add(@ModelAttribute CountryDto countryDto) {
		countryDao.insert(countryDto);
		return "나라 등록 완료";
	}
	
	@RequestMapping("edit")
	public String edit(@ModelAttribute CountryDto countryDto) {
		boolean success = countryDao.update(countryDto);
		return success ? "나라 변경 완료" : "존재하지 않는 나라 번호"; 
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int countryNo) {
		boolean success = countryDao.delete(countryNo);
		return success ? "나라 삭제 완료" : "존재하지 않는 나라 번호";
	}
	
	@RequestMapping("/list")
	public String list() {
		List<CountryDto> list = countryDao.selectList();
		StringBuffer buffer = new StringBuffer();
		if(list.isEmpty()) {
			buffer.append("데이터가 존재하지 않습니다.");
		}
		else {
			buffer.append("검색 결과 수 : " + list.size() + "<br>");
			for(CountryDto countryDto : list) {
				buffer.append(countryDto.toString());
				buffer.append("<br>");
			}
		}
		return buffer.toString();
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam String column, @RequestParam String keyword) {
		List<CountryDto> list = countryDao.selectList(column, keyword);
		StringBuffer buffer = new StringBuffer();
		buffer.append("검색 결과 수 : " + list.size() + "<br>");
		for(CountryDto countryDto : list) {
			buffer.append(countryDto.toString());
			buffer.append("<br>");
		}
		return buffer.toString();
	}
	
	@RequestMapping("/total")
	public String total(@RequestParam(required = false) String column, 
						@RequestParam(required = false) String keyword) {
		boolean isSearch = column != null && keyword != null;
		
		List<CountryDto> list = isSearch ? countryDao.selectList(column, keyword) : countryDao.selectList();
		
		StringBuffer buffer = new StringBuffer();

		if(list.isEmpty()) {
			buffer.append("데이터가 존재하지 않습니다.");
		}
		else {
			buffer.append("검색 결과 수 : " + list.size() + "<br>");
			for(CountryDto countryDto : list) {
				buffer.append(countryDto.toString());
				buffer.append("<br>");
			}
		}
		return buffer.toString();
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam int countryNo) {
		CountryDto countryDto = countryDao.selectOne(countryNo);
		
		if(countryDto == null) {
			return "존재하지 않는 나라 번호";
		}
		else {
			StringBuffer buffer = new StringBuffer();
			buffer.append("국가번호 : " + countryDto.getCountryNo() + "<br>");
			buffer.append("국가명 : " + countryDto.getCountryName() + "<br>");
			buffer.append("수도명 : " + countryDto.getCountryCapital() + "<br>");
			buffer.append("인구수 : " + countryDto.getCountryPopulation() + "<br>");
			
			return buffer.toString();
		}
	}
	
}
