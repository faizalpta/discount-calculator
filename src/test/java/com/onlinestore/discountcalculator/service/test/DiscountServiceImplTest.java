package com.onlinestore.discountcalculator.service.test;


import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.onlinestore.discountcalculator.factory.DiscountFactory;
import com.onlinestore.discountcalculator.model.AffiliateDiscount;
import com.onlinestore.discountcalculator.model.Bill;
import com.onlinestore.discountcalculator.model.DefaultDiscount;
import com.onlinestore.discountcalculator.model.EmployeeDiscount;
import com.onlinestore.discountcalculator.model.Item;
import com.onlinestore.discountcalculator.model.LongTermDiscount;
import com.onlinestore.discountcalculator.model.User;
import com.onlinestore.discountcalculator.service.impl.DiscountServiceImpl;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class DiscountServiceImplTest {
	
	@Tested
	private DiscountServiceImpl discountServiceImpl;

	@Injectable
	private DiscountFactory discountFactory;
	
	private Bill bill;
	
	@Before
	public void setUp() {
		bill = new Bill();
		Item item = new Item();
		item.setId(1L);
		item.setCategory("Electronics");
		item.setPrice(100);
		bill.setItems(new ArrayList<>());
		bill.getItems().add(item);
		
		User user = new User();
		user.setType("Normal");
		user.setUserName("sam");
		user.setRegisteredDate(Calendar.getInstance().getTime());
		bill.setPayee(user);
		
		bill.setTotalAmount(100);
		
	}
	
	@Test
	public void testDefaultDiscount() {
		new Expectations() {
			{
				discountFactory.getDefaultDiscount();
				returns(new DefaultDiscount());
			}
			{
				discountFactory.getSpecialDiscount((User)any);
				returns(null);
			}
		};
		
		Bill discountedBill = discountServiceImpl.calculateDiscount(bill);
		Assert.assertNotNull(discountedBill);
		Assert.assertTrue(5 == discountedBill.getDiscountAmount());
		Assert.assertTrue(95 == discountedBill.getFinalAmount());
		
	}
	
	@Test
	public void testEmployeeDiscount() {
		new Expectations() {
			{
				discountFactory.getDefaultDiscount();
				returns(new DefaultDiscount());
			}
			{
				discountFactory.getSpecialDiscount((User)any);
				returns(new EmployeeDiscount());
			}
		};
		
		Item item = new Item();
		item.setId(2L);
		item.setCategory("Grocery");
		item.setPrice(100);
		bill.getItems().add(item);
		bill.setTotalAmount(200);
		Bill discountedBill = discountServiceImpl.calculateDiscount(bill);
		Assert.assertNotNull(discountedBill);
		Assert.assertTrue(40 == discountedBill.getDiscountAmount());
		Assert.assertTrue(160 == discountedBill.getFinalAmount());
		
	}
	
	@Test
	public void testAffiliateDiscount() {
		new Expectations() {
			{
				discountFactory.getDefaultDiscount();
				returns(new DefaultDiscount());
			}
			{
				discountFactory.getSpecialDiscount((User)any);
				returns(new AffiliateDiscount());
			}
		};
		
		Item item = new Item();
		item.setId(2L);
		item.setCategory("Grocery");
		item.setPrice(100);
		bill.getItems().add(item);
		bill.setTotalAmount(200);
		Bill discountedBill = discountServiceImpl.calculateDiscount(bill);
		Assert.assertNotNull(discountedBill);
		Assert.assertTrue(20 == discountedBill.getDiscountAmount());
		Assert.assertTrue(180 == discountedBill.getFinalAmount());
		
	}
	
	@Test
	public void testLongTermDiscount() {
		new Expectations() {
			{
				discountFactory.getDefaultDiscount();
				returns(new DefaultDiscount());
			}
			{
				discountFactory.getSpecialDiscount((User)any);
				returns(new LongTermDiscount());
			}
		};
		
		Item item = new Item();
		item.setId(2L);
		item.setCategory("Grocery");
		item.setPrice(100);
		bill.getItems().add(item);
		bill.setTotalAmount(200);
		Calendar regDate = Calendar.getInstance();
		regDate.set(Calendar.YEAR, 2015);
		bill.getPayee().setRegisteredDate(regDate.getTime());
		Bill discountedBill = discountServiceImpl.calculateDiscount(bill);
		Assert.assertNotNull(discountedBill);
		Assert.assertTrue(15 == discountedBill.getDiscountAmount());
		Assert.assertTrue(185 == discountedBill.getFinalAmount());
		
	}
	
	@Test
	public void testLongTermDiscount_Lessthan_two_years() {
		new Expectations() {
			{
				discountFactory.getDefaultDiscount();
				returns(new DefaultDiscount());
			}
			{
				discountFactory.getSpecialDiscount((User)any);
				returns(null);
			}
		};
		
		Item item = new Item();
		item.setId(2L);
		item.setCategory("Grocery");
		item.setPrice(100);
		bill.getItems().add(item);
		bill.setTotalAmount(200);
		Calendar regDate = Calendar.getInstance();
		regDate.add(Calendar.YEAR, -1);
		bill.getPayee().setRegisteredDate(regDate.getTime());
		Bill discountedBill = discountServiceImpl.calculateDiscount(bill);
		Assert.assertNotNull(discountedBill);
		Assert.assertTrue(10 == discountedBill.getDiscountAmount());
		Assert.assertTrue(190 == discountedBill.getFinalAmount());
		
	}
}
