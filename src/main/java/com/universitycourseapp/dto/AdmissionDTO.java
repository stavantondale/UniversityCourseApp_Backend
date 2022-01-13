package com.universitycourseapp.dto;

import com.universitycourseapp.entities.Applicant;

public class AdmissionDTO {
	private int admissionId;
	private String courseName;
	private Applicant applicant;
	public AdmissionDTO() {
		// TODO Auto-generated constructor stub
	}
	public int getAdmissionId() {
		return admissionId;
	}
	public void setAdmissionId(int admissionId) {
		this.admissionId = admissionId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Applicant getApplicant() {
		return applicant;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	@Override
	public String toString() {
		return "AdmissionDTO [admissionId=" + admissionId + ", courseName=" + courseName + ", applicant=" + applicant
				+ "]";
	}
}
