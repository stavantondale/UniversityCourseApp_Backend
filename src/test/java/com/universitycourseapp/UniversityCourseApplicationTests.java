package com.universitycourseapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.service.IAdmissionService;

@SpringBootTest
class UniversityCourseApplicationTests {

	/*
	 * @Test void contextLoads() { }
	 */

	@Autowired
	IAdmissionService admissionService;

	@Test
	void addAdmissionTest() {
		/*
		 * Admission admission = new Admission(); admission.setAdmissionId(1);
		 * admission.setApplicantId(1); admission.setCourseId(1);
		 * admission.setAdmissionDate(LocalDate.now());
		 * admission.setStatus(AdmissionStatus.PENDING); System.out.println(admission);
		 * Admission admission1 = admissionService.addAdmission(admission);
		 * assertEquals(admission.getApplicantId(), admission1.getAdmissionId());
		 */
	}

}
