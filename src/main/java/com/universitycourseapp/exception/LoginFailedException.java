package com.universitycourseapp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class LoginFailedException extends Exception {
public LoginFailedException(String message) {
super(message);
}
}
