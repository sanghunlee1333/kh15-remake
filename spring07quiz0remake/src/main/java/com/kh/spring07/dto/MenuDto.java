package com.kh.spring07.dto;

import lombok.Data;

@Data
public class MenuDto {
	private int menuNo;
	private String menuName;
	private String menuType;
	private int menuPrice;
	private String menuEvent;
}
