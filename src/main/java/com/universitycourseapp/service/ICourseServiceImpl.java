package com.universitycourseapp.service;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.Course;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.CourseIsAlreadyExistException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.repository.IAdmissionRepository;
import com.universitycourseapp.repository.ICourseRepository;

@Service
public class ICourseServiceImpl implements ICourseService {
	String message = "Course not found with given id";

	@Autowired
	private ICourseRepository courseRepository;
	@Autowired
	IAdmissionRepository admissionRepository;
	
	/**
	 * Method :      addCourse
	 * @param        Course.
	 * @return       Course object.
	 * @throws       CourseNotFoundException.
	 * Description : This method create new entry for course .
	 * 
	 */

	@Override
	public Course addCourse(Course course) throws CourseIsAlreadyExistException {
		List<Course> courses = courseRepository.getCourseByCourseNameAndCourseDuration(course.getCourseName(), course.getCourseDuration());
		if(courses.isEmpty()) {
			return courseRepository.save(course);
		}
		throw new CourseIsAlreadyExistException("This course is already exist.");

	}
	
	/**
	 * Method :      removeCourse 
	 * @param        courseId.
	 * @return       Course object.
	 * @throws       CourseNotFoundException.
	 * Description : This method remove course .
	 * 
	 */

	@Override
	public Course removeCourse(int courseId) throws CourseNotFoundException, AdmissionAlreadyExistException {
		List<Admission> admissions = admissionRepository.showAllAdmissionByCourseId(courseId);
		Optional<Course> result = courseRepository.findById(courseId);
//		if (admissions.isEmpty()){
//			
//			if (result.isEmpty()) {
//				throw new CourseNotFoundException(message);
//			}
//			courseRepository.delete(result.get());		
//		}
//		else {
//			throw new AdmissionAlreadyExistException("Applicant already applied for this course");
//		}
		if(result.isEmpty()) 
				throw new CourseNotFoundException(message);	
		if(admissions.isEmpty())
			courseRepository.delete(result.get());
		else
			throw new AdmissionAlreadyExistException("Applicant already applied for this course");
		return result.get();

	}

	/**
	 * Method :      updateCourse
	 * @param :      Course.
	 * @return :      Course object.
	 * @throws :     CourseNotFoundException.
	 * Description : This method update record of course .
	 * 
	 */
	@Override
	public Course updateCourse(Course course) throws CourseNotFoundException {
		Optional<Course> result = courseRepository.findById(course.getCourseId());
		if(result.isEmpty()) {
			throw new CourseNotFoundException(message);
		}
		return  courseRepository.save(course);
		
		
	}


	/**
	 * Method :      viewCourse
	 * @param :       courseid
	 * @return :     Course object.
	 * @throws :     CourseNotFoundException.
	 * Description : This method view record of staffMember.
	 * 
	 */
	@Override
	public Course viewCourse(int courseid) throws CourseNotFoundException {
		Optional<Course> result = courseRepository.findById(courseid);
		if (result.isEmpty()) {
			throw new CourseNotFoundException(message);
		}	
		return result.get();
	}

	/**
	 * Method :      viewAllCourses
	 * @param        .
	 * @return       List of Course object.
	 * @throws       CourseNotFoundException.
	 * Description : This method show all courses.
	 * 
	 */
	@Override
	public List<Course> viewAllCourses() throws CourseNotFoundException {
		List<Course> courseList = courseRepository.findAll();
		if(courseList.isEmpty()) {
			throw new CourseNotFoundException("Courses not found");
		}
		return courseList;
	}
	
	/**
	 * Method :      getCourseFee
	 * @param :       courseid
	 * @return :     Double value
	 * @throws :     CourseNotFoundException.
	 * Description : This method view course fee.
	 * 
	 */

	@Override
	public Double getCourseFee(int courseid) throws CourseNotFoundException {
		Optional<Course> result = courseRepository.findById(courseid);
		if (result.isEmpty()) {
			throw new CourseNotFoundException(message);
		}
		return result.get().getCourseFees();
	}
	
	
	@Override
	public int getCountOfCourses() {
		return courseRepository.findAll().size();
	}

}
