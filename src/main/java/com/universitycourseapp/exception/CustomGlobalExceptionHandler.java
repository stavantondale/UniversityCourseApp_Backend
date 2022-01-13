package com.universitycourseapp.exception;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("Status", status.value());
		
		//Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
		body.put("Errors", errors);
		return new ResponseEntity<>(body, headers, status);
	}
	
	@ExceptionHandler(value = AdmissionNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleAdmissionNotFoundException(AdmissionNotFoundException exception, WebRequest webRequest){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setMessage(exception.getMessage());
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = AdmissionAlreadyExistException.class)
	public ResponseEntity<ExceptionResponse> handleAdmissionAlreadyExistException(AdmissionAlreadyExistException exception, WebRequest webRequest){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setMessage(exception.getMessage());
		exceptionResponse.setStatus(500);
		exceptionResponse.setTime(LocalDateTime.now());
		return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = CourseNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleCourseNotFoundException(CourseNotFoundException e,
			WebRequest webRequest) {

		ExceptionResponse exceptionResponce = new ExceptionResponse();
		exceptionResponce.setStatus(404);
		exceptionResponce.setTime(LocalDateTime.now());
		exceptionResponce.setMessage(e.getMessage());

		return new ResponseEntity<>(exceptionResponce, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleCourseIsAlreadyExistException(CourseIsAlreadyExistException e, WebRequest webRequest){
		ExceptionResponse exceptionResponce = new ExceptionResponse();
		exceptionResponce.setStatus(404);
		exceptionResponce.setTime(LocalDateTime.now());
		exceptionResponce.setMessage(e.getMessage());
		return new ResponseEntity<>(exceptionResponce, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value = UniversityStaffNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUniversityStaffNotFoundException(UniversityStaffNotFoundException exception, WebRequest webRequest){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setMessage(exception.getMessage());
		exceptionResponse.setTime(LocalDateTime.now());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value =DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionResponse> handleUniqueConstraintViolationException(DataIntegrityViolationException exception, WebRequest webRequest){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(406);
		exceptionResponse.setMessage("Username not available");
		exceptionResponse.setTime(LocalDateTime.now());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = CommitteeMemberNotFound.class)
	public ResponseEntity<ExceptionResponse> handleCommitteeMemberNotFOundException(CommitteeMemberNotFound exception, WebRequest webRequest){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setMessage(exception.getMessage());
		exceptionResponse.setTime(LocalDateTime.now());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value = LoginFailedException.class)
	public ResponseEntity<ExceptionResponse> handleLoginFailedException(LoginFailedException exception, WebRequest webRequest){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(406);
		exceptionResponse.setMessage(exception.getMessage());
		exceptionResponse.setTime(LocalDateTime.now());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(value = ApplicantNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleApplicantNotFoundException(ApplicantNotFoundException exception, WebRequest webRequest){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setMessage(exception.getMessage());
		exceptionResponse.setTime(LocalDateTime.now());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> handleParentException(Exception e,
			WebRequest webRequest) {
		e.printStackTrace();
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(500);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(e.getMessage());
		e.printStackTrace();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
