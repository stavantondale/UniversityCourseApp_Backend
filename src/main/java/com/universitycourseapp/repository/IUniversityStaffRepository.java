package com.universitycourseapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.entities.UniversityStaffMember;

public interface IUniversityStaffRepository extends JpaRepository<UniversityStaffMember,Integer> {
	
	public List<UniversityStaffMember> findByUserUserId(int userId);
	/*
	 * public UniversityStaffMember addStaff(UniversityStaffMember user);
	 * 
	 * public UniversityStaffMember updateStaff(UniversityStaffMember user);
	 * 
	 * public UniversityStaffMember viewStaff(int staffid);
	 * 
	 * public void removeStaff(int staffid);
	 * 
	 * public List<UniversityStaffMember> viewAllStaffs();
	 */
}
