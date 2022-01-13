package com.universitycourseapp.service;

import java.util.List;

import com.universitycourseapp.entities.Course;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.CourseIsAlreadyExistException;
import com.universitycourseapp.exception.CourseNotFoundException;

public interface ICourseService {

	public Course addCourse(Course course) throws CourseIsAlreadyExistException ;

	public Course removeCourse(int courseId) throws CourseNotFoundException,AdmissionAlreadyExistException;

	public Course updateCourse(Course course) throws CourseNotFoundException;

	public Course viewCourse(int courseid) throws CourseNotFoundException;
	
	public Double getCourseFee(int courseid) throws CourseNotFoundException;

	public List<Course> viewAllCourses() throws CourseNotFoundException;
	
	public int getCountOfCourses();

}
