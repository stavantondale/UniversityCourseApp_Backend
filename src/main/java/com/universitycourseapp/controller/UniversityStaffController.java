package com.universitycourseapp.controller;

import java.util.List;
import java.util.Map;

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

import com.universitycourseapp.entities.Course;
import com.universitycourseapp.entities.UniversityStaffMember;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.CourseIsAlreadyExistException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.exception.UniversityStaffNotFoundException;
import com.universitycourseapp.service.IUniversityStaffService;


@RestController
@RequestMapping("/universitystaff")
@CrossOrigin(origins="http://localhost:4200")
public class UniversityStaffController {
	@Autowired //
	IUniversityStaffService universitystaffservice;
	
	/*******************************
	 * 
	 * Method :      addStaff
	 * @param        UniversityStaffMember
	 * @return       Response Entity of Object type.
	 * Description : This method creates a new entry for an staffMember.   
	 * @PostMapping: Post mapping requests a body from the user which is then  persisted onto the database.
	 *              
	 ******************************/

	@PostMapping("/UnviersityStaffCreation")
	public ResponseEntity<UniversityStaffMember> universityStaffCreation(@RequestBody @Validated  UniversityStaffMember staff) {
		UniversityStaffMember staffMember = universitystaffservice.addStaff(staff);
		return new ResponseEntity<>(staffMember,new HttpHeaders(), HttpStatus.OK);
	}

	/*******************************
	 * 
	 * Method :      updateStaff
	 * @param        UniversityStaffMember
	 * @return       Response Entity of Object type 
	 * Description : This method Updates record of staffMember.   
	 * @throws Exception 
	 * @Putmapping: It is used to accept HTTP Put request containing a Request Body.
	 *              
	 ******************************/
	
	// UpdateStaff
	@PutMapping("/UpdateStaff")
	public ResponseEntity<UniversityStaffMember> updateUniversityStaff(@RequestBody @Validated UniversityStaffMember staff) throws Exception {
		staff = universitystaffservice.updateStaff(staff);
		return new ResponseEntity<>(staff, new HttpHeaders(), HttpStatus.OK);
	}

	/*******************************
	 * 
	 * Method :        removeStaff
	 * @param          staffid
	 * @PathVariable : This annotation is used to extract the value from the URI.
	 * @return         Response Entity of Object type
	 * Description :   This method remove record of staffMember.
	 * @DeleteMapping: annotation maps HTTP DELETE requests onto specific handler methods.
	 *              
	 ******************************/
	
	// RemoveStaff()
	@DeleteMapping("/universityRemovestaff/{id}")
	public ResponseEntity<Void> removeStaff(@PathVariable("id") int id) throws UniversityStaffNotFoundException {
		universitystaffservice.removeStaff(id);
		return new ResponseEntity<Void>( new HttpHeaders(), HttpStatus.OK);
	}

	/*******************************
	 * 
	 * Method :      viewStaffById
	 * @param        staffid
	 * @PathVariable: This annotation is used to extract the value from the URI.
	 * @return       Response Entity of Object type 
	 * Description : This method view record of staffMember.   
	 * @GetMapping:  annotated methods handle the HTTP GET requests matched with given URI expression.
	 *              
	 ******************************/
	
	// viewStaffbyid
	@GetMapping("/viewStaffById/{staffid}")
	public ResponseEntity<UniversityStaffMember> viewStaffById(@PathVariable int staffid)
			throws UniversityStaffNotFoundException {
		UniversityStaffMember staff = universitystaffservice.viewStaff(staffid);
		return new ResponseEntity<>(staff, new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * 
	 * Method :      viewAllStaffs
	 * @param        .
	 * @throws       The method throws UniversityStaffNotFoundException different exceptions based on improperly entered fields.
	 * @return       Response Entity of Object type 
	 * Description : This method viewAllStaffs.   
	 * @GetMapping:  annotated methods handle the HTTP GET requests matched with given URI expression.
	 *              
	 ******************************/
	

	@GetMapping("/viewAllStaffs")
	public ResponseEntity<List<UniversityStaffMember>> viewAllStaffs() throws UniversityStaffNotFoundException {
		List<UniversityStaffMember> staffList = universitystaffservice.viewAllStaffs();
			return new ResponseEntity<>(staffList, new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * 
	 * Method :      addCourse
	 * @param        Course
	 * @throws       The method throws CourseNotFoundException different exceptions based on improperly entered fields.
	 * @return       Response Entity of Object type 
	 * Description : This method creates a  new course.   
	 * @throws CourseIsAlreadyExistException 
	 * @PostMapping: Post mapping requests a body from the user which is then  persisted onto the database.
	 *              
	 ******************************/
	

	@PostMapping("/universityStaffaddedcourse")
	public ResponseEntity<String> addCourse(@RequestBody Course course) throws CourseNotFoundException, CourseIsAlreadyExistException {
		universitystaffservice.addCourse(course);
		return new ResponseEntity<>("Course Added Successfully", new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * 
	 * Method :      removeCourse
	 * @param        courseid
	 * @throws       The method throws CourseNotFoundException different exceptions based on improperly entered fields.
	 * @return       Response Entity of Object type 
	 * Description : This method remove course.   
	 * @throws AdmissionAlreadyExistException 
	 * @DeleteMapping: annotation maps HTTP DELETE requests onto specific handler methods.
	 *              
	 ******************************/

	@DeleteMapping("/universityRemovecourse/{id}")
	public ResponseEntity<String> removeCourse(@PathVariable("id") int id) throws CourseNotFoundException, AdmissionAlreadyExistException {
		universitystaffservice.removeCourse(id);
		return new ResponseEntity<>("Course remove successfully", new HttpHeaders(), HttpStatus.OK);
	}
	
	/*******************************
	 * Method :       updateCourse
	 * @param         Course
	 * @throws        The method throws CourseNotFoundException different exceptions based on improperly entered fields.
	 * @return        Response Entity of Object type 
	 * Description :  This method updates record of course.   
	 * @Putmapping:   It is used to accept HTTP Put request containing a Request Body.
	 *              
	 ******************************/

	@PutMapping("/Updatecourse")
	public ResponseEntity<String> updateCourse(@RequestBody Course course) throws CourseNotFoundException {
		universitystaffservice.updateCourse(course);
		return new ResponseEntity<>("Update Successfully", new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/getStatistics")
	public ResponseEntity<Map<String, Integer>> getStatistics(){
		Map<String, Integer> stats = universitystaffservice.getStats();
		return new ResponseEntity<>(stats, new HttpHeaders(), HttpStatus.OK);
	}
}
