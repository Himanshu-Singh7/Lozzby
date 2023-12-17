package com.lozzby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lozzby.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
