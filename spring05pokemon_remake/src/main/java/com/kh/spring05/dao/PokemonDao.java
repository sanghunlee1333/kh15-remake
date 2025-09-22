package com.kh.spring05.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring05.dto.PokemonDto;
import com.kh.spring05.mapper.PokemonMapper;

//DAO (Data Access Object) = 영속성 있는 저장소(DB, 파일 등)에 접근하는 전담 객체
//보통은 DB 전용 -> SQL 실행해서 CRUD 담당
//그래서 흔히 “DAO = 영속성 계층(Persistence Layer)을 제어하는 도구”라고 부름

//@Repository = @Component의 특화 버전 (DAO 계층 전용)
//스프링이 “오케이, 이 클래스에서 SQLException 같은 게 터지면, 내가 알아서 공통 예외(DataAccessException)로 갈아껴줄게” 하고 AOP를 씌워버림
//(참고)AOP = 스프링이 메서드 실행을 가로채서 부가기능 자동으로 끼워넣는 기술
//역할 :
//1. 빈(Bean) 등록 -> IoC 컨테이너가 객체로 관리
//2. 예외 변환(AOP 지원) -> DB마다 다르게 던지는 에러를 스프링이 알아서 공통 예외로 갈아끼워주는 것
//덕분에 DB 종류(Oracle, MySQL...)가 달라도 코드 수정 최소화 가능

@Repository
public class PokemonDao {
	
	@Autowired
	private PokemonMapper pokemonMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//등록 메소
	public void insert(PokemonDto pokemonDto) {
		String sql = "insert into pokemon("
							+ "pokemon_no, pokemon_name, pokemon_type"
						+ ") "
						+ "values(pokemon_seq.nextval, ?, ?)";
		Object[] data = {pokemonDto.getPokemonName(), pokemonDto.getPokemonType()};
		jdbcTemplate.update(sql, data);
	}
	
	//수정 메소드
	public boolean update(PokemonDto pokemonDto) {
		String sql = "update pokemon "
				+ "set pokemon_name = ?, pokemon_type = ? "
				+ "where pokemon_no = ?";
		Object[] data = {
				pokemonDto.getPokemonName(),
				pokemonDto.getPokemonType(),
				pokemonDto.getPokemonNo()
		};
		int rows = jdbcTemplate.update(sql, data);
		
		return rows > 0; // true/false;
	}
	
	//삭제 메소드
	public boolean delete(int pokemonNo) {
		String sql = "delete from pokemon where pokemon_no = ?";
		Object[] data = {pokemonNo};
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	//목록조회 메소드
	public List<PokemonDto> selectList() {
		String sql = "select * from pokemon"; //DB에 있는 pokemon 테이블의 모든 행을 가져오는 쿼리
		//가져온 각 row(행)를 RowMapper에게 전달
		//RowMapper가 한 행(row)을 -> DTO 객체로 변환
		//변환된 DTO들을 List에 담아서 리턴
		return jdbcTemplate.query(sql, pokemonMapper); //즉, query() = DB SELECT 실행 -> 결과를 DTO 리스트로 변환해서 리턴
	}
	
	//검색에 사용할 컬럼에 대한 정보를 저장
	//Map = Key -> Value 구조로 데이터를 저장하는 자료구조
	//예) "이름" → "pokemon_name"
	//예) "속성" → "pokemon_type"
	//Map.of(...) = 자바 9부터 추가된 불변 Map 생성기
	//그냥 한 줄로 "이름"은 "pokemon_name"에 대응, "속성"은 "pokemon_type"에 대응하도록 만들어줌
	//즉, "한글 키워드" -> "DB 컬럼명"으로 매핑해주는 역할
	//왜 Map을 쓰냐? -> 사용자가 "이름"이나 "속성" 같은 문자열을 던져주면, 그걸 DB 컬럼 이름으로 바꿔야 하니까
	//만약 Map 안 쓰면 -> if(column.equals("이름")) {...} else if(column.equals("속성")) {...} 이런 식으로 지저분해짐.
	//Map은 키워드-값 매칭을 깔끔하게 관리할 수 있음
	
	private Map<String, String> columnExample = Map.of(
		"이름", "pokemon_name",
		"속성", "pokemon_type"
	);
	
	//검색 메소드
	public List<PokemonDto> selectList(String column, String keyword) {
		String columnName = columnExample.get(column);
		if(columnName == null) {
			throw new RuntimeException("항목 오류");
		}
		String sql = "select * from pokemon "
						+ "where instr(" + columnName + ", ?) > 0 "
						+ "order by " + columnName + " asc, pokemon_no asc";
		Object[] data = {keyword};
		return jdbcTemplate.query(sql, pokemonMapper, data);
	}
	
	//상세조회 메소드
	public PokemonDto selectOne(int pokemonNo) {
		String sql = "select * from pokemon where pokemon_no = ?";
		Object[] data = {pokemonNo};
		List<PokemonDto> list = jdbcTemplate.query(sql, pokemonMapper, data);
		return list.isEmpty() ? null : list.get(0);  
	}
	
}
