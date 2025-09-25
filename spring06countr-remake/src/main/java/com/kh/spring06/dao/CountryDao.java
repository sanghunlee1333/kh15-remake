package com.kh.spring06.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring06.dto.CountryDto;
import com.kh.spring06.mapper.CountryMapper;

@Repository
public class CountryDao {
	
	@Autowired
	private CountryMapper countryMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insert(CountryDto countryDto) {
		String sql = "insert into country(company_no, company_name, company_capital, company_population"
				+ ") values(country_seq.nextval, ?, ?, ?)";
		Object[] data = {
				countryDto.getCountryName(), 
				countryDto.getCountryCapital(), 
				countryDto.getCountryPopulation()
		};
		jdbcTemplate.update(sql, data);
	}
	
	public boolean update(CountryDto countryDto) {
		String sql = "update country set country_name = ?, country_capital = ?, country_population = ?";
		Object[] data = {
				countryDto.getCountryName(),
				countryDto.getCountryCapital(),
				countryDto.getCountryPopulation()
		};
		int rows = jdbcTemplate.update(sql, data);
		
		return rows > 0;
	}
	
	public boolean delete(int countryNo) {
		String sql = "delete from country where country_no = ?";
		Object[] data = {countryNo};
		
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	public List<CountryDto> selectList() {
		String sql = "select * from country";
		return jdbcTemplate.query(sql, countryMapper);
	};
	
	private Map<String, String> columnExamples = Map.of(
			"국가명", "country_name",
			"수도명", "country_capital"
	);
	
	public List<CountryDto> selectList(String column, String keyword) {
		String columnName = columnExamples.get(column);
		if(columnName == null) throw new RuntimeException("항목 오류");
		
		String sql = "select * from country "
				+ "where instr(#1, ?) > 0 " //컬럼 안에 keyword가 포함되어 있으면
				+ "order by #1 asc, country_no asc"; //#1이 아니어도 상관없음
		sql = sql.replace("#1", columnName); //JDBC의 ?는 "값(데이터)"만 치환 가능
		//하지만 SQL 구조(컬럼명, 테이블명, ORDER BY 대상 등)은 ?로 대체할 수 없음
		//그래서 "컬럼명을 나중에 바꾸고 싶다"면 어쩔 수 없이 문자열 치환(replace)
		Object[] data = {keyword};
		
		return jdbcTemplate.query(sql, countryMapper, data);
	}
	
	public CountryDto selectOne(int countryNo) {
		String sql = "select * from country_no = ?";
		Object[] data = {countryNo};
		List<CountryDto> list = jdbcTemplate.query(sql, countryMapper, data); 
		
		return list.isEmpty() ? null : list.get(0); 
	}
}
