package com.kh.spring07.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring07.dto.ItemDto;
import com.kh.spring07.mapper.ItemMapper;

@Repository
public class ItemDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ItemMapper itemMapper;
	
	public void insert(ItemDto itemDto) {
		String sql = "insert into item(item_no, item_name, "
				+ "item_type, item_price, "
				+ "item_discount_rate, item_qty, item_early) "
				+ "values(item_seq.nextval, ?, ?, ?, ?, ?, ?)";
		Object[] data = {
				itemDto.getItemName(), itemDto.getItemType(),
				itemDto.getItemPrice(), itemDto.getItemDiscountRate(),
				itemDto.getItemQty(), itemDto.getItemEarly()
		};
		jdbcTemplate.update(sql, data);
	}
	
	public boolean update(ItemDto itemDto) {
		String sql = "update item set item_name = ?, item_type = ?, "
				+ "item_price = ?, item_discount_rate = ?, item_qty = ?, "
				+ "item_early = ? where item_no = ?";
		Object[] data = {
				itemDto.getItemName(), itemDto.getItemType(),
				itemDto.getItemPrice(), itemDto.getItemDiscountRate(),
				itemDto.getItemQty(), itemDto.getItemEarly(),
				itemDto.getItemNo()
		};
		int rows = jdbcTemplate.update(sql, data);
		
		return rows > 0; 
	}
	
	public boolean delete(int itemNo) {
		String sql = "delete from item where item_no = ?";
		Object[] data = {itemNo};
		
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	public List<ItemDto> selectList() {
		String sql = "select * from item";
		
		return jdbcTemplate.query(sql, itemMapper);
	}
	
	private Map<String, String> columnExamples = Map.of(
		"아이템명", "item_name",
		"아이템종류", "item_type"
	);
	
	public List<ItemDto> selectList(String column, String keyword) {
		String columnName = columnExamples.get(column);
		if(columnName == null) {
			throw new RuntimeException("항목 오류");
		}
		String sql = "select * from item where instr(#1, ?) > 0 order by #1 asc, item_no asc";
		sql = sql.replace("#1", columnName);
		Object[] data = {keyword};
		
		return jdbcTemplate.query(sql, itemMapper, data);
	}
	
	public ItemDto selectOne(int itemNo) {
		String sql = "select * from item where item_no = ?";
		Object[] data = {itemNo};
		List<ItemDto> list = jdbcTemplate.query(sql, itemMapper, data);
		
		return list.isEmpty() ? null : list.get(0);
	}
}
