package com.onlinestore.discountcalculator.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Bill {

	@NotNull
	private List<Item> items;
	
	@NotNull
	private User payee;
	
	/**
	 * total amount before applying discount
	 */
	@NotNull
	private double totalAmount;
	
	/**
	 * discount amount
	 */
	private double discountAmount;
	
	/**
	 * final bill amount after discount
	 */
	private double finalAmount;	

}
