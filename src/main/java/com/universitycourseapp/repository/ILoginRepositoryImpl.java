package com.universitycourseapp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.universitycourseapp.entities.AdmissionCommitteeMember;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.entities.UniversityStaffMember;
import com.universitycourseapp.exception.LoginFailedException;

@Repository
@Transactional
public class ILoginRepositoryImpl implements ILoginRepository {
	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	// verify uesrname and password for applicant
	@Override
	public boolean verifyApplicantCredentials(String username, String pwd) throws LoginFailedException  {

		TypedQuery<Applicant> q2 = entityManager.createQuery(
				"select a from Applicant a where a.password = :pwd and a.userName = :username", Applicant.class);
		q2.setParameter("username", username);
		q2.setParameter("pwd", pwd);
		List<Applicant> l1 = q2.getResultList();

		if (l1.size() == 0) {
			
			throw new LoginFailedException("Please Enter Valid Username and Password");
		}
		return true;

	}

	// verify username and password for addmission commitee member
	@Override
	public boolean verifyAdmissionCommiteeMemberCredentials(String username, String pwd) throws LoginFailedException {
		TypedQuery<AdmissionCommitteeMember> q2 = entityManager.createQuery(
				"select a from AdmissionCommiteeMember a where a.password = :pwd and a.userName = :username",
				AdmissionCommitteeMember.class);
		q2.setParameter("username", username);
		q2.setParameter("pwd", pwd);
		List<AdmissionCommitteeMember> l1 = q2.getResultList();

		if (l1.size() == 0) {
			throw new LoginFailedException("Please Enter Valid Username and Password");
		}
		return true;
	}

	// verify username and password for university staff member
	@Override
	public boolean verifyUniversityStaffMemberCredentials(String username, String pwd) throws LoginFailedException {
		System.out.println(username +" " + pwd);
		TypedQuery<UniversityStaffMember> q2 = entityManager.createQuery(
				"select a from UniversityStaffMember a where a.password = :pwd and a.userName = :username",
				UniversityStaffMember.class);
		q2.setParameter("username", username);
		q2.setParameter("pwd", pwd);
		List<UniversityStaffMember> l1 = q2.getResultList();

		if (l1.size() == 0) {
			throw new LoginFailedException("Please Enter Valid Username and Password");
		}
		return true;
	}
	
	public List<UniversityStaffMember> getUniversityStaffMemberCredentials(String username) {
		TypedQuery<UniversityStaffMember> q2 = entityManager.createQuery(
				"select a from UniversityStaffMember a where a.userName = :username",
				UniversityStaffMember.class);
		q2.setParameter("username", username);
		List<UniversityStaffMember> l1 = q2.getResultList();
		return l1;
	}
	public List<AdmissionCommitteeMember> getAddmitionCommitteMemberCredentials(String username) {
		TypedQuery<AdmissionCommitteeMember> q2 = entityManager.createQuery(
				"select a from AdmissionCommitteeMember a where a.userName = :username",
				AdmissionCommitteeMember.class);
		q2.setParameter("username", username);
		List<AdmissionCommitteeMember> l1 = q2.getResultList();
		return l1;
	}

}
