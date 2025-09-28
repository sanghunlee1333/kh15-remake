package com.kh.spring07.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring07.dto.ItemDto;

@Component
public class ItemMapper implements RowMapper<ItemDto> {

	@Override
	public ItemDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemDto itemDto = new ItemDto();
		
		itemDto.setItemNo(rs.getInt("item_no"));
		itemDto.setItemName(rs.getString("item_name"));
		itemDto.setItemPrice(rs.getInt("item_price"));
		itemDto.setItemType(rs.getString("item_type"));
		itemDto.setItemDiscountRate(rs.getFloat("item_discount_rate"));
		itemDto.setItemQty(rs.getInt("item_qty"));
		itemDto.setItemEarly(rs.getString("item_early"));
		
		return itemDto;
	}

}
