package com.universitycourseapp.entities;

import java.util.Objects;

import javax.persistence.CascadeType;  
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "applicant")
public class Applicant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int applicantId;
	
	@Column(length = 50)
	@NotNull(message = "Applicant Name cannot be null")
	@NotBlank(message = "Applicant Name cannot be blank")
	private String applicantName;
	
	@Column(length = 10)
	@NotNull(message = "Mobile Number cannot be null")
	@NotBlank(message = "Mobile Number cannot be blank")
	private String mobileNumber;
	
	@Column(length = 12)
	@NotNull(message = "Degree cannot be null")
	@NotBlank(message = "Degree cannot be blank")
	private String applicantDegree;
	
	@Column(length = 10)
	//@Size(max=100,min=0,message = "Percent must be between 0 to 100" )
	private double applicantGraduationPercent;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "admission_id")
	private Admission admission;
	private AdmissionStatus status;

	public int getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(int applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getApplicantDegree() {
		return applicantDegree;
	}

	public void setApplicantDegree(String applicantDegree) {
		this.applicantDegree = applicantDegree;
	}

	public double getApplicantGraduationPercent() {
		return applicantGraduationPercent;
	}

	public void setApplicantGraduationPercent(double applicantGraduationPercent) {
		this.applicantGraduationPercent = applicantGraduationPercent;
	}


	public Admission getAdmission() {
		return admission;
	}

	public void setAdmission(Admission admission) {
		this.admission = admission;
	}

	public AdmissionStatus getStatus() {
		return status;
	}

	public void setStatus(AdmissionStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Applicant [applicantId=" + applicantId + ", applicantName=" + applicantName + ", mobileNumber="
				+ mobileNumber + ", applicantDegree=" + applicantDegree + ", applicantGraduationPercent="
				+ applicantGraduationPercent + ", user=" + user + ", admission=" + admission + ", status=" + status
				+ "]";
	}

	public Applicant(int applicantId,
			@NotNull(message = "Applicant Name cannot be null") @NotBlank(message = "Applicant Name cannot be blank") String applicantName,
			@NotNull(message = "Mobile Number cannot be null") @NotBlank(message = "Mobile Number cannot be blank") String mobileNumber,
			@NotNull(message = "Degree cannot be null") @NotBlank(message = "Degree cannot be blank") String applicantDegree,
			int applicantGraduationPercent, User user, Admission admission, AdmissionStatus status) {
		super();
		this.applicantId = applicantId;
		this.applicantName = applicantName;
		this.mobileNumber = mobileNumber;
		this.applicantDegree = applicantDegree;
		this.applicantGraduationPercent = applicantGraduationPercent;
		this.user = user;
		this.admission = admission;
		this.status = status;
	}

	public Applicant() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(admission, applicantDegree, applicantGraduationPercent, applicantId, applicantName,
				mobileNumber, status, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Applicant other = (Applicant) obj;
		return Objects.equals(admission, other.admission) && Objects.equals(applicantDegree, other.applicantDegree)
				&& applicantGraduationPercent == other.applicantGraduationPercent && applicantId == other.applicantId
				&& Objects.equals(applicantName, other.applicantName)
				&& Objects.equals(mobileNumber, other.mobileNumber) && status == other.status
				&& Objects.equals(user, other.user);
	}
	
}
