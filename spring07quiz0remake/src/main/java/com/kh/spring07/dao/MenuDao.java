package com.kh.spring07.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring07.dto.MenuDto;
import com.kh.spring07.mapper.MenuMapper;

@Repository
public class MenuDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MenuMapper menuMapper;
	
	public void insert(MenuDto menuDto) {
		String sql = "insert into menu(menu_no, menu_name, "
				+ "menu_type, menu_price, menu_event) "
				+ "values(menu_seq.nextval, ?, ?, ?, ?)";
		Object[] data = {
				menuDto.getMenuName(), menuDto.getMenuType(), 
				menuDto.getMenuPrice(), menuDto.getMenuEvent() 
		};
		jdbcTemplate.update(sql, data);
	}
	
	public boolean update(MenuDto menuDto) {
		String sql = "update menu set menu_name = ?, menu_type = ?, "
				+ "menu_price = ?, menu_event = ? "
				+ "where menu_no = ?";
		Object[] data = {
				menuDto.getMenuName(), menuDto.getMenuType(),
				menuDto.getMenuPrice(), menuDto.getMenuEvent(),
				menuDto.getMenuNo()
		};
		int rows = jdbcTemplate.update(sql, data);
		
		return rows > 0;
	}
	
	public boolean delete(int menuNo) {
		String sql = "delete from menu where menu_no = ?";
		Object[] data = {menuNo};
		
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	public List<MenuDto> selectList() {
		String sql = "select * from menu";
		return jdbcTemplate.query(sql, menuMapper);
	}
	
	private Map<String, String> columnExamples = Map.of(
		"메뉴명", "menu_name",
		"메뉴종류", "menu_type",
		"메뉴가격", "menu_price",
		"행사여부", "menu_event"
	);
	
	public List<MenuDto> selectList(String column, String keyword) {
		String columnName = columnExamples.get(column);
		if(columnName == null) {
			throw new RuntimeException("항목 오");
		}
		
		String sql = "select * from menu where instr(#1, ?) > 0 order by #1 asc, menu_no asc";
		sql = sql.replace("#1", columnName);
		Object[] data = {keyword};
		
		return jdbcTemplate.query(sql, menuMapper, data);
	}
	
	public MenuDto selectOne(int menuNo) {
		String sql = "select * from menu where menu_no = ?";
		Object[] data = {menuNo};
		List<MenuDto> list = jdbcTemplate.query(sql, menuMapper, data);
		
		return list.isEmpty() ? null : list.get(0);
	}
}
