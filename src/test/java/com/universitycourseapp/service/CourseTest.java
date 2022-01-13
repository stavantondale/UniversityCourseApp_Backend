package com.universitycourseapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.universitycourseapp.entities.Course;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.CourseIsAlreadyExistException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.repository.ICourseRepository;

@SpringBootTest
class CourseTest {

	@Autowired
	ICourseService service;
	@MockBean
	ICourseRepository repository;

	@Test
	@DisplayName("Add Course - Successful")
	void testAddCourse() throws CourseIsAlreadyExistException {
		Course course = new Course(9, "Physics", "5 Months", LocalDate.of(2021, 8, 07), LocalDate.of(2021, 9, 07),
				1000.0);
		when(repository.save(course)).thenReturn(course);
		Course course1 = service.addCourse(course);
		assertEquals(course, course1);
	}
	
	@Test
	@DisplayName("Add Course - Course Is Already Exist")
	void testAddCourseExist() {
		Course course = new Course();
		course.setCourseId(1);
		course.setCourseName("Maths");
		course.setCourseDuration("1");
		course.setCourseStartDate(LocalDate.of(2021, 5, 14));
		course.setCourseEndDate(LocalDate.of(2021, 6, 14));
		course.setCourseFees(1500.0);
		List<Course> list = new ArrayList<>();
		list.add(course);
		when(repository.getCourseByCourseNameAndCourseDuration(course.getCourseName(), course.getCourseDuration())).thenReturn(list);
		when(repository.save(course)).thenReturn(course);
		Course course1;
		try {
			course1 = service.addCourse(course);
		} catch (CourseIsAlreadyExistException e) {
			assertEquals("This course is already exist.", e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Update Course - Successful")
	void testUpdateCourse() throws CourseNotFoundException {
		Course course = new Course(1, "Electrical engineering", "4 years", LocalDate.of(2021, 1, 12),
				LocalDate.of(2024, 1, 12), 400000.0);
		when(repository.save(course)).thenReturn(course);
		when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
		Course course1 = service.updateCourse(course);
		assertEquals(course, course1);
	}

	@Test
	@DisplayName("Update Course - Course Not Found")
	void testNotUpdated() {
		Course course = new Course();
		Course course1 = new Course();
		when(repository.save(course)).thenReturn(course);
		when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
		try {
			course1 = service.updateCourse(course);
		} catch (CourseNotFoundException e) {

			assertEquals("Course not found with given id", e.getMessage());
			e.printStackTrace();
		}
		assertEquals(course, course1);

	}

	@Test
	@DisplayName("Remove Course - Successful")
	void testRemoveCourse() throws CourseNotFoundException, AdmissionAlreadyExistException {
		Course course = new Course(1, "Physics", "5 Months", LocalDate.of(2021, 8, 07), LocalDate.of(2021, 9, 07),
				1000.0);

		when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
		service.removeCourse(course.getCourseId());
		verify(repository, times(1)).delete(course);

	}

	@Test
	@DisplayName("Remove Course -  Course Not Found")
	void testNotRemoved() throws AdmissionAlreadyExistException {
		Course course = new Course();
		when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
		try {
			service.removeCourse(course.getCourseId());
		} catch (CourseNotFoundException e) {

			assertEquals("Course not found with given id", e.getMessage());
			e.printStackTrace();
		}
		verify(repository, times(1)).delete(course);
	}

	@Test
	@DisplayName("View Course - Successful")
	void testViewCourseById() throws CourseNotFoundException {
		Course course = new Course(1, "Physics", "5 Months", LocalDate.of(2021, 8, 07), LocalDate.of(2021, 9, 07),
				1000.0);
		when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
		Course course2 = service.viewCourse(course.getCourseId());
		assertEquals(course, course2);

	}

	@Test
	@DisplayName("View Course - Course Not Found")
	void testNoCourseFoundById() {
		Course course = new Course();
		Course course1 = new Course();
		when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
		try {
			course1 = service.viewCourse(course.getCourseId());
		} catch (CourseNotFoundException e) {

			assertEquals("Course not found with given id", e.getMessage());
			e.printStackTrace();
		}
		assertEquals(course, course1);
	}

	@Test
	@DisplayName("View All Courses - Successful")
	void testViewAllCourses() throws CourseNotFoundException {
		List<Course> courseList = new ArrayList<Course>();
		List<Course> courseList1 = new ArrayList<Course>();
		Course course1 = new Course(1, "Maths", "4 Months", LocalDate.of(2021, 5, 05), LocalDate.of(2021, 6, 06),
				5000.0);
		Course course2 = new Course(2, "Chemistry", "5 Months", LocalDate.of(2022, 4, 04), LocalDate.of(2022, 5, 05),
				1000.0);
		courseList.add(course1);
		courseList.add(course2);
		Mockito.when(repository.findAll()).thenReturn(courseList);
		courseList1 = service.viewAllCourses();
		assertEquals(2, courseList1.size());
	}

	@Test
	@DisplayName("View All Courses - No Courses Found")
	void testNoCoursesFound() {
		List<Course> list = new ArrayList<>();
		List<Course> list1 = new ArrayList<>();
		Mockito.when(repository.findAll()).thenReturn(list);
		try {
			list1 = service.viewAllCourses();
		} catch (CourseNotFoundException e) {
			assertEquals("Courses not found", e.getMessage());
			e.printStackTrace();
		}
		assertEquals(list, list1);
	}

	@Test
	@DisplayName("Get Course Fee - Successful")
	void testGetCourseFee() throws CourseNotFoundException {
		Course course = new Course(1, "Physics", "5 Months", LocalDate.of(2021, 8, 07), LocalDate.of(2021, 9, 07),
				1000.0);
		when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
		Double courseFee = service.getCourseFee(course.getCourseId());
		assertEquals(course.getCourseFees(), courseFee);
	}

	@Test
	@DisplayName("Get Course Fee - No Course Found")
	void testCourseFeeNotFound() {
		Course course = new Course();
		Double courseFee = 0.0;
		when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
		try {
			courseFee = service.getCourseFee(course.getCourseId());
		} catch (CourseNotFoundException e) {
			assertEquals("Course not found with given id", e.getMessage());
			e.printStackTrace();
		}
		assertEquals(course.getCourseFees(), courseFee);
	}
}
