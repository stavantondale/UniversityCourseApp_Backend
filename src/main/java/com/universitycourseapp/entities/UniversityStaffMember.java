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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "university_staff_member")
public class UniversityStaffMember {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int staffId;
	
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	public UniversityStaffMember() {
	}

	public UniversityStaffMember(int staffId, User user) {
		super();
		this.staffId = staffId;
		this.user = user;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UniversityStaffMember [staffId=" + staffId + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(staffId, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UniversityStaffMember other = (UniversityStaffMember) obj;
		return staffId == other.staffId && Objects.equals(user, other.user);
	}
	
}
