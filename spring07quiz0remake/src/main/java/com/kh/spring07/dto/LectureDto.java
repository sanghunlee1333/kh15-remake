package com.kh.spring07.dto;

import lombok.Data;

@Data
public class LectureDto {
	private int lectureNo;
	private String lectureName;
	private String lectureCategory;
	private int lecturePeriod;
	private int lecturePrice;
	private String lectureType;
}
