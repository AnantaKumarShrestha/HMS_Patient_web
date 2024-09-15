package com.box.bookstore.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.box.bookstore.model.PatientModel;
import com.box.bookstore.service.PatientService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@RestController
@OpenAPIDefinition(info=@Info(title="Patient Rest Api",version ="Version 1",description = "Patient CRUD operaion"))
public class PatientRestController {
	
	
	@Autowired
	private PatientService patientService;
	
	@GetMapping("/api/patientList")
	public List<PatientModel> getAllPatient() {
		return patientService.getAllPatients();
	}
	
	@DeleteMapping("/api/delete/{id}")
	public String deletePatient(@PathVariable int id) {
		patientService.getdeletedbyid(id);
		return "Deleted Successfully";
	}
	
	@GetMapping("/api/patient/{id}")
	public PatientModel getPatient(@PathVariable int id) {
	
		return patientService.getPatientId(id);
	}
	

}
