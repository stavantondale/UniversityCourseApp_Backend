package com.universitycourseapp.service;



import java.util.List;

import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.AdmissionCommitteeMember;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.exception.AdmissionNotFoundException;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CommitteeMemberAlreadyExistException;
import com.universitycourseapp.exception.CommitteeMemberNotFound;


public interface IAdmissionCommiteeMemberService {
public  AdmissionCommitteeMember addCommitteeMember(AdmissionCommitteeMember member) throws CommitteeMemberAlreadyExistException;
public AdmissionCommitteeMember updateCommitteeMember(AdmissionCommitteeMember member) throws CommitteeMemberNotFound;
public AdmissionCommitteeMember viewCommitteeMember(int adminId) throws CommitteeMemberNotFound;
public void removeCommitteeMember(int adminId) throws CommitteeMemberNotFound;
public List<AdmissionCommitteeMember> viewAllCommitteeMembers() throws CommitteeMemberNotFound;
public AdmissionStatus provideAdmissionResult(Applicant applicant, Admission admission);
public void confirmApplication(int admissionId) throws AdmissionNotFoundException, ApplicantNotFoundException;
public void rejectApplication(int admissionId) throws AdmissionNotFoundException, ApplicantNotFoundException;
int getCountOfCommitteeMembers();

}
