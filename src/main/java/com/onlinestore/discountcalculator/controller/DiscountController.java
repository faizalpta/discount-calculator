package com.onlinestore.discountcalculator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlinestore.discountcalculator.model.Bill;
import com.onlinestore.discountcalculator.service.DiscountService;

@RestController("/applydiscount")
public class DiscountController {
	
	@Autowired
	private DiscountService discountService;
	
	@PostMapping
	public ResponseEntity<Bill> processDiscount(@RequestBody @Valid Bill bill){
		Bill discountedBill = discountService.calculateDiscount(bill);
		return new ResponseEntity<Bill>(discountedBill, HttpStatus.OK);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> onException(Throwable throwable) {
		if(throwable instanceof MethodArgumentNotValidException) {
			return new ResponseEntity<String>("Invalid input!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Internal Server Error!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
