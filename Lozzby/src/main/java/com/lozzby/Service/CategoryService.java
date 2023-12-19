package com.lozzby.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lozzby.model.Category;
import com.lozzby.repository.CategoryRepo;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	
	public Category addCategory(Category category) {
		Category saveCategory = this.categoryRepo.save(category);
		return saveCategory;
	}
	
	public List<Category> getAllCategory(){
		List<Category> allCategory = this.categoryRepo.findAll();
		return allCategory;
	}
	
	public void removeCategoryByID(int id) {
		this.categoryRepo.deleteById(id);
	}
	
	public Optional<Category> getCategoryById(int id) {
        
		return this.categoryRepo.findById(id);
	}

}
