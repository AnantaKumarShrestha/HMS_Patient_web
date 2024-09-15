package com.box.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.box.bookstore.model.PatientModel;

public interface Patient_repo extends JpaRepository<PatientModel, Integer> {

	PatientModel findByEmailAndPassword(String email,String password);

	PatientModel findByEmail(String email);
;
	
	 @Query("SELECT p FROM PatientModel p WHERE p.patientPersonalDetailsModel.gmail = :gmail")
	 PatientModel findByGmail(@Param("gmail") String gmail);
	
	
}
