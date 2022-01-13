package com.universitycourseapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universitycourseapp.dto.AdmissionDTO;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.service.IApplicantService;


@RestController
@RequestMapping("/universityCourseApp")
@CrossOrigin("http://localhost:4200")
public class ApplicantController {
	
	@Autowired
	IApplicantService applicants;
	
	/**
	 * 
	 * @param        Applicant
	 * @return       Response Entity of Object type
	 * Description : This method creates a new entry for an applicant.
	 * @GetMapping: maps HTTP GET requests onto specific handler methods.
	 * @throws       The method throws CourseNotFoundException based on improperly entered fields.
	 */
	
	@PostMapping("/ApplicantRegistration") 
	public ResponseEntity<Applicant> ApplicantRegistration(@Validated @RequestBody Applicant applicant) throws CourseNotFoundException{
		Applicant applicant1 = applicants.addApplicant(applicant);
		return new ResponseEntity<>(applicant1, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param        Applicant
	 * @return       Response Entity of Object type
	 * Description : This method updates the existing entry of an applicant.
	 * @PutMapping: maps HTTP PUT requests onto specific handler methods.
	 * @throws       The method throws ApplicantNotFoundException based on improperly entered fields.
	 * 
	 */
	@PutMapping("/UpdateApplicant")
	public ResponseEntity<Applicant> UpdateApplicant(@Validated @RequestBody Applicant applicant) throws ApplicantNotFoundException{
		Applicant updatedStudent = applicants.updateApplicant(applicant);
		return new ResponseEntity<>(updatedStudent ,new HttpHeaders(),HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param        Applicant
	 * @return       Response Entity of Object type
	 * Description : This method deletes the existing entry of an applicant.
	 * @DeleteMapping: maps HTTP DELETE requests onto specific handler methods.
	 * @throws       The method throws ApplicantNotFoundException based on improperly entered fields.
	 * 
	 */
	@DeleteMapping("/DeleteApplicant")
	public ResponseEntity<Applicant> DeleteApplicant(@Validated @RequestBody Applicant applicant) throws ApplicantNotFoundException{
		Applicant delStudent = applicants.deleteApplicant(applicant);
		return new ResponseEntity<>(delStudent,new HttpHeaders() ,HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param        ApplicantId
	 * @return       Applicant
	 * Description : This method is used to view the applicants with applicantId.
	 * @GetMapping: maps HTTP GET requests onto specific handler methods.
	 * @throws       The method throws ApplicantNotFoundException based on improperly entered fields.
	 * 
	 */
	@GetMapping("/ViewApplicant/{applicantId}")
	public Applicant getApplicant(@PathVariable("applicantId") int applicantId) throws ApplicantNotFoundException {
		return applicants.viewApplicant(applicantId);
	}
	
	/**
	 * 
	 * @param        AdmissionStatus
	 * @return       Response Entity of Object type
	 * Description : This method is used to view the applicants with applicant status.
	 * @throws CourseNotFoundException 
	 * @GetMapping: maps HTTP GET requests onto specific handler methods.
	 * @throws       The method throws ApplicantNotFoundException based on improperly entered fields.
	 */
	@GetMapping("/ViewAllApplicantsByStatus/{status}")
	public ResponseEntity<List<AdmissionDTO>> displayAllApplicantsByStatus(@PathVariable AdmissionStatus status) throws ApplicantNotFoundException, CourseNotFoundException{
		List<AdmissionDTO> studentList = applicants.viewAllApplicantsByStatus(status);
		return new ResponseEntity<>(studentList,new HttpHeaders(), HttpStatus.OK);
	
	}
	
	/**
	 * 
	 * @param        CourseId
	 * @return       Response Entity of Object type
	 * Description : This method is used to view the applicants with courseId.
	 * @GetMapping: maps HTTP GET requests onto specific handler methods.
	 * @throws       The method throws appropriate based on improperly entered fields.
	 */
	@GetMapping("/ViewAllApplicantsByCourseId/{courseId}")
	public ResponseEntity<List<Applicant>> displayAllApplicantsByCourseId(@PathVariable int courseId) throws ApplicantNotFoundException, CourseNotFoundException{
		List<Applicant> studentList = applicants.viewAllApplicantsByCourse(courseId);
		return new ResponseEntity<>(studentList,new HttpHeaders(), HttpStatus.OK);
	
	}
 
}
