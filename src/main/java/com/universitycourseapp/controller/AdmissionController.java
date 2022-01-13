package com.universitycourseapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universitycourseapp.dto.AdmissionDTO;
import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.AdmissionNotFoundException;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.service.IAdmissionService;

@RestController
@RequestMapping("/Admission")
//@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("http://localhost:4200")
public class AdmissionController {

	@Autowired
	IAdmissionService admissionService;
	
	/*******************************
	 * Method : 		addAdmission
	 * 
	 * @param  			admission
	 * @throws 			The method throws different exceptions based on improperly entered
	 *          		fields
	 * @return 			Response Entity of type String 
	 * Description : 	this method adds admission record in table
	 * @throws CourseNotFoundException 
	 * @throws ApplicantNotFoundException 
	 * @postmapping: 	Post mapping requests a body from the user which is then
	 *              	persisted onto the database.
	 ******************************/
	@PostMapping("/AddAdmission")
	public ResponseEntity<Admission> addAdmission( @RequestBody @Validated Admission admission) throws AdmissionAlreadyExistException, CourseNotFoundException, ApplicantNotFoundException{
		Admission admission1 = admissionService.addAdmission(admission);
		return new ResponseEntity<>(admission1, new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * Method : 		updateAdmission
	 * 
	 * @param  			admission
	 * @throws 			The method throws different exceptions based on improperly entered
	 *          		fields
	 * @return 			Response Entity of type String 
	 * Description : 	this method updates admission record in table
	 * @throws Exception 
	 * @putmapping: 	Put mapping requests a body from the user which is then
	 *              	persisted onto the database.
	 ******************************/
	@PutMapping("/updateAdmission")
	public ResponseEntity<Admission> updateAdmission(@RequestBody @Validated Admission admission) throws Exception{
		Admission admission1 = admissionService.updateAdmission(admission);
		return new ResponseEntity<>(admission1, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	/*******************************
	 * Method : 		confirmAdmission
	 * 
	 * @param  			admissionId
	 * @throws 			this method throws AdmissionNotFoundException
	 * @return 			Response Entity of type String 
	 * Description : 	this method updates admission status 
	 * @Getmapping: 	Get mapping map http get request to the method
	 *              	
	 ******************************/
	@GetMapping("/confirmAdmission/{admissionId}")
	public ResponseEntity<Admission> confirmAdmission(@PathVariable int admissionId) throws AdmissionNotFoundException, ApplicantNotFoundException{
		Admission admission = admissionService.confirmAdmission(admissionId);
		return new ResponseEntity<>(admission, new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * Method : 		cancelAdmission
	 * 
	 * @param  			admissionId
	 * @throws 			this method throws AdmissionNotFoundException
	 * @return 			Response Entity of type String 
	 * Description : 	this method updates admission status to rejected
	 * @Getmapping: 	Get mapping map http get request to the method
	 *              	
	 ******************************/
	@GetMapping("/cancelAdmission/{admissionId}")
	public ResponseEntity<Admission> cancelAdmission(@PathVariable int admissionId) throws AdmissionNotFoundException, ApplicantNotFoundException{
		Admission admission = admissionService.cancelAdmission(admissionId);
		return new ResponseEntity<>(admission, new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * Method : 		showAllAdmissionByCourseId
	 * 
	 * @param  			courseId of type int
	 * @throws 			this method throws AdmissionNotFoundException, CourseNotFoundException
	 * @return 			List of type Admission
	 * Description : 	this method returns list of Admissions by specific course 
	 * @throws ApplicantNotFoundException 
	 * @Getmapping: 	Get mapping map http get request to the method
	 *              	
	 ******************************/
	@GetMapping("/getAdmissionsByCourseId/{courseId}")
	public ResponseEntity<List<AdmissionDTO>> showAllAdmissionByCourseId(@PathVariable("courseId") int courseId) throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException{
		List<AdmissionDTO> admissions = admissionService.showAllAdmissionByCourseId(courseId);
		return new ResponseEntity<>(admissions,new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * Method : 		showAllAdmission
	 * 
	 * @param  			
	 * @throws 			this method throws AdmissionNotFoundException
	 * @return 			List of type AdmissionDTO
	 * Description : 	this method returns list of Admissions by specific date 
	 * @throws ApplicantNotFoundException 
	 * @throws CourseNotFoundException 
	 * @Getmapping: 	Get mapping map http get request to the method
	 *              	
	 ******************************/
	@GetMapping("/getAllAdmissions")
	public ResponseEntity<List<AdmissionDTO>> showAllAdmissions() throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException{
		List<AdmissionDTO> admissions = admissionService.showAllAdmissions();
		return new ResponseEntity<>(admissions,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	/*******************************
	 * Method : 		showAllAdmissionByDate
	 * 
	 * @param  			date of type LocalDate
	 * @throws 			this method throws AdmissionNotFoundException
	 * @return 			List of type Admission
	 * Description : 	this method returns list of Admissions by specific date 
	 * @throws ApplicantNotFoundException 
	 * @throws CourseNotFoundException 
	 * @Getmapping: 	Get mapping map http get request to the method
	 *              	
	 ******************************/
	@GetMapping("/getAdmissionsByDate/{date}")
	public ResponseEntity<List<AdmissionDTO>> showAllAdmissionByDate(@PathVariable("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException{
		List<AdmissionDTO> admissions = admissionService.showAllAdmissionByDate(date);
		return new ResponseEntity<>(admissions,new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * Method : 		showAllAdmissionByApplicant
	 * 
	 * @param  			applicantId of type int
	 * @throws 			this method throws AdmissionNotFoundException
	 * @return 			List of type Admission
	 * Description : 	this method returns list of Admissions by specific applicant 
	 * @throws AdmissionNotFoundException 
	 * @throws ApplicantNotFoundException 
	 * @throws CourseNotFoundException 
	 * @Getmapping: 	Get mapping map http get request to the method
	 *              	
	 ******************************/
	@GetMapping("/getAdmissionsByApplicant/{applicantId}")
	public ResponseEntity<AdmissionDTO> showAllAdmissionByApplicant(@PathVariable("applicantId") int applicantId) throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException {
		AdmissionDTO admissions = admissionService.showAllAdmissionsByApplicant(applicantId);
		return new ResponseEntity<>(admissions,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	/*******************************
	 * Method : 		calculateTotalCost
	 * 
	 * @param  			courseId of type int
	 * @throws 			this method throws AdmissionNotFoundException
	 * @return 			List of type Admission
	 * Description : 	this method returns list of Admissions by specific date 
	 * @throws AdmissionNotFoundException 
	 * @Getmapping: 	Get mapping map http get request to the method
	 *              	
	 ******************************/
	@GetMapping("/calculateTotalCost/{courseId}")
	public ResponseEntity<Double> calculateTotalCost(@PathVariable("courseId") int courseId) throws CourseNotFoundException, AdmissionNotFoundException{
		double totalCost = admissionService.calculateTotalCost(courseId);
		return new ResponseEntity<>(totalCost,new HttpHeaders(), HttpStatus.OK);
	}
}
