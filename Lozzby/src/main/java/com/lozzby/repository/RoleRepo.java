package com.lozzby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lozzby.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{

}
