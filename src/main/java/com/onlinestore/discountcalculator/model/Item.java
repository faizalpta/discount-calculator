package com.onlinestore.discountcalculator.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Item {

	@NotNull
	private Long Id;
	
	@NotNull
	private String category;
	
	@NotNull
	private double price;
}
