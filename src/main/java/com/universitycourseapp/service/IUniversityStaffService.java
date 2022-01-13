package com.universitycourseapp.service;

import java.util.List;
import java.util.Map;

import com.universitycourseapp.entities.Course;
import com.universitycourseapp.entities.UniversityStaffMember;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.CourseIsAlreadyExistException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.exception.UniversityStaffNotFoundException;

public interface IUniversityStaffService {
	
	
	public UniversityStaffMember addStaff(UniversityStaffMember user);

	public UniversityStaffMember updateStaff(UniversityStaffMember user) throws UniversityStaffNotFoundException;

	public UniversityStaffMember viewStaff(int staffid) throws UniversityStaffNotFoundException;

	public void removeStaff(int staffid) throws UniversityStaffNotFoundException;

	public List<UniversityStaffMember> viewAllStaffs() throws UniversityStaffNotFoundException;

	public Course addCourse(Course course) throws CourseNotFoundException, CourseIsAlreadyExistException;

	public Course removeCourse(int courseId) throws CourseNotFoundException, AdmissionAlreadyExistException;

	public Course updateCourse(Course course) throws CourseNotFoundException;
	
	public int getCountOfStaff();
	
	public Map<String, Integer> getStats();
	

}
