package com.universitycourseapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.universitycourseapp.entities.Course;

public interface ICourseRepository extends JpaRepository<Course, Integer> {

	@Query("SELECT a FROM Course a WHERE a.courseName = ?1 AND a.courseDuration= ?2")
	List<Course> getCourseByCourseNameAndCourseDuration(String courseName, String courseDuration);

}
