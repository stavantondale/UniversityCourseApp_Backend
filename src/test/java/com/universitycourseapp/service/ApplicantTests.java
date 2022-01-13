package com.universitycourseapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.universitycourseapp.dto.AdmissionDTO;
import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.entities.Course;
import com.universitycourseapp.entities.Role;
import com.universitycourseapp.entities.User;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.repository.IApplicantRepository;
import com.universitycourseapp.repository.ICourseRepository;

@SpringBootTest
 class ApplicantTests {
	
	@Autowired
	IApplicantService service;
	@MockBean
	IApplicantRepository repository; 
	@MockBean
	ICourseRepository courseRepo;
	
	@Test
	 void testAddApplicant() throws CourseNotFoundException{
		Admission admission = new Admission(1, 5,1,LocalDate.of(2021,8,07), AdmissionStatus.PENDING);
		User user = new User(1,"naval1","naval123",Role.APPLICANT);
		Applicant applicant = new Applicant(0, "naval3", "98765432", "ME", 70 , user, admission, AdmissionStatus.PENDING);
		when(repository.save(applicant)).thenReturn(applicant);
		Course course = new Course();
//		course.setCourseFees(1000.0);
		course.setCourseId(5);
		when(courseRepo.findById(course.getCourseId())).thenReturn(Optional.of(course));
		Applicant applicant1 = service.addApplicant(applicant);
		assertEquals(applicant,applicant1);
	}
	
	@Test
	 void testUpdateApplicant() throws ApplicantNotFoundException{
		Admission admission = new Admission(1,5,1,LocalDate.of(2021,8,07), AdmissionStatus.PENDING);
		User user = new User(1,"naval1","naval123",Role.APPLICANT);
		Applicant applicant = new Applicant(0, "naval3", "98765432", "ME", 70 , user, admission, AdmissionStatus.PENDING);
		when(repository.save(applicant)).thenReturn(applicant);
		when(repository.findById(applicant.getApplicantId())).thenReturn(Optional.of(applicant));
		System.out.println(applicant);
		Applicant applicant1 = service.updateApplicant(applicant);
		System.out.println(applicant);
		assertEquals(applicant,applicant1);
	}
	
	@Test
	 void testDeleteApplicant() throws ApplicantNotFoundException{
		Admission admission = new Admission(1, 5,1,LocalDate.of(2021,8,07), AdmissionStatus.PENDING);
		User user = new User(1,"naval1","naval123",Role.APPLICANT);
		Applicant applicant = new Applicant(0, "naval3", "98765432", "ME", 70 , user, admission, AdmissionStatus.PENDING);
		when(repository.save(applicant)).thenReturn(applicant);
		when(repository.findById(applicant.getApplicantId())).thenReturn(Optional.of(applicant));
		Applicant applicant1 = service.deleteApplicant(applicant);
		assertEquals(applicant,applicant1);
	}
	
	
	@Test
	 void testViewApplicant() throws ApplicantNotFoundException{
		Admission admission = new Admission(1,5,1,LocalDate.of(2021,8,07), AdmissionStatus.PENDING);
		User user = new User(1,"naval1","naval123",Role.APPLICANT);
		Applicant applicant = new Applicant(0, "naval3", "98765432", "ME", 70 , user, admission, AdmissionStatus.PENDING);
		when(repository.save(applicant)).thenReturn(applicant);
		when(repository.findById(applicant.getApplicantId())).thenReturn(Optional.of(applicant));
		System.out.println(applicant);
		Applicant applicant1 = service.viewApplicant(applicant.getApplicantId());
		System.out.println(applicant);
		assertEquals(applicant,applicant1);
		
	}
	
	@Test
	 void viewApplicantNotFound() {
		
		Admission admission = new Admission(1,5,1,LocalDate.of(2021,8,07), AdmissionStatus.PENDING);
		User user = new User(1,"naval1","naval123",Role.APPLICANT);
		Applicant applicant = new Applicant(0, "naval3", "98765432", "ME", 70 , user, admission, AdmissionStatus.PENDING);
		when(repository.save(applicant)).thenReturn(applicant);
		when(repository.findById(applicant.getApplicantId())).thenReturn(Optional.of(applicant));
			try {
				applicant = service.viewApplicant(applicant.getApplicantId());
			} catch (ApplicantNotFoundException e) {
				System.out.println(e.getClass());
				assertEquals("Applicant not found with Id: " + applicant.getApplicantId() ,e.getMessage());
			}
			
		}
	
	@Test
	 void testViewAllApplicant() throws ApplicantNotFoundException, CourseNotFoundException{
		Admission admission1 = new Admission(1,5,1,LocalDate.of(2021,8,07), AdmissionStatus.PENDING);
		Admission admission2 = new Admission(2,5,2,LocalDate.of(2021,8,07), AdmissionStatus.PENDING);
		User user = new User(1,"naval1","naval123",Role.APPLICANT);
		Applicant applicant1 = new Applicant(0, "naval3", "98765432", "ME", 70 , user, admission1, AdmissionStatus.PENDING);
		User user1 = new User(1,"naval2","naval321",Role.APPLICANT);
		Applicant applicant2 = new Applicant(0, "naval3", "98765432", "ME", 70 , user1, admission2, AdmissionStatus.PENDING);
		List<Applicant> applicantList = new ArrayList<>();
		applicantList.add(applicant1);
		applicantList.add(applicant2);
		when(repository.viewAllApplicantsByStatus(AdmissionStatus.PENDING)).thenReturn(applicantList);
		List<AdmissionDTO> applicantList1 = new ArrayList<>();
		applicantList1 = service.viewAllApplicantsByStatus(AdmissionStatus.PENDING);
		assertEquals(applicantList,applicantList1);
	}
	
	

	

	@Test
	 void viewAllApplicantsByStatusNotFoundTest() throws CourseNotFoundException {
		
			List<Applicant> applicantList = new ArrayList<>();
			when(repository.viewAllApplicantsByStatus(AdmissionStatus.PENDING)).thenReturn(applicantList);
			List<AdmissionDTO> applicantList1 = new ArrayList<>();
			try {
				applicantList1 = service.viewAllApplicantsByStatus(AdmissionStatus.PENDING);
			} catch (ApplicantNotFoundException e) {
				System.out.println(e.getClass());
				assertEquals("No Applicant Found",e.getMessage());
			}
			

		}

	

	
}
