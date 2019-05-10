package com.onlinestore.discountcalculator.model;

public class LongTermDiscount extends PercentileDiscount {

	private final int DISCOUNT_PERCENT = 5; 

	@Override
	public Bill applyDiscount(Bill bill) {
		super.setDiscountPercentage(DISCOUNT_PERCENT);
		return super.applyDiscount(bill);
	}

}

