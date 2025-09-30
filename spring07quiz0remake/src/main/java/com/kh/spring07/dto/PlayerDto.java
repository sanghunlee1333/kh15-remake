package com.kh.spring07.dto;

import lombok.Data;

@Data
public class PlayerDto {

	private int playerNo;
	private String playerName;
	private String playerEvent;
	private String playerType;
	private int playerGoldMedal;
	private int playerSilverMedal;
	private int playerBronzeMedal;
	
}
