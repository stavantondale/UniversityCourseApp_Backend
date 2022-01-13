package com.universitycourseapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.AdmissionStatus;

public interface IAdmissionRepository extends JpaRepository<Admission, Integer>{
	@Query("SELECT a FROM Admission a WHERE a.courseId=?1")
	List<Admission> showAllAdmissionByCourseId(int id);
	
	@Query("SELECT a FROM Admission a WHERE a.admissionDate= ?1")
	List<Admission> showAllAdmissionByDate(LocalDate date);
	
	@Query("SELECT a FROM Admission a WHERE a.applicantId= ?1")
	List<Admission> showAllAdmissionsByApplicant(int applicantId);
	
	@Query("SELECT a FROM Admission a WHERE a.applicantId = ?1 AND a.courseId= ?2")
	List<Admission> getAdmissionByApplicantIdAndCourseId(int applicantId, int courseId);
	
	List<Admission> findByCourseIdAndStatus(int courseId, AdmissionStatus status);
}
