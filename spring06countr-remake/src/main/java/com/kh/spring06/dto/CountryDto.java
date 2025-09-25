package com.kh.spring06.dto;

import lombok.Data;

@Data
public class CountryDto {
	private int countryNo;
	private String countryName;
	private String countryCapital;
	private long countryPopulation;
}
