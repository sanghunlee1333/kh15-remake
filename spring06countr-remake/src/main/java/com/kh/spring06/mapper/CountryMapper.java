package com.kh.spring06.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring06.dto.CountryDto;

@Component
public class CountryMapper implements RowMapper<CountryDto> {

	@Override
	public CountryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CountryDto countryDto = new CountryDto();
		countryDto.setCountryName(rs.getString("country_name"));
		countryDto.setCountryCapital(rs.getString("country_capital"));
		countryDto.setCountryPopulation(rs.getLong("country_population"));
		return countryDto;
	}
	
}
