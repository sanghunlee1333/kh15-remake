package com.kh.spring05.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring05.dto.PokemonDto;

//@Component = 스프링 컨테이너에 “이 클래스도 빈(Bean)으로 등록해줘”라고 표시하는 어노테이션
//즉, 개발자가 new 안 해도 스프링이 자동으로 객체를 만들고 관리해
//@Component 붙은 클래스는 IoC 컨테이너 안에 올라가고, 다른 클래스에서 @Autowired로 주입받을 수 있

@Component
public class PokemonMapper implements RowMapper<PokemonDto> { //RowMapper는 DB에서 꺼낸 한 줄(row)을 DTO 객체로 바꿔주는 인터페이스
	//즉, ResultSet에 담긴 한 줄을 PokemonDto로 변환하는 역할
	
	//mapRow -> RowMapper가 가진 유일한 메서드, 꼭 구현해야 함
	//ResultSet rs -> SQL 실행 결과에서 현재 행(row)을 가리키는 객체
	//rs.getInt("컬럼명"), rs.getString("컬럼명")으로 값 꺼낼 수 있음
	//int rowNum -> 몇 번째 행인지 알려줌 (0부터 시작)
	//거의 안 쓰고, 필요하면 로깅 정도에만 씀
	
	@Override 
	public PokemonDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PokemonDto pokemonDto = new PokemonDto(); //PokemonDto 객체 하나 생성 (new)
		//DB 결과(ResultSet)에서 pokemon_no, pokemon_name, pokemon_type 컬럼 꺼냄
		pokemonDto.setPokemonNo(rs.getInt("pokemon_no")); //DTO의 필드에 각각 채워 넣음 (setter 호출)
		pokemonDto.setPokemonName(rs.getString("pokemon_name")); 
		pokemonDto.setPokemonType(rs.getString("pokemon_type"));
		return pokemonDto; //채워진 PokemonDto를 반환 → 이게 한 줄(row)에 대한 DTO
	}
}

	
