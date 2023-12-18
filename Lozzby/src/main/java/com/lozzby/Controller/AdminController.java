package com.lozzby.Controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.lozzby.Service.CategoryService;
import com.lozzby.model.Category;

@Controller
public class AdminController {
	
	@Autowired
	private CategoryService categoryService;

	// http://localhost:8181/admin

	@GetMapping("/admin")
	public String adminHome() {

		return "adminHome";
	}

	@GetMapping("/admin/categories")
	public String getCategorie(Model model) {
        model.addAttribute("categories",this.categoryService.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCategorieAdd(Model model) {
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCategorieAdd(@ModelAttribute("category") Category category) {
		this.categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategorie(@PathVariable int id) {
		this.categoryService.removeCategoryByID(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategories(@PathVariable int id, Model model) {
	Optional<Category> category = this.categoryService.getCategoryById(id);
	  if(category.isPresent()) {
		  model.addAttribute("category", category.get());
		  return "categoriesAdd";
	  }
		
		return "404";
	}
}
