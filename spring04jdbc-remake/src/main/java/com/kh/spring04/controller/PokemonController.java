package com.kh.spring04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {
	
	//제어 역전(IoC) : new 안 쓰고, 객체를 스프링 컨테이너가 관리하도록 맡
	//의존성 주입(DI) : @Autowired(혹은 생성자)로 컨테이너가 관리하는 객체를 다른 객체에 주입
	
	//Bean = 스프링이 관리하는 객체
	//원래 자바에서는 new 키워드로 내가 직접 객체 생성 -> 생명주기도 내가 책임
	//스프링에서는 @Component, @Service, @Controller 같은 어노테이션이나 설정을 통해 스프링 컨테이너에 등록된 객체 = Bean
	//한 번만 만들어져서 싱글톤으로 관리됨(기본)
	//필요할 때마다 컨테이너에서 꺼내 씀
	
	//Spring JDBC (JdbcTemplate) 방
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/insert")
	public String insert(@RequestParam String pokemonName,
						@RequestParam String pokemonType) {
		String sql = "insert into pokemon("
				+ "pokemon_no, pokemon_name, pokemon_type"
				+ ") values(pokemon_seq.nextval, ?, ?)";
		Object[] data = {pokemonName, pokemonType};
		jdbcTemplate.update(sql, data); //SQL 실행
		return "포켓몬 등록 완료";
	}
}
