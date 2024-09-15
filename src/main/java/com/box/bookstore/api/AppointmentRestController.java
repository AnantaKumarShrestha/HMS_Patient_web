package com.box.bookstore.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.box.bookstore.model.AppointmentModel;
import com.box.bookstore.service.AppointmentService;

@RestController
public class AppointmentRestController {

	
	@Autowired
	private AppointmentService appointmentService;
	
	@GetMapping("/api/appointmentRequestList/{id}")
	public List<AppointmentModel>  getAppointmentRequestList(@PathVariable int id) {
	
		return appointmentService.getAllAppointmentRequestByDoctorID(id);
	}
	
	@GetMapping("/api/appointmentList/{id}")
	public List<AppointmentModel> getAppointmentList(@PathVariable int id){
		
		return appointmentService.getAllAppointmentAcceptedByDoctorId(id);
	}
	
	@GetMapping("/api/appointmentCancelList/{id}")
	public List<AppointmentModel> getAppointmentCancelList(@PathVariable int id){
		
		return appointmentService.getAllAppointmentCancelListByDoctorId(id);
	}
	
	@GetMapping("/api/delete/{id}")
	public void getDeleteAppointment(@PathVariable int id) {
		appointmentService.deleteAppointment(id);
	}
	
	@GetMapping("/api/appointmentApprove/{id}")
	public void getAppointmentApproved(@PathVariable int id) {
		appointmentService.changeStatusOfAppointment(id,"accepted");
	}
	////NEW
	@GetMapping("/api/appointmentCanceled/{id}")
	public void getAppointmentCanceled(@PathVariable int id) {
		appointmentService.changeStatusOfAppointment(id,"canceled");
	}
	
	@GetMapping("/api/appointmentCanceled/{id}/{cancelMessage}")
	public void getAppointmentCanceled(@PathVariable int id,@PathVariable String cancelMessage) {
		appointmentService.changeStatusOfAppointment(id,"canceled",cancelMessage);
	}
	//NEW
	
	@GetMapping("/api/appointmentDetails/{id}")
	public AppointmentModel getAppointmentDetails(@PathVariable int id) {
		return appointmentService.getAppointmentById(id);
	}
	
}
