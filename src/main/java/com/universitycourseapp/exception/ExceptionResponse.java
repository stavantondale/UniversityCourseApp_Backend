package com.universitycourseapp.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
	String message;
	int status;
	LocalDateTime time;
	public ExceptionResponse() {
		// TODO Auto-generated constructor stub
	}
	public ExceptionResponse(String message, int status, LocalDateTime date) {
		super();
		this.message = message;
		this.status = status;
		this.time = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime date) {
		this.time = date;
	}
}
