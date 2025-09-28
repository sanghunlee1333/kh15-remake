package com.kh.spring07.dto;

import lombok.Data;

@Data
public class ItemDto {
	private int itemNo;
	private String itemName;
	private String itemType;
	private int itemPrice;
	private Float itemDiscountRate;
	private int itemQty;
	private String itemEarly;
}
