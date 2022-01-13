package com.universitycourseapp.repository;

import java.util.List;

import com.universitycourseapp.entities.AdmissionCommitteeMember;
import com.universitycourseapp.entities.UniversityStaffMember;
import com.universitycourseapp.exception.LoginFailedException;

public interface ILoginRepository {
	
	
	public boolean verifyApplicantCredentials(String username,String pwd) throws LoginFailedException;
	public boolean verifyAdmissionCommiteeMemberCredentials(String username,String pwd) throws LoginFailedException;
	public boolean verifyUniversityStaffMemberCredentials(String username,String pwd) throws LoginFailedException;
	public List<UniversityStaffMember> getUniversityStaffMemberCredentials(String username);
	public List<AdmissionCommitteeMember> getAddmitionCommitteMemberCredentials(String username);
	
	 
}
