package com.box.bookstore.model;

import com.box.bookstore.controller.DoctorStudyDetailsModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
public class DoctorModel {
    
	private int id;
	private String email;
	private String password;
	private String registered;
	private String authorized;
	private String conpassword;
	

	

	private DoctorPersonalDetailsModel doctorPersonalDetailsModel;

	private DoctorStudyDetailsModel doctorStudyDetailsModel;
	
	
}
