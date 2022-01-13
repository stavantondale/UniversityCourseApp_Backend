package com.universitycourseapp.service;

import java.util.List;

import com.universitycourseapp.dto.AdmissionDTO;
import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CourseNotFoundException;



public interface IApplicantService {
	public abstract Applicant addApplicant(Applicant applicant) throws CourseNotFoundException;

	public abstract Applicant updateApplicant(Applicant applicant) throws ApplicantNotFoundException;

	public abstract Applicant deleteApplicant(Applicant applicant) throws ApplicantNotFoundException;

	public abstract Applicant viewApplicant(int applicantId) throws ApplicantNotFoundException;

	public abstract List<AdmissionDTO> viewAllApplicantsByStatus(AdmissionStatus status) throws ApplicantNotFoundException, CourseNotFoundException;

	
	public abstract List<Applicant> viewAllApplicantsByCourse(int courseId) throws CourseNotFoundException, ApplicantNotFoundException;
	
	public Applicant updateStatus(Applicant applicant);
}
