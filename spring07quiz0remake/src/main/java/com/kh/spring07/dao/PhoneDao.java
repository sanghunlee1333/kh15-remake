package com.kh.spring07.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring07.dto.PhoneDto;
import com.kh.spring07.mapper.PhoneMapper;

@Repository
public class PhoneDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PhoneMapper phoneMapper;

	public void insert(PhoneDto phoneDto) {

		String sql = "insert into phone(phone_no, phone_name, phone_telecom, phone_price, phone_contract) "
				+ "values(phone_seq.nextval, ?, ?, ?, ?)";
		Object[] data = { phoneDto.getPhoneName(), phoneDto.getPhoneTelecom(), phoneDto.getPhonePrice(),
				phoneDto.getPhoneContract() };
		jdbcTemplate.update(sql, data);

	}

	public boolean update(PhoneDto phoneDto) {

		String sql = "update phone set phone_name = ?, phone_telecom = ?, phone_price = ?, phone_contract = ? where phone_no = ?";
		Object[] data = { phoneDto.getPhoneName(), phoneDto.getPhoneTelecom(), phoneDto.getPhonePrice(),
				phoneDto.getPhoneContract(), phoneDto.getPhoneNo() };
		int rows = jdbcTemplate.update(sql, data);

		return rows > 0;

	}

	public boolean delete(int phoneNo) {

		String sql = "delete phone where phone_no = ?";
		Object[] data = { phoneNo };

		return jdbcTemplate.update(sql, data) > 0;

	}

	public List<PhoneDto> selectList() {

		String sql = "select * from phone";

		return jdbcTemplate.query(sql, phoneMapper);

	}

	private Map<String, String> columnExamples = Map.of(

			"휴대폰이름", "phone_name", "통신사", "phone_telecom"

	);

	public List<PhoneDto> selectList(String column, String keyword) {

		String columnName = columnExamples.get(column);
		if (columnName == null) {
			throw new RuntimeException("항목 오류");
		}

		String sql = "select * from phone where instr(#1, ?) > 0 order by #1 asc, phone_no asc";
		sql = sql.replace("#1", columnName);
		Object[] data = { keyword };
		return jdbcTemplate.query(sql, phoneMapper, data);

	}

	public PhoneDto selectOne(int phoneNo) {
		String sql = "select * from phone where phone_no = ?";
		Object[] data = { phoneNo };
		List<PhoneDto> list = jdbcTemplate.query(sql, phoneMapper, data);
		return list.isEmpty() ? null : list.get(0);
	}
}
