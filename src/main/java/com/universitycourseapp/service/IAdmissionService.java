package com.universitycourseapp.service;

import java.time.LocalDate;
import java.util.List;

import com.universitycourseapp.dto.AdmissionDTO;
import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.AdmissionNotFoundException;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CourseNotFoundException;

public interface IAdmissionService {
	Admission addAdmission(Admission admission) throws AdmissionAlreadyExistException, CourseNotFoundException, ApplicantNotFoundException;
	Admission updateAdmission(Admission admission) throws Exception;
	Admission confirmAdmission(int id) throws AdmissionNotFoundException, ApplicantNotFoundException;
	Admission cancelAdmission (int id) throws AdmissionNotFoundException, ApplicantNotFoundException;
	List<AdmissionDTO> showAllAdmissionByCourseId(int id) throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException;
	List<AdmissionDTO> showAllAdmissionByDate(LocalDate date) throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException;
	AdmissionDTO showAllAdmissionsByApplicant(int applicantId) throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException;
	double calculateTotalCost(int courseId) throws CourseNotFoundException, AdmissionNotFoundException;
	List<AdmissionDTO> showAllAdmissions()throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException;
	int getCountOfAdmissions();
}
