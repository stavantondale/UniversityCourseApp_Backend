package com.universitycourseapp.service;



import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.AdmissionCommitteeMember;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.entities.Role;
import com.universitycourseapp.exception.AdmissionNotFoundException;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CommitteeMemberAlreadyExistException;
import com.universitycourseapp.exception.CommitteeMemberNotFound;
import com.universitycourseapp.repository.IAdmissionCommiteeMemberRepository;
@Service("admissionCommitteMemberService")
public class IAdmissionCommitteeServiceImpl implements IAdmissionCommiteeMemberService{

	@Autowired
	private IAdmissionCommiteeMemberRepository repository;
	
	@Autowired
	private AdmissionServiceImpl admissionServiceObj;

	@Autowired
	private IApplicantService applicantServiceObj;
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
	}
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	/**
	 * method - addCommitteeMember
	 * @param        Admission Committee member
	 * @return       Response Entity of Object type
	 * Description : This method calls save method of JpaRepository
	 * 
	 * 
	 */

	@Override
	public AdmissionCommitteeMember addCommitteeMember(AdmissionCommitteeMember member) throws CommitteeMemberAlreadyExistException {
		Optional<AdmissionCommitteeMember> o =repository.findById(member.getAdminId());
		if(o.isPresent()) {
			throw new CommitteeMemberAlreadyExistException("Committee Member Already Present!!");
		}
		member.getUser().setPassword(bcryptEncoder.encode(member.getUser().getPassword()));
		member.getUser().setRole(Role.ADMISSIONCOMMITTEEMEMBER);
		return repository.save(member);
	}
	
	/**
	 * method - updateCommitteeMember
	 * @param        Admission Committee member
	 * @return       Response Entity of Object type
	 * Description : This method calls save method of JpaRepository.
	 * 
	 * 
	 */
	@Override
	public AdmissionCommitteeMember updateCommitteeMember(AdmissionCommitteeMember member) throws CommitteeMemberNotFound {
		Optional<AdmissionCommitteeMember> o = repository.findById(member.getAdminId());
		if(o.isEmpty()) {
			throw new CommitteeMemberNotFound("No Committee Member found by this ID:"+member.getAdminId());
		}
		if (member.getUser()!= null) {
			if (member.getUser().getRole() == null)
				 member.getUser().setRole(o.get().getUser().getRole());
			if (member.getUser().getPassword() != null)
				 member.getUser().setPassword(bcryptEncoder.encode(member.getUser().getPassword()));
		} else {
			member.setUser(o.get().getUser());
		}
		//member.getUser().setPassword(bcryptEncoder.encode(member.getUser().getPassword()));
		//member.getUser().setRole(Role.ADMISSIONCOMMITTEEMEMBER);
		return repository.save(member);
	}
	/**
	 * method - viewCommitteeMember
	 * @param        adminId
	 * @return       Response Entity of Object type
	 * Description : This method retrieves data for an existing Committee Member by adminId.
	 * 
	 * 
	 */
	

	@Override
	public AdmissionCommitteeMember viewCommitteeMember(int adminId) throws CommitteeMemberNotFound {
		
		Optional<AdmissionCommitteeMember> o = repository.findById(adminId);
		if(o.isEmpty())
			throw new CommitteeMemberNotFound("No Committee Member found by this ID!!");
		//member.getUser().setPassword(bcryptEncoder.encode(member.getUser().getPassword()));
		//member.getUser().setRole(Role.ADMISSIONCOMMITTEEMEMBER);
		return o.get();
	}

	/**
	 * method - removeCommitteeMember
	 * @param        adminId
	 * @return       Response Entity of Object type
	 * Description : This method retrieves data for an existing Committee Member by adminId and deletes it from the database.
	 * 
	 * 
	 */

	@Override
	public void removeCommitteeMember(int adminId) throws CommitteeMemberNotFound{
		Optional<AdmissionCommitteeMember> member = repository.findById(adminId);
		if(member.isEmpty())
			throw new CommitteeMemberNotFound("No Committee Member found by this ID!!");
		//member.getUser().setPassword(bcryptEncoder.encode(member.getUser().getPassword()));
		//member.getUser().setRole(Role.ADMISSIONCOMMITTEEMEMBER);
		repository.deleteById(adminId);
	}
	
	/**
	 * method - viewAllCommitteeMembers
	 * @param        
	 * @return       Response Entity of Object type
	 * Description : This method retrieves data for all existing Committee Member of the database.
	 * 
	 * 
	 */

	@Override
	public List<AdmissionCommitteeMember> viewAllCommitteeMembers() throws CommitteeMemberNotFound {
		if(repository.findAll().isEmpty()) {
			throw new CommitteeMemberNotFound("No Committee Member found !!");
		}
		
		return repository.findAll();
		
	}
	/**
	 * method - confirmApplication
	 * @param        admissionId
	 * @return       This method does not return anything
	 * Description : This method calls the confirmAdmission method of IApplicantServiceImpl
	 * 
	 * 
	 */

	@Override
	public void confirmApplication(int admissionId) throws AdmissionNotFoundException, ApplicantNotFoundException {
			admissionServiceObj.confirmAdmission(admissionId);
	}
	/**
	 * method - rejectApplication
	 * @param        admissionId
	 * @return       This method does not return anything
	 * Description : This method calls the cancelAdmission method of IApplicantServiceImpl
	 * 
	 * 
	 */
	@Override
	public void rejectApplication(int admissionId) throws AdmissionNotFoundException, ApplicantNotFoundException{
			admissionServiceObj.cancelAdmission(admissionId);}
	/**
	 * method - provideAdmissionResult
	 * @param        Applicant object applicant , Admission object admission
	 * @return       Response Entity of AdmissionStatus 
	 * Description : This method accepts the applicant and admission object and then returns the AdmissionStatus.
	 * 
	 * 
	 */
	@Override
	public AdmissionStatus provideAdmissionResult(Applicant applicant, Admission admission) { 	
		return  repository.provideAdmissionResult(applicant.getApplicantId(), admission.getAdmissionId());	
	}
	
	@Override
	public int getCountOfCommitteeMembers() {
		return repository.findAll().size();
	}
	
}
