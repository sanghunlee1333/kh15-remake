package com.kh.spring05.dto;

import lombok.Data;

//DTO는 단순 데이터 전달용 객체라 상태를 공유하면 안 되고, 매번 새로운 데이터로 사용해야 하기 때문에 스프링 빈으로 주입하지 않고 필요할 때 new로 생성
// 반면, Service/Repository는 로직과 리소스를 관리해야 해서 빈으로 등록해 주입

@Data
public class PokemonDto {
	private int pokemonNo;
	private String pokemonName;
	private String pokemonType;
}
