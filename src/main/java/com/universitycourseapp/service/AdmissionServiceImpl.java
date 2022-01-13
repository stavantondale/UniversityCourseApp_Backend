package com.universitycourseapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universitycourseapp.dto.AdmissionDTO;
import com.universitycourseapp.entities.Admission;
import com.universitycourseapp.entities.AdmissionStatus;
import com.universitycourseapp.entities.Applicant;
import com.universitycourseapp.entities.Course;
import com.universitycourseapp.exception.AdmissionAlreadyExistException;
import com.universitycourseapp.exception.AdmissionNotFoundException;
import com.universitycourseapp.exception.ApplicantNotFoundException;
import com.universitycourseapp.exception.CourseNotFoundException;
import com.universitycourseapp.repository.IAdmissionRepository;

@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired
	IAdmissionRepository repository;

	@Autowired
	ICourseService courseService;

	@Autowired
	IApplicantService applicantService;

	/**
	 * 
	 * Method : addAdmission
	 * 
	 * @param AdmissionDTO
	 * @throws AdmissionAlreadyExistException
	 * @return Object of type Admission Description : This method stores Admission
	 *         record in table
	 * @throws CourseNotFoundException
	 * @throws ApplicantNotFoundException, AdmissionAlreadyExistException,
	 *                                     CourseNotFoundException
	 * 
	 */
	@Override
	public Admission addAdmission(Admission admission)
			throws AdmissionAlreadyExistException, CourseNotFoundException, ApplicantNotFoundException {
		courseService.viewCourse(admission.getCourseId());
		applicantService.viewApplicant(admission.getApplicantId());
		List<Admission> admissions = repository.getAdmissionByApplicantIdAndCourseId(admission.getApplicantId(),
				admission.getCourseId());
		if (admissions.size() > 0)
			throw new AdmissionAlreadyExistException("Applicant already applied for this course");
		admission.setStatus(AdmissionStatus.APPLIED);
		return repository.save(admission);
	}

	/**
	 * Method : updateAdmission
	 * 
	 * @param AdmissionDTO
	 * @return Object of type Admission Description : This method updates admission
	 *         record
	 * @throws Exception
	 * 
	 */
	@Override
	public Admission updateAdmission(Admission admission) throws Exception {
		Optional<Admission> admission1 = repository.findById(admission.getAdmissionId());
		courseService.viewCourse(admission.getCourseId());
		if (admission1.isEmpty())
			throw new AdmissionNotFoundException("Admission not found with id: " + admission.getAdmissionId());
		else {
			if (admission.getApplicantId() != admission1.get().getApplicantId())
				throw new Exception("Given Applicant id not matched with existing Applicant id");
		}
		return repository.save(admission);
	}

	/**
	 * Method : confirmAdmission
	 * 
	 * @param admissionId
	 * @throws AdmissionNotFoundException
	 * @return Object of type Admission Description : This method updates Admission
	 *         Status to "CONFIRMED" of admission record
	 * 
	 */
	@Override
	public Admission confirmAdmission(int id) throws AdmissionNotFoundException, ApplicantNotFoundException {
		Admission admission;
		Applicant applicant;
		Optional<Admission> admissionOptional = repository.findById(id);
		if (admissionOptional.isEmpty()) {
			throw new AdmissionNotFoundException("No admission found with given id: " + id);
		} else {
			admission = admissionOptional.get();
			admission.setStatus(AdmissionStatus.CONFIRMED);
			admission.setAdmissionDate(LocalDate.now());
			applicant = applicantService.viewApplicant(admission.getApplicantId());
			applicant.setStatus(AdmissionStatus.CONFIRMED);
			applicantService.updateStatus(applicant);
			return admission;
		}
	}

	/**
	 * Method : cancelAdmission
	 * 
	 * @param admissionId
	 * @throws AdmissionNotFoundException
	 * @return Object of type Admission Description : This method updates Admission
	 *         Status to "REJECTED" of admission record
	 * 
	 */
	@Override
	public Admission cancelAdmission(int id) throws AdmissionNotFoundException, ApplicantNotFoundException {
		Admission admission;
		Optional<Admission> admissionOptional = repository.findById(id);
		if (admissionOptional.isEmpty()) {
			throw new AdmissionNotFoundException("No admission found with given id: " + id);
		} else {
			admission = admissionOptional.get();
			Applicant applicant = applicantService.viewApplicant(admission.getApplicantId());
			admission.setStatus(AdmissionStatus.REJECTED);
			applicant.setStatus(AdmissionStatus.REJECTED);
			applicantService.updateStatus(applicant);
			return admission;
		}
	}

	/**
	 * Method : showAllAdmissionByCourseId
	 * 
	 * @param course ID
	 * @throws CourseNotFoundException, AdmissionNotFoundException
	 * @return List of Admission Description : This method return list of admission
	 *         records of the specific course
	 * @throws ApplicantNotFoundException
	 * 
	 */
	@Override
	public List<AdmissionDTO> showAllAdmissionByCourseId(int id)
			throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException {
		courseService.viewCourse(id);
		List<Admission> admissions = repository.showAllAdmissionByCourseId(id);
		if (admissions.isEmpty())
			throw new AdmissionNotFoundException("Admission not found with id: " + id);
		else {
			List<AdmissionDTO> admissionsDTO = new ArrayList<>();
			for (Admission admission : admissions)
				admissionsDTO.add(getAdmissionDetails(admission));
			return admissionsDTO;
		}
	}

	/**
	 * Method : showAllAdmissionByDate
	 * 
	 * @param LocalDate
	 * @throws AdmissionNotFoundException
	 * @return List of Admission Description : This method return list of admission
	 *         records of the specific date
	 * @throws ApplicantNotFoundException
	 * @throws CourseNotFoundException
	 * 
	 */
	@Override
	public List<AdmissionDTO> showAllAdmissionByDate(LocalDate date)
			throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException {
		List<Admission> admissions = repository.showAllAdmissionByDate(date);
		if (admissions.isEmpty())
			throw new AdmissionNotFoundException("Admissions not found for given date: " + date);
		else {
			List<AdmissionDTO> admissionsDTO = new ArrayList<>();
			for (Admission admission : admissions) {
				admissionsDTO.add(getAdmissionDetails(admission));
			}
			return admissionsDTO;
		}

	}

	/**
	 * Method : showAllAdmissionsByApplicant
	 * 
	 * @param applicant Id
	 * @throws AdmissionNotFoundException
	 * @return List of Admission Description : This method return list of admission
	 *         records of the specific applicant
	 * @throws ApplicantNotFoundException
	 * @throws CourseNotFoundException
	 * 
	 */
	@Override
	public AdmissionDTO showAllAdmissionsByApplicant(int applicantId)
			throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException {
		List<Admission> admissions = repository.showAllAdmissionsByApplicant(applicantId);
		if (admissions.isEmpty())
			throw new AdmissionNotFoundException("Applicant not found with Id: " + applicantId);
		else {
			List<AdmissionDTO> admissionsDTO = new ArrayList<>();
			for (Admission admission : admissions) {
				admissionsDTO.add(getAdmissionDetails(admission));
			}
			return admissionsDTO.get(0);
		}

	}

	/**
	 * Method : calculateTotalCost
	 * 
	 * @param course ID
	 * @throws CourseNotFoundException, AdmissionNotFoundException
	 * @return total cost of course Description : This method return total cost of
	 *         the course
	 * 
	 */
	@Override
	public double calculateTotalCost(int courseId) throws CourseNotFoundException, AdmissionNotFoundException {

		double courseFee = courseService.getCourseFee(courseId);
		List<Admission> admissions = repository.findByCourseIdAndStatus(courseId, AdmissionStatus.CONFIRMED);
		//if (admissions.isEmpty())
			//throw new AdmissionNotFoundException("Admissions not found for given course. Course id: " + courseId);
		return courseFee * admissions.size();
	}

	@Override
	public List<AdmissionDTO> showAllAdmissions() throws AdmissionNotFoundException, CourseNotFoundException, ApplicantNotFoundException {
		List<Admission> admissions = repository.findAll();
		if (admissions.isEmpty())
			throw new AdmissionNotFoundException("Admission not found");
		else {
			List<AdmissionDTO> admissions1 = new ArrayList<>();
			for (Admission admission : admissions) {
				admissions1.add(getAdmissionDetails(admission));
			}
			return admissions1;
		}
	}
	
	@Override
	public int getCountOfAdmissions() {
		return repository.findAll().size();
	}
	
	private AdmissionDTO getAdmissionDetails(Admission admission)
			throws CourseNotFoundException, ApplicantNotFoundException {
		AdmissionDTO admissionDetails = new AdmissionDTO();
		Course course = courseService.viewCourse(admission.getCourseId());
		Applicant applicant = applicantService.viewApplicant(admission.getApplicantId());
		admissionDetails.setAdmissionId(admission.getAdmissionId());
		admissionDetails.setApplicant(applicant);
		admissionDetails.setCourseName(course.getCourseName());
		return admissionDetails;
	}
}
