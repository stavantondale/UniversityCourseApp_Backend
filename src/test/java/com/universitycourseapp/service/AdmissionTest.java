package com.universitycourseapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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
import com.universitycourseapp.entities.Course;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.AdmissionNotFoundException;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.repository.IAdmissionRepository;
import com.universitycourseapp.repository.ICourseRepository;

@SpringBootTest
class AdmissionTest {

	@MockBean
	IAdmissionRepository admissionRepo;

	@Autowired
	IAdmissionService admissionService;

	@MockBean
	ICourseRepository courseRepo;

	@Autowired
	ICourseService courseService;

	// to test add admission
	@Test
	void addAdmissionTest() throws AdmissionAlreadyExistException, CourseNotFoundException, ApplicantNotFoundException {
		Admission admission = new Admission();
		// admission.setAdmissionId(1); admission.setApplicantId(1);
		admission.setCourseId(1);
		admission.setAdmissionDate(LocalDate.now());
		admission.setStatus(AdmissionStatus.PENDING);
		System.out.println(admission);
		List<Admission> list = new ArrayList<>();
		list.add(admission);
		Course course = new Course();
		course.setCourseName("Physics");
		course.setCourseId(1);
		when(courseRepo.findById(course.getCourseId())).thenReturn(Optional.of(course));
		when(admissionRepo.getAdmissionByApplicantIdAndCourseId(admission.getApplicantId(), admission.getCourseId()))
				.thenReturn(list);
		when(admissionRepo.save(admission)).thenReturn(admission);
		Admission admission1 = admissionService.addAdmission(admission);
		assertEquals(admission, admission1);
	}

	@Test
	void addAdmissionExceptionTest() throws CourseNotFoundException, ApplicantNotFoundException {
		Admission admission = new Admission();
		// admission.setAdmissionId(1);
		admission.setApplicantId(1);
		admission.setCourseId(1);
		admission.setAdmissionDate(LocalDate.now());
		admission.setStatus(AdmissionStatus.PENDING);
		List<Admission> list = new ArrayList<>();
		list.add(admission);
		Course course = new Course();
		course.setCourseName("Physics");
		course.setCourseId(1);
		when(courseRepo.findById(course.getCourseId())).thenReturn(Optional.of(course));
		when(admissionRepo.getAdmissionByApplicantIdAndCourseId(admission.getApplicantId(), admission.getCourseId()))
				.thenReturn(list);
		when(admissionRepo.save(admission)).thenReturn(admission);
		Admission admission1;
		try {
			admission1 = admissionService.addAdmission(admission);
		} catch (AdmissionAlreadyExistException e) {
			// TODO Auto-generated catch block
			assertEquals("Applicant already applied for this course", e.getMessage());
		}
		// assertEquals(admission, admission1);
	}

	// to test update admission
	@Test
	void updateAdmissionTest() throws Exception {
		Admission admission = new Admission(2, 2, 2, LocalDate.now(), AdmissionStatus.PENDING);
		Course course = new Course();
		course.setCourseName("Physics");
		course.setCourseId(2);
		
		admission.setStatus(AdmissionStatus.PENDING);
		System.out.println(admission);
		when(courseRepo.findById(course.getCourseId())).thenReturn(Optional.of(course));
		when(admissionRepo.findById(2)).thenReturn(Optional.of(admission));	
		when(admissionRepo.save(admission)).thenReturn(admission);
		Admission admission1 = admissionService.updateAdmission(admission);
		assertEquals(admission, admission1);
	}

	// To test list of admissions by course id
	@Test
	void showAllAdmissionByCourseIdTest() throws AdmissionNotFoundException, CourseNotFoundException {
		Admission admission1 = new Admission(2, 2, 2, LocalDate.now(), AdmissionStatus.PENDING);

		Admission admission2 = new Admission(2, 2, 2, LocalDate.now(), AdmissionStatus.PENDING);

		List<Admission> admissionList = new ArrayList<>();
		admissionList.add(admission1);
		admissionList.add(admission2);
		Course course = new Course();
		course.setCourseFees(1000.0);
		course.setCourseId(1);

		when(admissionRepo.showAllAdmissionByCourseId(1)).thenReturn(admissionList);
		when(courseRepo.findById(course.getCourseId())).thenReturn(Optional.of(course));
		List<Admission> admissionList1 = new ArrayList<>();
		//admissionList1 = admissionService.showAllAdmissionByCourseId(1);
		assertEquals(admissionList, admissionList1);
		assertIterableEquals(admissionList, admissionList1);

	}

	@Test
	void showAllAdmissionByCourseIdNotFoundTest() throws CourseNotFoundException, ApplicantNotFoundException {
		// Admission admission1 = new Admission(2, 2, 2, LocalDate.now(),
		// AdmissionStatus.PENDING);

		// Admission admission2 = new Admission(2, 2, 2, LocalDate.now(),
		// AdmissionStatus.PENDING);

		List<Admission> admissionList = new ArrayList<>();
		

		//when(courseRepo.findById(course.getCourseId())).thenReturn(Optional.of(course));
		when(admissionRepo.showAllAdmissionByCourseId(1)).thenReturn(admissionList);
		List<AdmissionDTO> admissionList1 = new ArrayList<>();
		try {
			admissionList1 = admissionService.showAllAdmissionByCourseId(1);
		} catch (CourseNotFoundException e) {
			System.out.println(e.getClass());

		}
		// assertEquals(admissionList, admissionList1);
		catch (AdmissionNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			assertEquals("Admission not found with id: " + 1, e.getMessage());
		}

	}

	// To test two lists of different course ids
	@Test
	void showAllAdmissionByCourseIdTwoListsTest() throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException {
		Admission admission1 = new Admission(2, 2, 2, LocalDate.now(), AdmissionStatus.PENDING);

		Admission admission2 = new Admission(3, 2, 2, LocalDate.now(), AdmissionStatus.PENDING);

		List<Admission> admissionList1 = new ArrayList<>();
		admissionList1.add(admission1);

		List<AdmissionDTO> admissionList2 = new ArrayList<>();
		Course course = new Course();
		course.setCourseFees(1000.0);
		course.setCourseId(1);

		when(courseRepo.findById(course.getCourseId())).thenReturn(Optional.of(course));
		when(courseRepo.findById(2)).thenReturn(Optional.of(course));
		when(admissionRepo.showAllAdmissionByCourseId(1)).thenReturn(admissionList1);
		admissionList2 = admissionService.showAllAdmissionByCourseId(1);
		assertEquals(admissionList2, admissionList1);
		assertIterableEquals(admissionList1, admissionList2);

		List<Admission> admissionList3 = new ArrayList<>();
		List<AdmissionDTO> admissionList4 = new ArrayList<>();
		admissionList3.add(admission2);
		when(admissionRepo.showAllAdmissionByCourseId(2)).thenReturn(admissionList3);
		admissionList4 = admissionService.showAllAdmissionByCourseId(2);

		assertIterableEquals(admissionList3, admissionList4);
	}

	// to test list of admissions by specific date
	@Test
	void showAllAdmissionByDateTest() throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException {
		Admission admission1 = new Admission(1, 1, 1, LocalDate.now(), AdmissionStatus.PENDING);
		Admission admission2 = new Admission(2, 2, 1, LocalDate.now(), AdmissionStatus.PENDING);

		List<Admission> admissionList = new ArrayList<>();
		admissionList.add(admission1);
		admissionList.add(admission2);

		when(admissionRepo.showAllAdmissionByDate(LocalDate.now())).thenReturn(admissionList);

		List<AdmissionDTO> admissionList1 = new ArrayList<>();
		admissionList1 = admissionService.showAllAdmissionByDate(LocalDate.now());

		assertEquals(admissionList, admissionList1);
		assertIterableEquals(admissionList, admissionList1);
	}

	@Test
	void showAllAdmissionByDateTwoListsTest() throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException {
		Admission admission1 = new Admission(1, 1, 1, LocalDate.now(), AdmissionStatus.PENDING);
		Admission admission2 = new Admission(2, 2, 1, LocalDate.of(2021, 4, 4), AdmissionStatus.PENDING);
		Admission admission3 = new Admission(2, 2, 1, LocalDate.now(), AdmissionStatus.PENDING);

		List<Admission> admissionList1 = new ArrayList<>();
		admissionList1.add(admission1);
		admissionList1.add(admission3);

		List<Admission> admissionList2 = new ArrayList<>();
		admissionList2.add(admission2);
		when(admissionRepo.showAllAdmissionByDate(LocalDate.now())).thenReturn(admissionList1);
		when(admissionRepo.showAllAdmissionByDate(LocalDate.of(2021, 4, 4))).thenReturn(admissionList2);
		List<AdmissionDTO> admissionList3 = new ArrayList<>();
		List<AdmissionDTO> admissionList4 = new ArrayList<>();
		admissionList3 = admissionService.showAllAdmissionByDate(LocalDate.now());
		admissionList4 = admissionService.showAllAdmissionByDate(LocalDate.of(2021, 4, 4));

		assertEquals(admissionList3, admissionList1);
		assertIterableEquals(admissionList4, admissionList2);

	}

	@Test
	void showAllAdmissionByDateNotFoundTest() throws CourseNotFoundException, ApplicantNotFoundException {
		// Admission admission1 = new Admission(2, 2, 2, LocalDate.now(),
		// AdmissionStatus.PENDING);

		// Admission admission2 = new Admission(2, 2, 2, LocalDate.now(),
		// AdmissionStatus.PENDING);
		Course course = new Course();
		course.setCourseFees(1000.0);
		course.setCourseId(1);
		List<Admission> admissionList = new ArrayList<>();
		when(admissionRepo.showAllAdmissionByDate(LocalDate.now())).thenReturn(admissionList);
		when(courseRepo.findById(course.getCourseId())).thenReturn(Optional.of(course));
		List<AdmissionDTO> admissionList1 = new ArrayList<>();
		try {
			admissionList1 = admissionService.showAllAdmissionByCourseId(1);
		} catch (CourseNotFoundException e) {
			System.out.println(e.getClass());
			
		} catch (AdmissionNotFoundException e) {
			// TODO Auto-generated catch block
			assertEquals("Admission not found with id: " + 1, e.getMessage());
		}
		assertIterableEquals(admissionList, admissionList1);

	}

	@Test
	void calculateTotalCostTest() throws Exception {
		Admission admission1 = new Admission(1, 2, 1, LocalDate.now(), AdmissionStatus.PENDING);
		Admission admission2 = new Admission(2, 2, 1, LocalDate.of(2021, 4, 4), AdmissionStatus.PENDING);
		Course course = new Course();
		course.setCourseFees(1000.0);
		course.setCourseId(2);
		List<Admission> admissionList1 = new ArrayList<>();
		admissionList1.add(admission1);
		admissionList1.add(admission2);
		when(courseRepo.findById(2)).thenReturn(Optional.of(course));
		when(admissionRepo.findByCourseIdAndStatus(admission1.getCourseId(), AdmissionStatus.CONFIRMED)).thenReturn(admissionList1);
		double totalCost = admissionService.calculateTotalCost(2);
		assertEquals(course.getCourseFees() * admissionList1.size(), totalCost);
	}
}
