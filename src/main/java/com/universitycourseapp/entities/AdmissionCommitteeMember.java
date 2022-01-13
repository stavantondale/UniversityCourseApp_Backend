package com.universitycourseapp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "admission_committee_member")
public class AdmissionCommitteeMember {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int adminId;

	@Column(length = 20)
	@NotNull(message = "Admin name can not be empty!!")
	@NotEmpty(message = "Admin name can not be empty!!")
	@NotBlank(message = "Admin name can not be empty!!")
	private String adminName;

	@NotBlank(message = "Admin Contact can not be empty!!")
	@NotNull(message = "Admin Contact can not be empty!!")
	@NotEmpty(message = "Admin Contact can not be empty!!")
	@Column(length = 30)
	private String adminContact;

	@Valid
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	public AdmissionCommitteeMember(int adminId,
			@NotNull(message = "Admin name can not be empty!!") @NotEmpty(message = "Admin name can not be empty!!") @NotBlank(message = "Admin name can not be empty!!") String adminName,
			@NotBlank(message = "Admin Contact can not be empty!!") @NotNull(message = "Admin Contact can not be empty!!") @NotEmpty(message = "Admin Contact can not be empty!!") String adminContact,
			@Valid User user) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminContact = adminContact;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminContact() {
		return adminContact;
	}

	public void setAdminContact(String adminContact) {
		this.adminContact = adminContact;
	}

	public AdmissionCommitteeMember() {
		super();
	}

	@Override
	public String toString() {
		return "AdmissionCommitteeMember [adminId=" + adminId + ", adminName=" + adminName + ", adminContact="
				+ adminContact + ", user=" + user + "]";
	}
	
	
}
