package com.universitycourseapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.universitycourseapp.dto.AdmissionDTO;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.entities.Course;
import com.universitycourseapp.entities.Role;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.repository.IApplicantRepository;

@Service("iApplicantService")
public class ApplicantServiceImpl implements IApplicantService{
	@Autowired
	private IApplicantRepository repository;
	@Autowired
	private ICourseService courseService;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	
	/*******************************
	 * Method : addApplicant
	 * 
	 * @param Applicant
	 * @throws The method throws CourseNotFoundException
	 * @return applicant 
	 * Description : This method creates adds a new applicant
	 *         
	 * 
	 ******************************/
	
	@Override
	public Applicant addApplicant(Applicant applicant) throws CourseNotFoundException {
				 
		courseService.viewCourse(applicant.getAdmission().getCourseId());
		applicant.getUser().setPassword(bcryptEncoder.encode(applicant.getUser().getPassword()));
		applicant.getUser().setRole(Role.APPLICANT);
		applicant = repository.save(applicant);
		applicant.getAdmission().setApplicantId(applicant.getApplicantId());
		applicant = repository.save(applicant);
		
		return applicant;
		
	}
	
	/*******************************
	 * Method : updateApplicant
	 * 
	 * @param Applicant
	 * @throws The method throws ApplicantNotFoundException
	 * @return applicant
	 * Description : This method updates the Applicant details
	 * 
	 ******************************/
	
	
	@Override
	public Applicant updateApplicant(Applicant applicant) throws ApplicantNotFoundException {
		Optional<Applicant> student = repository.findById(applicant.getApplicantId());
		System.out.println("student fetched from db: "+student);
		System.out.println("Applicant: "+applicant);
		if(student.isEmpty()) {
			 throw new ApplicantNotFoundException("Applicant not found with Id no. : " + applicant.getApplicantId());
		}
		if (applicant.getUser() != null) {
			if (applicant.getUser().getRole() == null)
				applicant.getUser().setRole((student.get().getUser().getRole()));
			if (applicant.getUser().getPassword() != null)
				applicant.getUser().setPassword(bcryptEncoder.encode(applicant.getUser().getPassword()));
		} else {
			applicant.setUser(student.get().getUser());
		}
		
		if(applicant.getAdmission() == null) {
			applicant.setAdmission(student.get().getAdmission());
		}
		System.out.println("student fetched from db after: "+student);
		System.out.println("Applicant after: "+applicant);
		return repository.save(applicant);
		
	}
	
	/*******************************
	 * Method : deleteApplicant
	 * 
	 * @param Applicant
	 * @throws The method throws ApplicantNotFoundException
	 * @return applicant
	 * Description : This method deletes the applicant.
	 *
	 ******************************/
	
	
	@Override
	public Applicant deleteApplicant(Applicant applicant) throws ApplicantNotFoundException {
		Optional<Applicant> student = repository.findById(applicant.getApplicantId());
		if(student.isPresent()) {
			repository.delete(applicant);
			return applicant;
		}else {
			 throw new ApplicantNotFoundException("Applicant not found with Id no. : " + applicant.getApplicantId());
			 
		}
		
	}
	
	/*******************************
	 * Method : viewApplicant
	 * 
	 * @param applicantId
	 * @throws The method throws ApplicantNotFoundException
	 * @return applicant 
	 * Description : This method views the applicant by applicantId
	 * 
	 ******************************/

	@Override
	public Applicant viewApplicant(int applicantId) throws ApplicantNotFoundException  {
		Optional<Applicant> student =repository.findById(applicantId);
		if(student.isPresent()) {
			return student.get();
		}else {
			 throw new ApplicantNotFoundException("Applicant not found with Id: " + applicantId);
		}
		
	}

	/*******************************
	 * Method : viewAllApplicantsByStatus
	 * 
	 * @param AdmissionStatus
	 * @throws The method throws ApplicantNotFoundException
	 * @return List of applicants
	 * Description : This method shows applicant with the status
	 * @throws CourseNotFoundException 
	 * 
	 ******************************/
	
	@Override
	public List<AdmissionDTO> viewAllApplicantsByStatus(AdmissionStatus status) throws ApplicantNotFoundException, CourseNotFoundException {
		List<Applicant> listOfStudents = repository.viewAllApplicantsByStatus(status);
		if(listOfStudents.isEmpty()) {
			throw new ApplicantNotFoundException("No Applicant Found");
		}else {
			List<AdmissionDTO> listOfApplicants = new ArrayList<>();
			for(Applicant applicant:listOfStudents) {
				listOfApplicants.add(getApplicantDetails(applicant));
			}
			return listOfApplicants;
		}
		
		
	}
	
	/*******************************
	 * Method : viewAllApplicantsByCourseId
	 * 
	 * @param CourseId
	 * @throws The method throws different exceptions based on improperly entered
	 *             fields
	 * @return List of applicants
	 * Description : This method shows the applicant with the courseId.
	 * 
	 ******************************/

	@Override
	public List<Applicant> viewAllApplicantsByCourse(int courseId) throws CourseNotFoundException, ApplicantNotFoundException {
		List<Applicant> listOfApplicantsInCourse = repository.viewAllApplicantsByCourse(courseId);
		if(listOfApplicantsInCourse.isEmpty()) {
			throw new ApplicantNotFoundException("No Applicant Found");
		}
		return listOfApplicantsInCourse;
	}

	private AdmissionDTO getApplicantDetails(Applicant applicant) throws CourseNotFoundException {
		AdmissionDTO applicantDetails= new AdmissionDTO();
		Course course = courseService.viewCourse(applicant.getAdmission().getCourseId());
		applicantDetails.setAdmissionId(applicant.getAdmission().getAdmissionId());
		applicantDetails.setApplicant(applicant);
		applicantDetails.setCourseName(course.getCourseName());
	
		return applicantDetails;
		
	}

	@Override
	public Applicant updateStatus(Applicant applicant) {
		repository.save(applicant);
		return applicant;
	}



}
