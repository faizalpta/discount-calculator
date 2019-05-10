package com.onlinestore.discountcalculator.model;

public class AffiliateDiscount extends PercentileDiscount {

	private final int DISCOUNT_PERCENT = 10; 

	@Override
	public Bill applyDiscount(Bill bill) {
		super.setDiscountPercentage(DISCOUNT_PERCENT);
		return super.applyDiscount(bill);
	}

}
