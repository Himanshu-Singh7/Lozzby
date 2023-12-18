package com.lozzby.Dto;

import lombok.Data;

@Data
public class ProductDto {

	private Long id;
	private String name;
	private double price;
	private double weight;
	private String description;
	private String imageName;
	private int categoryId;
}
