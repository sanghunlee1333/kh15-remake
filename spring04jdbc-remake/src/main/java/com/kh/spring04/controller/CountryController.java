package com.kh.spring04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CountryController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/insert")
	public String insert(@RequestParam String countryName,
						@RequestParam String countryCapital,
						@RequestParam long countryPopulation
		) {
		String sql = "insert into country(country_no, country_name, "
				+ "country_capital, country_population"
				+ ") values(country_seq.nextval, ?, ?, ?)";
		Object[] data = {countryName, countryCapital, countryPopulation};
		jdbcTemplate.update(sql, data);
		
		return "나라 정보 등록 완료";
	}
}
