package com.onlinestore.discountcalculator.factory;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.onlinestore.discountcalculator.model.AffiliateDiscount;
import com.onlinestore.discountcalculator.model.DefaultDiscount;
import com.onlinestore.discountcalculator.model.Discount;
import com.onlinestore.discountcalculator.model.EmployeeDiscount;
import com.onlinestore.discountcalculator.model.LongTermDiscount;
import com.onlinestore.discountcalculator.model.User;

import mockit.Deencapsulation;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class DiscountFactoryTest {
	
	@Tested
	private DiscountFactory discountFactory;
	
	private User user;
	
	@Before
	public void setUp() {
		user = new User();
		user.setUserName("sam");
	}
	
	@Test
	public void testGetDefaultDiscount() {
		Discount defaultDiscount = discountFactory.getDefaultDiscount();
		
		Assert.assertTrue(defaultDiscount instanceof DefaultDiscount);
	}
	
	@Test
	public void testGetSpecialDiscount_For_Employee() {
		user.setType("Employee");
		Discount discount = discountFactory.getSpecialDiscount(user);
		
		Assert.assertTrue(discount instanceof EmployeeDiscount);
	}
	
	@Test
	public void testGetSpecialDiscount_For_Affiliate() {
		user.setType("Affiliate");
		Discount discount = discountFactory.getSpecialDiscount(user);
		
		Assert.assertTrue(discount instanceof AffiliateDiscount);
	}
	
	@Test
	public void testGetSpecialDiscount_For_Lonterm_Customer() {
		Calendar regDate = Calendar.getInstance();
		regDate.add(Calendar.YEAR, -3);
		user.setRegisteredDate(regDate.getTime());
		Discount discount = discountFactory.getSpecialDiscount(user);
		
		Assert.assertTrue(discount instanceof LongTermDiscount);
	}
	
	@Test
	public void testGetSpecialDiscount_For_Normal_Customer() {
		Calendar regDate = Calendar.getInstance();
		regDate.add(Calendar.YEAR, -1);
		user.setRegisteredDate(regDate.getTime());
		Discount discount = discountFactory.getSpecialDiscount(user);
		
		Assert.assertNull(discount);
	}
	
	@Test
	public void testIsLongTermCustomer_Variation_Month() {
		Calendar regDate = Calendar.getInstance();
		regDate.add(Calendar.YEAR, -2);
		regDate.add(Calendar.MONTH, 2);
		user.setRegisteredDate(regDate.getTime());
		boolean isLomgTermCustomer = Deencapsulation.invoke(discountFactory, "isLongTermCustomer", user);
		Assert.assertFalse(isLomgTermCustomer);
	}
	
	@Test
	public void testIsLongTermCustomer_Equal_Month() {
		Calendar regDate = Calendar.getInstance();
		regDate.add(Calendar.YEAR, -2);
		regDate.add(Calendar.DATE, -2);
		user.setRegisteredDate(regDate.getTime());
		boolean isLomgTermCustomer = Deencapsulation.invoke(discountFactory, "isLongTermCustomer", user);
		Assert.assertTrue(isLomgTermCustomer);
	}
	
	@Test
	public void testIsLongTermCustomer_Variation_Date() {
		Calendar regDate = Calendar.getInstance();
		regDate.add(Calendar.YEAR, -2);
		regDate.add(Calendar.DATE, 2);
		user.setRegisteredDate(regDate.getTime());
		boolean isLomgTermCustomer = Deencapsulation.invoke(discountFactory, "isLongTermCustomer", user);
		Assert.assertFalse(isLomgTermCustomer);
	}

}
