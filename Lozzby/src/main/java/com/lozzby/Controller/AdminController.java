package com.lozzby.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.lozzby.Dto.ProductDto;
import com.lozzby.Service.CategoryService;
import com.lozzby.Service.ProductService;
import com.lozzby.model.Category;
import com.lozzby.model.Product;

@Controller
public class AdminController {

	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	// http://localhost:8181/admin

	@GetMapping("/admin")
	public String adminHome() {

		return "adminHome";
	}

	@GetMapping("/admin/categories")
	public String getCategorie(Model model) {
		model.addAttribute("categories", this.categoryService.getAllCategory());
		return "categories";
	}

	@GetMapping("/admin/categories/add")
	public String getCategorieAdd(Model model) {
		model.addAttribute("category", new Category());
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
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		}

		return "404";
	}

	// Product Section

	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products", this.productService.getAllProduct());
		return "products";
	}

	@GetMapping("/admin/products/add")
	public String productAddGet(Model model) {
		model.addAttribute("productDTO", new ProductDto());
		model.addAttribute("categories", this.categoryService.getAllCategory());
		return "productsAdd";

	}

	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO") ProductDto productDTO,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws IOException {

		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		product.setCategory(this.categoryService.getCategoryById(productDTO.getCategoryId()).get());
		String imageUUID;
		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();

			Path fileNameandPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameandPath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		this.productService.addProduct(product);

		return "redirect:/admin/products";

	}

	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		this.productService.removeProductById(id);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/update/{id}")
	public String updateProdect(@PathVariable long id, Model model) {
		Product product = this.productService.getProductById(id).get();

		ProductDto productDTO = new ProductDto();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		productDTO.setCategoryId(product.getCategory().getId());

		model.addAttribute("categories", this.categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		return "productsAdd";
	}
}
