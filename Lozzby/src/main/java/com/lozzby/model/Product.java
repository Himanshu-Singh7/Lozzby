package com.lozzby.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	private String name;
	private double price;
	private double weight;
	private String description;
	private String imageName;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id" ,referencedColumnName = "category_id")
	private Category category;
	
	
}
