package com.onlinestore.discountcalculator.model;

public class EmployeeDiscount extends PercentileDiscount {
	
	private final int DISCOUNT_PERCENT = 30; 

	@Override
	public Bill applyDiscount(Bill bill) {
		super.setDiscountPercentage(DISCOUNT_PERCENT);
		return super.applyDiscount(bill);
	}

}
