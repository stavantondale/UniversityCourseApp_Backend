package com.universitycourseapp.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.universitycourseapp.entities.AdmissionCommitteeMember;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;


public interface IAdmissionCommiteeMemberRepository extends JpaRepository<AdmissionCommitteeMember, Integer>{
//public AdmissionCommitteeMember addCommitteeMember(AdmissionCommitteeMember member);
//public AdmissionCommitteeMember updateCommitteeMember(AdmissionCommitteeMember member);
//public AdmissionCommitteeMember viewCommitteeMember(int adminId);
//public void removeCommitteeMember(int adminId);
//public List<AdmissionCommitteeMember> viewAllCommitteeMembers();
	
	@Query("SELECT a.status FROM Admission a WHERE a.applicantId = ?1 AND a.admissionId= ?2")
	AdmissionStatus provideAdmissionResult(int applicantId, int admissionId);
//	
	public List<AdmissionCommitteeMember> findByUserUserId(int userId);
//	@Query("select a.status from Applicant a join a.admission ad where a.applicantId=ad.applicantId and ad.courseId=?1")
//	public List<Applicant> viewAllApplicantsByCourse(int courseId);
}
