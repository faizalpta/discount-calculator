package com.onlinestore.discountcalculator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinestore.discountcalculator.controller.DiscountController;
import com.onlinestore.discountcalculator.model.Bill;
import com.onlinestore.discountcalculator.model.Item;
import com.onlinestore.discountcalculator.model.User;
import com.onlinestore.discountcalculator.service.DiscountService;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class DiscountControllerTest {
	
	@Tested
	private DiscountController discountController;
	
	private MockMvc mockMvc;
	
	private String billJson;
	
	@Injectable
	private DiscountService discountService;
	
	private Bill bill;
	
	@Before
	public void setUp() throws JsonProcessingException {
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
		billJson = new ObjectMapper().writeValueAsString(bill);
		
		bill.setTotalAmount(100);
		
		
	}
	
	private void initMockMvc() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(discountController).build();
	}
	
	@Test
	public void testPrepareDiscount() throws Exception {
		initMockMvc();
		new Expectations() {
			{
				discountService.calculateDiscount((Bill) any);
				Bill bill = new Bill();
				bill.setDiscountAmount(5);
				bill.setFinalAmount(95);
				returns(bill);
			}
			
		};
		mockMvc.perform(post("/applydiscount").contentType(MediaType.APPLICATION_JSON).content(billJson))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testPrepareDiscount_Bad_Request() throws Exception {
		initMockMvc();
		bill.setPayee(null);
		billJson = new ObjectMapper().writeValueAsString(bill);
		mockMvc.perform(post("/applydiscount").contentType(MediaType.APPLICATION_JSON).content(billJson))
		.andExpect(status().is(400));
	}
	
	@Test
	public void testPrepareDiscount_Server_Error() throws Exception {
		initMockMvc();
		new Expectations() {
			{
				discountService.calculateDiscount((Bill) any);
				result = new Exception();
			}
			
		};
		mockMvc.perform(post("/applydiscount").contentType(MediaType.APPLICATION_JSON).content(billJson))
		.andExpect(status().is(500));
	}
}
