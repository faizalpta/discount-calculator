package com.onlinestore.discountcalculator.model;

public class DefaultDiscount implements Discount {

	@Override
	public Bill applyDiscount(Bill bill) {
		double discount = ((int)bill.getTotalAmount()/100) * 5;
		bill.setDiscountAmount(discount);
		bill.setFinalAmount(bill.getTotalAmount() - discount);
		return bill;
	}

}
