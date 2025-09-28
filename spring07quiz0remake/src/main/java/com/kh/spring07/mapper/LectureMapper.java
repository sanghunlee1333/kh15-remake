package com.kh.spring07.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring07.dto.LectureDto;

@Component
public class LectureMapper implements RowMapper<LectureDto> {

	@Override
	public LectureDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		LectureDto lectureDto = new LectureDto();
		
		lectureDto.setLectureNo(rs.getInt("lecture_no"));
		lectureDto.setLectureName(rs.getString("lecture_name"));
		lectureDto.setLectureCategory(rs.getString("lecture_category"));
		lectureDto.setLectureType(rs.getString("lecture_type"));
		lectureDto.setLecturePeriod(rs.getInt("lecture_period"));
		lectureDto.setLecturePrice(rs.getInt("lecture_price"));
		
		return lectureDto;
	}
	
}
