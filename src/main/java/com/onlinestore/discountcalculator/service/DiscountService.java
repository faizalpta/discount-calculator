package com.onlinestore.discountcalculator.service;

import com.onlinestore.discountcalculator.model.Bill;

public interface DiscountService {

	Bill calculateDiscount(Bill bill);
}
