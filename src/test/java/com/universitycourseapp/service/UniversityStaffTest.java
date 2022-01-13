package com.universitycourseapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.universitycourseapp.entities.Course;
import com.universitycourseapp.entities.Role;
import com.universitycourseapp.entities.UniversityStaffMember;
import com.universitycourseapp.entities.User;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.CourseIsAlreadyExistException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.exception.UniversityStaffNotFoundException;
import com.universitycourseapp.repository.ICourseRepository;
import com.universitycourseapp.repository.IUniversityStaffRepository;

@SpringBootTest
 class UniversityStaffTest {

	@Autowired
	IUniversityStaffService service;
	@MockBean
	IUniversityStaffRepository dao;

	@MockBean
	ICourseRepository repo;

	@Test
	@DisplayName("AddStaff")
	 void testAddStaff() {
		User user = new User(1, "Engi_123", "Password", Role.UNIVERSITYSTAFFMEMBER);
		UniversityStaffMember staff = new UniversityStaffMember(1, user);
		when(dao.save(staff)).thenReturn(staff);
		UniversityStaffMember staff1 = service.addStaff(staff);
		assertEquals("Engi_123", staff1.getUser().getUsername());
	}

	@Test
	@DisplayName("UpdateStaff")
	 void testUpdateStaff() throws UniversityStaffNotFoundException {
		User user = new User(1, "Engi_123", "Password", Role.UNIVERSITYSTAFFMEMBER);
		UniversityStaffMember staff = new UniversityStaffMember(1, user);
		when(dao.save(staff)).thenReturn(staff);
		when(dao.findById(staff.getStaffId())).thenReturn(Optional.of(staff));
		UniversityStaffMember staff1 = service.updateStaff(staff);
		System.out.println(staff1);
		assertEquals("Engi_123", staff.getUser().getUsername());
	}

	@Test
	@DisplayName("RemoveStaff")
	 void testRemoveStaff() throws UniversityStaffNotFoundException {
		User user = new User(1, "Engi_123", "Password", Role.UNIVERSITYSTAFFMEMBER);
		UniversityStaffMember staff = new UniversityStaffMember(1, user);
		when(dao.findById(staff.getStaffId())).thenReturn(Optional.of(staff));
		service.removeStaff(1);
		verify(dao, times(1)).deleteById(staff.getStaffId());
	}

	@Test
	@DisplayName("ViewStaff")
	 void testViewStaff() throws UniversityStaffNotFoundException {
		User user = new User(1, "Engi_123", "Password", Role.UNIVERSITYSTAFFMEMBER);
		UniversityStaffMember staff = new UniversityStaffMember(1, user);
		when(dao.findById(1)).thenReturn(Optional.of(staff));
		assertEquals(1, service.viewStaff(1).getStaffId());
	}

	@Test
	@DisplayName("staffNotFoundByID")
	 void testNoStaffFoundById() {
		UniversityStaffMember staff = new UniversityStaffMember();
		UniversityStaffMember staff1 = new UniversityStaffMember();
		when(dao.findById(staff.getStaffId())).thenReturn(Optional.of(staff));
		try {
			staff1=service.viewStaff(staff.getStaffId());
		} catch (UniversityStaffNotFoundException e) {
			// TODO Auto-generated catch block
			assertEquals("Staff NotFound by id",e.getMessage() );
			e.printStackTrace();
		}
		assertEquals(staff, staff1);
	}
		
	

	@Test
	@DisplayName("ViewAllStaff")
	public void testViewAllStaff() throws UniversityStaffNotFoundException {
		User user = new User(1, "Engi_123", "Password", Role.UNIVERSITYSTAFFMEMBER);
		UniversityStaffMember staff = new UniversityStaffMember(1, user);
		User user1 = new User(2, "tony_12", "Password", Role.UNIVERSITYSTAFFMEMBER);
		UniversityStaffMember staff1 = new UniversityStaffMember(1, user);
		User user2 = new User(3, "john_1", "Password", Role.UNIVERSITYSTAFFMEMBER);
		UniversityStaffMember staff2 = new UniversityStaffMember(1, user);
		

		List<UniversityStaffMember> staffList = new ArrayList<>();
		staffList.add(staff1);
		staffList.add(staff2);
		staffList.add(staff);
		// Mockito.when(query.list()).thenReturn(productList);

		when(dao.findAll()).thenReturn(staffList);
		List<UniversityStaffMember> staffList1 = new ArrayList<>();
		staffList1 = service.viewAllStaffs();
		assertEquals(staffList.size(), staffList1.size());
		assertIterableEquals(staffList, staffList1);
	}

	@Test
	public void testNoStaffFound() {
		List<UniversityStaffMember> staffList = new ArrayList<>();
		List<UniversityStaffMember> staffList1 = new ArrayList<>();
		
		when(dao.findAll()).thenReturn(staffList1);
		try {
			staffList1=service.viewAllStaffs();
		} catch (UniversityStaffNotFoundException e) {
			// TODO Auto-generated catch block
			assertEquals("Staff not found",e.getMessage() );
			e.printStackTrace();
		}
		assertEquals(staffList, staffList1);
	}
	
	@Test
	@DisplayName("AddCourse")
	public void testAddCourse() throws CourseNotFoundException, CourseIsAlreadyExistException {
		Course course = new Course(1, "Civil engineering", "4 years", LocalDate.of(2021, 1, 12),
				LocalDate.of(2024, 1, 12), 400000.0);
		when(repo.save(course)).thenReturn(course);
		Course course1 = service.addCourse(course);
		assertEquals("Civil engineering", course1.getCourseName());
	}

	@Test
	@DisplayName("UpdateCourse")
	public void testUpdateCourse() throws CourseNotFoundException {
		Course course = new Course(1, "Electrical engineering", "4 years", LocalDate.of(2021, 1, 12),
				LocalDate.of(2024, 1, 12), 400000.0);
		when(repo.findById(1)).thenReturn(Optional.of(course));
		when(repo.save(course)).thenReturn(course);
		Course course1 = service.updateCourse(course);
		assertEquals("Electrical engineering", course1.getCourseName());
	}

	@Test
	@DisplayName("RemoveCourse")
	public void testRemoveCourse() throws CourseNotFoundException, AdmissionAlreadyExistException {
		Course course = new Course(1, "Electrical engineering", "4 years", LocalDate.of(2021, 1, 12),
				LocalDate.of(2024, 1, 12), 400000.0);
		when(repo.findById(1)).thenReturn(Optional.of(course));
		service.removeCourse(1);
		verify(repo, times(1)).delete(course);
	}
}
