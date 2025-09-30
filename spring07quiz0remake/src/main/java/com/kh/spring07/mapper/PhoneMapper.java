package com.kh.spring07.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring07.dto.PhoneDto;

@Component
public class PhoneMapper implements RowMapper<PhoneDto>{

	@Override
	public PhoneDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		PhoneDto phoneDto = new PhoneDto();
		phoneDto.setPhoneNo(rs.getInt("phone_no"));
		phoneDto.setPhoneName(rs.getString("phone_name"));
		phoneDto.setPhoneTelecom(rs.getString("phone_telecom"));
		phoneDto.setPhoneContract(rs.getObject("phone_contract", Integer.class)); //추천
		
		return phoneDto;
	}

}
