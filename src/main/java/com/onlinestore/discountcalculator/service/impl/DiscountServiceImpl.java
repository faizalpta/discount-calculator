package com.onlinestore.discountcalculator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinestore.discountcalculator.factory.DiscountFactory;
import com.onlinestore.discountcalculator.model.Bill;
import com.onlinestore.discountcalculator.model.Discount;
import com.onlinestore.discountcalculator.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
	
	@Autowired
	private DiscountFactory discountFactory;

	/**
	 * The default discount will be applied first. Then, the special discount
	 * will be calculated if any exist for the user and that amount  will be deducted
	 * from the default discount applied bill amount.
	 * 
	 * @param bill
	 * @return bill after discount applied
	 */
	@Override
	public Bill calculateDiscount(Bill bill) {
		Discount defaultDiscount = discountFactory.getDefaultDiscount();
		Bill defDiscountedBill = defaultDiscount.applyDiscount(bill);
		Discount splDiscount = discountFactory.getSpecialDiscount(bill.getPayee());
		if(null != splDiscount) {
			double defDiscount = defDiscountedBill.getDiscountAmount();
			Bill splDIscountedBill = splDiscount.applyDiscount(bill);
			defDiscountedBill.setFinalAmount(splDIscountedBill.getFinalAmount() - defDiscount);
			defDiscountedBill.setDiscountAmount(defDiscount	+ splDIscountedBill.getDiscountAmount());
		}
		return defDiscountedBill;
	}

}
