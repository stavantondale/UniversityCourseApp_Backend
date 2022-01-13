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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.universitycourseapp.entities.Course;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.CourseIsAlreadyExistException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.service.ICourseService;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

	@Autowired
	ICourseService courseService;


	/*******************************
	 * 
	 * Method :      courseCreation
	 * @param        Course
	 * @throws       The method throws CourseNotFoundException based on improperly entered fields.
	 * @return       Response Entity of Object type 
	 * Description : This method creates a  new course.   
	 * @PostMapping: Post mapping requests a body from the user which is then  persisted onto the database.
	 *              
	 ******************************/
	@PostMapping("/courseCreation") // http://localhost:8590/course/courseCreation
	public ResponseEntity<Course> courseCreation(@RequestBody @Validated Course course) throws CourseIsAlreadyExistException {
		course = courseService.addCourse(course);
		return new ResponseEntity<Course>(course, new HttpHeaders(), HttpStatus.OK);
	}

	/*******************************
	 * 
	 * Method :      getCourse
	 * @param        courseid
	 * @PathVariable: This annotation is used to extract the value from the URL.
	 * @return       Response Entity of Object type 
	 * Description : This method view record of staffMember.   
	 * @GetMapping:  annotated methods handle the HTTP GET requests matched with given URI expression.
	 *              
	 ******************************/
	@GetMapping("/getCourse/{id}") // http://localhost:8590/course/getCourse/123
	public Course getCourse(@PathVariable("id") int id) throws CourseNotFoundException {
		return courseService.viewCourse(id);

	}

	/*******************************
	 * 
	 * Method :      getAllCourse
	 * @param        .
	 * @throws       The method throws CourseNotFoundException based on improperly entered fields.
	 * @return       Response Entity of Object type 
	 * Description : This method show all courses.   
	 * @GetMapping:  annotated methods handle the HTTP GET requests matched with given URI expression.
	 *              
	 ******************************/
	@GetMapping("/getAllCourse") // http://localhost:8590/course/getAllCourse
	public ResponseEntity<List<Course>> getAllCourse() throws CourseNotFoundException {
		List<Course> list = courseService.viewAllCourses();
		return new ResponseEntity<List<Course>>(list, new HttpHeaders(), HttpStatus.OK);

	}

	/*******************************
	 * Method :       updateCourse
	 * @param         Course
	 * @throws        The method throws CourseNotFoundException based on improperly entered fields.
	 * @return        Response Entity of Object type 
	 * Description :  This method updates record of course.   
	 * @Putmapping:   It is used to accept HTTP Put request containing a Request Body.
	 *              
	 ******************************/
	@PutMapping("/updateCourse") // http://localhost:8590/course/updateCourse
	public ResponseEntity<Course> updateCourse(@Validated @RequestBody Course course) throws CourseNotFoundException {
		 course = courseService.updateCourse(course);
		return new ResponseEntity<Course>(course, new HttpHeaders(), HttpStatus.OK);

	}

	/*******************************
	 * 
	 * Method :      deleteCourse
	 * @param        courseid
	 * @throws       The method throws CourseNotFoundException based on improperly entered fields.
	 * @return       Response Entity of Object type 
	 * Description : This method remove course.   
	 * @DeleteMapping: annotation maps HTTP DELETE requests onto specific handler methods.
	 *              
	 ******************************/
	
	@DeleteMapping("/deleteCourse/{id}") //http://localhost:8590/course/deleteCourse/123
	public ResponseEntity<Void> deleteCourse(@PathVariable("id") int id) throws CourseNotFoundException, AdmissionAlreadyExistException {
		courseService.removeCourse(id);
		return new ResponseEntity<Void>( new HttpHeaders(), HttpStatus.OK);
	}

}



