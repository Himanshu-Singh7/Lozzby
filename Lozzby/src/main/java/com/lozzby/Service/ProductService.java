package com.lozzby.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lozzby.model.Product;
import com.lozzby.repository.ProductRepo;

@Service
public class ProductService {
    
	@Autowired
	private ProductRepo productRepo;
	
	
	public List<Product> getAllProduct(){
		List<Product> product = this.productRepo.findAll();
		return product;
	}
	
	public Product addProduct(Product product) {
		Product saveProduct = this.productRepo.save(product);
		return saveProduct;
	}
	
	public void removeProductById(long id) {
		this.productRepo.deleteById(id);
	}
	
	public Optional<Product> getProductById(long id){
		Optional<Product> singleProduct = this.productRepo.findById(id);
		return singleProduct;
	}
	
	public List<Product> getAllProductsByCategoryId(int id){
		
		return this.productRepo.findAllByCategory_Id(id);
		
	}
	
	
	
}
