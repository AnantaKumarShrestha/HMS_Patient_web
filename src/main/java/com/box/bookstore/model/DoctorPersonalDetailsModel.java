package com.box.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class DoctorPersonalDetailsModel {
	
	
	private int id;
	private String fullname;
	private String gender;
	private String dob;
	private String gmail;
	private String phonenumber;
	private String address;
	

}
