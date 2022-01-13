package com.universitycourseapp.entities;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name="admission")
public class Admission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int admissionId;
	@NotNull(message = "Course Id is required")
	@Positive(message = "Invalid course Id")
	private int courseId;
	@NotNull(message = "Applicant Id is required")
	//@Positive(message = "Invalid applicant id")
	private int applicantId;
	@NotNull(message = "Admission date is required")
	private LocalDate admissionDate;
	private AdmissionStatus status;
	public Admission() {
	}
	public Admission(int admissionId, int courseId, int applicantId, LocalDate admissionDate, AdmissionStatus status) {
		super();
		this.admissionId = admissionId;
		this.courseId = courseId;
		this.applicantId = applicantId;
		this.admissionDate = admissionDate;
		this.status = status;
	}
	public int getAdmissionId() {
		return admissionId;
	}
	public void setAdmissionId(int admissionId) {
		this.admissionId = admissionId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(int applicantId) {
		this.applicantId = applicantId;
	}
	public LocalDate getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(LocalDate admissionDate) {
		this.admissionDate = admissionDate;
	}
	public AdmissionStatus getStatus() {
		return status;
	}
	public void setStatus(AdmissionStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Admission [admissionId=" + admissionId + ", courseId=" + courseId + ", applicantId=" + applicantId
				+ ", admissionDate=" + admissionDate + ", status=" + status + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(admissionDate, admissionId, applicantId, courseId, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admission other = (Admission) obj;
		return Objects.equals(admissionDate, other.admissionDate) && admissionId == other.admissionId
				&& applicantId == other.applicantId && courseId == other.courseId && status == other.status;
	}
	
}
