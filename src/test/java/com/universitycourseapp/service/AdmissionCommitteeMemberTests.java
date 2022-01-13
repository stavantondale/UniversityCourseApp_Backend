
package com.universitycourseapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.universitycourseapp.entities.AdmissionCommitteeMember;
import com.universitycourseapp.entities.Course;
import com.universitycourseapp.entities.Role;
import com.universitycourseapp.entities.User;
import com.universitycourseapp.exception.CommitteeMemberAlreadyExistException;
import com.universitycourseapp.exception.CommitteeMemberNotFound;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.repository.IAdmissionCommiteeMemberRepository;

@SpringBootTest
class AdmissionCommitteeMemberTests {

	@MockBean
	IAdmissionCommiteeMemberRepository repository;

	@Autowired
	IAdmissionCommitteeServiceImpl serviceobj;

	@Test

	@DisplayName("AddCommitteeMember")
	void addCommitteMemberTest() throws CommitteeMemberAlreadyExistException {
		AdmissionCommitteeMember member = new AdmissionCommitteeMember();
		User user = new User(1, "username", "password", Role.ADMISSIONCOMMITTEEMEMBER);
		member.setAdminName("Abc");
		member.setAdminContact("Abc@xyz.com");
		member.setUser(user);
		// System.out.println(member);
		when(repository.save(member)).thenReturn(member);
		AdmissionCommitteeMember member1 = serviceobj.addCommitteeMember(member);
		assertEquals(member.getAdminId(), member1.getAdminId());
	}

	@Test

	@DisplayName("updateCommitteeMamber")
	void updateCommitteeMamberTest() throws CommitteeMemberAlreadyExistException {
		AdmissionCommitteeMember member = new AdmissionCommitteeMember();
		User user = new User(1, "username", "password", Role.ADMISSIONCOMMITTEEMEMBER);
		member.setAdminName("Abc");
		member.setAdminContact("Abc@xyz.com");
		member.setUser(user);
		when(repository.save(member)).thenReturn(member);
		AdmissionCommitteeMember member1 = serviceobj.addCommitteeMember(member);
		assertEquals(member.getAdminId(), member1.getAdminId());
	}

	@Test

	@DisplayName("viewCommitteeMember")
	void viewCommitteeMemberTest() throws CommitteeMemberNotFound, CommitteeMemberAlreadyExistException {
		AdmissionCommitteeMember member = new AdmissionCommitteeMember();
		User user = new User(1, "username", "password", Role.ADMISSIONCOMMITTEEMEMBER);
		member.setAdminName("Abc");
		member.setAdminContact("Abc@xyz.com");
		member.setUser(user);
		when(repository.save(member)).thenReturn(member);
		AdmissionCommitteeMember member1 = serviceobj.addCommitteeMember(member);

		assertEquals(member.getAdminName(), member1.getAdminName());
	}

	@Test //
	@DisplayName("removeCommitteeMember") //
	void removeCommitteeMemberTest() throws CommitteeMemberNotFound { //
		AdmissionCommitteeMember member = new AdmissionCommitteeMember();
		User user = new User(1, "username", "password", Role.ADMISSIONCOMMITTEEMEMBER);
		member.setAdminName("Abc");
		member.setAdminContact("Abc@xyz.com");
		member.setUser(user);
		when(repository.findById(member.getAdminId())).thenReturn(Optional.of(member));
		serviceobj.removeCommitteeMember(member.getAdminId()); //
		verify(repository, times(1)).deleteById(member.getAdminId());
	}

	@Test

	@DisplayName("viewAllCommitteeMembers")
	void viewAllCommitteMembersTest() throws CommitteeMemberNotFound {
		AdmissionCommitteeMember member = new AdmissionCommitteeMember();
		User user = new User(1, "username1", "password", Role.ADMISSIONCOMMITTEEMEMBER);
		member.setAdminName("Abc");
		member.setAdminContact("Abc@xyz.com");
		member.setUser(user);
		AdmissionCommitteeMember member2 = new AdmissionCommitteeMember();
		User user2 = new User(2, "username2", "password", Role.ADMISSIONCOMMITTEEMEMBER);
		member2.setAdminName("pqr");
		member2.setAdminContact("pqr@xyz.com");
		member2.setUser(user2);

		AdmissionCommitteeMember member3 = new AdmissionCommitteeMember();
		User user3 = new User(3, "username3", "password", Role.ADMISSIONCOMMITTEEMEMBER);
		member3.setAdminName("Abc");
		member3.setAdminContact("pqr@xyz.com");
		member3.setUser(user3);
		List<AdmissionCommitteeMember> memberlist = new ArrayList<>();
		memberlist.add(member);
		memberlist.add(member2);
		memberlist.add(member3);
		when(repository.findAll()).thenReturn(memberlist);
		List<AdmissionCommitteeMember> memberlist1 = new ArrayList<>();
		memberlist1 = serviceobj.viewAllCommitteeMembers();
		assertEquals(memberlist.size(), memberlist1.size());
		assertIterableEquals(memberlist, memberlist1);
	}

}
