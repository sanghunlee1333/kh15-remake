package com.kh.spring04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/insert")
	public String insert(
			@RequestParam String playerName,
			@RequestParam String playerEvent,
			@RequestParam String playerType,
			@RequestParam int playerGoldMedal,
			@RequestParam int playerSilverMedal,
			@RequestParam int playerBronzeMedal
		) {
		String sql = "insert into player("
				+ "player_no, player_name, player_event, player_type, "
				+ "player_gold_medal, player_silver_medal, player_bronze_medal"
				+ ") values(player_seq.nextval, ?, ?, ?, ?, ? ,?)";
		Object[] data = {
				playerName, playerEvent, playerType, 
				playerGoldMedal, playerSilverMedal, playerBronzeMedal
		};
		jdbcTemplate.update(sql, data);
		
		return "선수 정보 등록 완료";
	}
}
