package com.universitycourseapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.universitycourseapp.entities.User;

public interface UserLoginRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
