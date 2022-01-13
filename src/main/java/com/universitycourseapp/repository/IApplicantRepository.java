package com.universitycourseapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;

public interface IApplicantRepository extends JpaRepository<Applicant,Integer>{
	
	@Query("select a from Applicant a where a.status=?1")
	public List<Applicant> viewAllApplicantsByStatus(AdmissionStatus status);
	
	@Query("select a from Applicant a join a.admission ad where a.applicantId=ad.applicantId and ad.courseId=?1")
	public List<Applicant> viewAllApplicantsByCourse(int courseId);
	//@Query("select a from Applicant a join u where a.userId=u.userId  u.userId=?1")
	public List<Applicant> findByUserUserId(int userId);
	
}
