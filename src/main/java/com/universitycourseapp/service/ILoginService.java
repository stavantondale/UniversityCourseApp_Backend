package com.universitycourseapp.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.universitycourseapp.exception.LoginFailedException;

public interface ILoginService {

	public UserDetails loadUserByUsername(String username);

	public void authenticate(String username, String password) throws LoginFailedException;
}
