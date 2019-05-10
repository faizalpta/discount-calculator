package com.onlinestore.discountcalculator.factory;

import static com.onlinestore.discountcalculator.common.ApplicationConstants.USER_TYPE_AFFILIATE;
import static com.onlinestore.discountcalculator.common.ApplicationConstants.USER_TYPE_EMPLOYEE;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.onlinestore.discountcalculator.model.AffiliateDiscount;
import com.onlinestore.discountcalculator.model.DefaultDiscount;
import com.onlinestore.discountcalculator.model.Discount;
import com.onlinestore.discountcalculator.model.EmployeeDiscount;
import com.onlinestore.discountcalculator.model.LongTermDiscount;
import com.onlinestore.discountcalculator.model.User;

@Component
public class DiscountFactory {
	
	private static final int LONG_TERM_YEARS = 2;

	public Discount getSpecialDiscount(User user) {
		Discount discount;

		if(USER_TYPE_EMPLOYEE.equals(user.getType())) {
			discount = new EmployeeDiscount();
		} else if(USER_TYPE_AFFILIATE.equals(user.getType())) {
			discount = new AffiliateDiscount();
		} else if(isLongTermCustomer(user)) {
			discount = new LongTermDiscount();
		} else {
			discount = null;
		}
		
		return discount;
	}
	
	public Discount getDefaultDiscount() {
		return new DefaultDiscount();
	}

	private boolean isLongTermCustomer(User user) {
		Calendar regCal = Calendar.getInstance();
		regCal.setTime(user.getRegisteredDate());
		Calendar currCal = Calendar.getInstance();
		int diffInYears = currCal.get(Calendar.YEAR) - regCal.get(Calendar.YEAR);
		if(regCal.get(Calendar.MONTH) > currCal.get(Calendar.MONTH)
				|| (regCal.get(Calendar.MONTH) == currCal.get(Calendar.MONTH) 
				&& (regCal.get(Calendar.DATE) > currCal.get(Calendar.DATE)))){
			diffInYears--;
		}
		return diffInYears >= LONG_TERM_YEARS;
	}
	
	
}
