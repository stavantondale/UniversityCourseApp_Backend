package com.universitycourseapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.universitycourseapp.entities.Course;
import com.universitycourseapp.entities.Role;
import com.universitycourseapp.entities.UniversityStaffMember;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.CourseIsAlreadyExistException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.exception.UniversityStaffNotFoundException;
import com.universitycourseapp.repository.IUniversityStaffRepository;

@Service
public class UniversityStaffServiceImpl implements IUniversityStaffService {

	@Autowired
	private IUniversityStaffRepository universitystaffrepository;
	@Autowired
	ICourseService courseService;
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private IAdmissionService admissionService;
	
	@Autowired
	private IAdmissionCommiteeMemberService committeeMemberService;
	/**
	 * Method : addStaff
	 * 
	 * @param UniversityStaffMember.
	 * @return UniversityStaffMember object.
	 * @throws UniversityStaffNotFoundException. Description : This method creates a
	 *                                           new entry for an staffMember.
	 * 
	 */

	@Override
	public UniversityStaffMember addStaff(UniversityStaffMember user) {
		user.getUser().setPassword(bcryptEncoder.encode(user.getUser().getPassword()));
		user.getUser().setRole(Role.UNIVERSITYSTAFFMEMBER);
		universitystaffrepository.save(user);
		return user;
	}

	/**
	 * Method : updateStaff
	 * 
	 * @param UniversityStaffMember.
	 * @return UniversityStaffMember object.
	 * @throws UniversityStaffNotFoundException 
	 * @throws UniversityStaffNotFoundException. Description : This method Updates
	 *                                           record of staffMember.
	 * 
	 */

	@Override
	public UniversityStaffMember updateStaff(UniversityStaffMember user) throws UniversityStaffNotFoundException {
		Optional<UniversityStaffMember> universitystaffmember1 = universitystaffrepository.findById(user.getStaffId());
		if (universitystaffmember1.isEmpty()) {
			throw new UniversityStaffNotFoundException("No Staff found with given id" + user.getStaffId());
		}
		if (user.getUser() != null) {
			if (user.getUser().getRole() == null)
				user.getUser().setRole((universitystaffmember1.get().getUser().getRole()));
			if (user.getUser().getPassword() != null)
				user.getUser().setPassword(bcryptEncoder.encode(user.getUser().getPassword()));
		} else {
			user.setUser(universitystaffmember1.get().getUser());
		}

		return universitystaffrepository.save(user);

	}

	/**
	 * Method : viewStaff
	 * 
	 * @param staffid.
	 * @return UniversityStaffMember object.
	 * @throws UniversityStaffNotFoundException. Description : This method view
	 *                                           record of staffMember.
	 * 
	 */

	@Override
	public UniversityStaffMember viewStaff(int staffid) throws UniversityStaffNotFoundException {
		Optional<UniversityStaffMember> staff = universitystaffrepository.findById(staffid);
		if (staff.isEmpty())
			throw new UniversityStaffNotFoundException("Staff Member not found");
		return staff.get();
	}

	/**
	 * Method : removeStaff
	 * 
	 * @param staffid.
	 * @return void.
	 * @throws UniversityStaffNotFoundException. Description : This method remove
	 *                                           record of staffMember.
	 * 
	 */

	@Override
	public void removeStaff(int staffid) throws UniversityStaffNotFoundException {
		Optional<UniversityStaffMember> universitystaffmember1 = universitystaffrepository.findById(staffid);
		if (universitystaffmember1.isEmpty()) {
			throw new UniversityStaffNotFoundException("No Staff found with given id" + staffid);
		}
		universitystaffrepository.deleteById(staffid);

	}

	/**
	 * Method : viewAllStaff
	 * 
	 * @param .
	 * @return List of UniversityStaffMember object.
	 * @throws UniversityStaffNotFoundException. Description : This method
	 *                                           viewAllStaff record of staffMember.
	 * 
	 */

	@Override
	public List<UniversityStaffMember> viewAllStaffs() throws UniversityStaffNotFoundException {
		List<UniversityStaffMember> staffList = universitystaffrepository.findAll();
		if (staffList.isEmpty())
			throw new UniversityStaffNotFoundException("Staff not found");
		else
			return staffList;

	}

	/**
	 * Method : addCourse
	 * 
	 * @param Course.
	 * @return Course object.
	 * @throws CourseIsAlreadyExistException.
	 * @throws CourseNotFoundException.
	 *  Description : This method create new entry
	 *                                  for course .
	 * 
	 */

	@Override
	public Course addCourse(Course course) throws CourseNotFoundException, CourseIsAlreadyExistException {
		return courseService.addCourse(course);
	}

	/**
	 * Method : removeStaff
	 * 
	 * @param courseId.
	 * @return Course object.
	 * @throws AdmissionAlreadyExistException , CourseNotFoundException
	 * @throws CourseNotFoundException. Description : This method remove record
	 *                                  course .
	 * 
	 */

	@Override
	public Course removeCourse(int courseId) throws CourseNotFoundException, AdmissionAlreadyExistException {
		return courseService.removeCourse(courseId);
	}

	/**
	 * Method : updateStaff
	 * 
	 * @param Course.
	 * @return Course object.
	 * @throws CourseNotFoundException. Description : This method update record of
	 *                                  course .
	 * 
	 */

	@Override
	public Course updateCourse(Course course) throws CourseNotFoundException {
		return courseService.updateCourse(course);
	}
	
	@Override
	public int getCountOfStaff() {
		List<UniversityStaffMember> staffMembers = universitystaffrepository.findAll();
		return staffMembers.size();
	}
	
	@Override
	public Map<String, Integer> getStats(){
		Map<String, Integer> stats = new HashMap<>();
		stats.put("UniversityStaffMember", getCountOfStaff());
		stats.put("Course", courseService.getCountOfCourses());
		stats.put("Admissions", admissionService.getCountOfAdmissions());
		stats.put("CommitteeMembers", committeeMemberService.getCountOfCommitteeMembers());
		return stats;
	}

}
