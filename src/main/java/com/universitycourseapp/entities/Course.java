package com.universitycourseapp.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Course {

	@Id
	@GeneratedValue
	@Column(length = 12)
	private int courseId;

	@NotEmpty(message = "Course name is required.")
	@NotBlank(message = "Course name can not be blank.")
	@Column(length = 50)
	private String courseName;

	@Positive(message = "Course duration must be Poisitve")
	@NotNull(message = "Course duration can not be null")
	@NotBlank(message = "Course duration can not be blank")
	@Column(name = "Course_duration_in_months ", length = 12)
	private String courseDuration;

	@NotNull(message = "Course end date can not be null")
	@Column(length = 12)
	private LocalDate courseEndDate;

	@NotNull(message = "Course start date can not be null")
	@Column(length = 12)
	private LocalDate courseStartDate;

	@Positive(message = "Course fees must be Poisitve")
	@NotNull(message = "Course fees can not be null")
	@Column(length = 12)
	private Double courseFees;

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}

	public LocalDate getCourseStartDate() {
		return courseStartDate;
	}

	public void setCourseStartDate(LocalDate courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public LocalDate getCourseEndDate() {
		return courseEndDate;
	}

	public void setCourseEndDate(LocalDate courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public Double getCourseFees() {
		return courseFees;
	}

	public void setCourseFees(Double courseFees) {
		this.courseFees = courseFees;
	}

	public Course() {

	}

	public Course(int courseId, String courseName, String courseDuration, LocalDate courseEndDate,
			LocalDate courseStartDate, Double courseFees) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDuration = courseDuration;
		this.courseEndDate = courseEndDate;
		this.courseStartDate = courseStartDate;
		this.courseFees = courseFees;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseDuration=" + courseDuration
				+ ", courseStartDate=" + courseStartDate + ", courseEndDate=" + courseEndDate + ", courseFees="
				+ courseFees + "]";
	}

}