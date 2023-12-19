package com.lozzby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lozzby.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findUserByEmail(String email);
}
