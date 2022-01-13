package com.universitycourseapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.AdmissionCommitteeMember;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.exception.CommitteeMemberAlreadyExistException;
import com.universitycourseapp.exception.CommitteeMemberNotFound;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.service.IAdmissionCommiteeMemberService;
import com.universitycourseapp.service.IApplicantService;

@RestController
@RequestMapping("/admissionCommitte")
@CrossOrigin("http://localhost:4200/")
public class AdmissionCommitteeMemberController {
	@Autowired
	IAdmissionCommiteeMemberService serviceObj;

	@Autowired
	IApplicantService applicants;

	// to create the AdmissionCommitteMember
	// //http://localhost:8586/admissionCommitte/addMember
	@PostMapping("/addMember") //
	public ResponseEntity<AdmissionCommitteeMember> addMember(@RequestBody @Validated AdmissionCommitteeMember member) throws CommitteeMemberAlreadyExistException {
		member = serviceObj.addCommitteeMember(member);
		return new ResponseEntity<>(member, new HttpHeaders(),	HttpStatus.OK);
	}
	/*******************************
	 * Method : addMember
	 * 
	 * @param member
	 * 
	 * @throws The method throws CommitteeMemberAlreadyExistException if Committee Member is already present.
	 *
	 * @return Response Entity of Object type Description : Admission Committee Member Added successfully.
	 * 
	 * @postmapping: Post mapping requests a body from the user which is then
	 *               persisted onto the database.
	 ******************************/

	
	@GetMapping("/getMember/{id}") //// http://localhost:8590/admissionCommitte/getMember/1
	public ResponseEntity<AdmissionCommitteeMember> getMember(@PathVariable("id") int id) throws CommitteeMemberNotFound {
		AdmissionCommitteeMember member = serviceObj.viewCommitteeMember(id);
		return new ResponseEntity<>(member, new HttpHeaders(), HttpStatus.OK);
	}
	/*******************************
	 * Method : getMember
	 * 
	 * @param id
	 * 
	 * @throws The method throws CommitteeMemberNotFound if Committee Member is not present for the given id.
	 *             fields
	 * @return Response Entity of Object which has fetched using the id provided by the user. 
	 * 
	 * @GetMapping: Annotation for mapping HTTP GET requests onto specific handler methods. 
	 *              
	 ******************************/

	@GetMapping("/getAllMembers") // http://localhost:8590/admissionCommitte/getAllMembers
	public ResponseEntity<List<AdmissionCommitteeMember>> getAllMembers() throws CommitteeMemberNotFound {
		List<AdmissionCommitteeMember> memberlist = serviceObj.viewAllCommitteeMembers();
		if (memberlist.isEmpty()) {
			throw new CommitteeMemberNotFound("There are no Committe Members");
		}
		else
			return new ResponseEntity<>(memberlist, new HttpHeaders(), HttpStatus.OK);
	}
	/*******************************
	 * Method : getAllMembers
	 * 
	 * @throws The method throws CommitteeMemberNotFound if Committee Member is not present.
	 *             fields
	 * @return Response Entity of list Object which contains all the committee members. 
	 * 
	 * @GetMapping: Annotation for mapping HTTP GET requests onto specific handler methods. 
	 *              
	 ******************************/


	@DeleteMapping("/deleteMember/{id}") // http://localhost:8590/admissionCommitte/deleteMember/1
	public ResponseEntity<Void> deleteMember(@PathVariable("id") int id) throws CommitteeMemberNotFound {
		serviceObj.removeCommitteeMember(id);
		return new ResponseEntity<>(new HttpHeaders(),
				HttpStatus.OK);
	}
	/*******************************
	 * Method : deleteMember
	 * 
	 * @param id
	 * 
	 * @throws The method throws CommitteeMemberNotFound if Committee Member is not present for the given id.
	 *             fields
	 * @return Response Entity of Object type Description : Admission Committee Member deleted successfully. 
	 * 
	 * @DeleteMapping: Annotation for mapping HTTP DELETE requests onto specific handler methods. 
	 *              
	 ******************************/

	@PutMapping("/updateMember") //http://localhost:8590/admissionCommitte/updateMember
	public ResponseEntity<AdmissionCommitteeMember> updateMember(@Validated @RequestBody AdmissionCommitteeMember member) throws CommitteeMemberNotFound {
		serviceObj.updateCommitteeMember(member);
		return new ResponseEntity<>( new HttpHeaders(),
				HttpStatus.OK);
	}
	/*******************************
	 * Method : updateMember
	 * 
	 * @param member
	 * 
	 * @throws The method throws CommitteeMemberNotFound if Committee Member is not present for the given id.
	 *             fields
	 * @return Response Entity of Object type Description : Admission Committee Member updated successfully. 
	 * 
	 * @PutMapping: Annotation for mapping HTTP PUT requests onto specific handler methods. 
	 *              
	 ******************************/
	
	
	
	@PostMapping("/getAdmissionResults") //http://localhost:8590/admissionCommitte/getAdmissionResults
	public ResponseEntity<AdmissionStatus> getAdmissionResults(@RequestBody Applicant applicant) throws CourseNotFoundException {
		applicants.addApplicant(applicant);
		Admission admission = applicant.getAdmission();
		AdmissionStatus s = serviceObj.provideAdmissionResult(applicant, admission);
		return new ResponseEntity<>(s , new HttpHeaders(), HttpStatus.OK);
	}
	/*******************************
	 * Method : getAdmissionResults
	 * 
	 * @param applicant
	 * 
	 * @throws The method throws CourseNotFoundException if course of applicant does not matched to course records
	 *             fields
	 * @return Response Entity of Object type Description : AdmissionStatus. 
	 * 
	 * @PostMapping: Post mapping requests a body from the user which is then persisted onto the database. 
	 *              
	 ******************************/
	
	
}
