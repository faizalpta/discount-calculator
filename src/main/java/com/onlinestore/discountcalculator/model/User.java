package com.onlinestore.discountcalculator.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class User {

	@NotNull
	private String userName;
	
	@NotNull
	private String type;
	
	@NotNull
	private Date registeredDate;
}
