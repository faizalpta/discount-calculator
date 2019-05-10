package com.onlinestore.discountcalculator.model;

import java.util.stream.Collectors;

import com.onlinestore.discountcalculator.common.ApplicationConstants;

public class PercentileDiscount implements Discount {
	
	private double discountPercentage;
	
	protected void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	@Override
	public Bill applyDiscount(Bill bill) {
		double discountableAmount = bill.getItems().stream()
				.filter(i -> !ApplicationConstants.ITEM_TYPE_GROCERY.equals(i.getCategory()))
				.collect(Collectors.summingDouble(Item::getPrice));
		double discount = discountableAmount * discountPercentage/100;
		bill.setDiscountAmount(discount);
		bill.setFinalAmount(bill.getTotalAmount() - discount);
		return bill;
	}

}
