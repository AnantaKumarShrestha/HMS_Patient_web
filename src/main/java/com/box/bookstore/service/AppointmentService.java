package com.box.bookstore.service;

import java.util.List;

import com.box.bookstore.model.AppointmentModel;

public interface AppointmentService {
   
	void addAppointment(AppointmentModel appointmentModel);
	
	List<AppointmentModel> getAllAppointment();
	
	List<AppointmentModel> getAllAppointmentByPatientId(int id);
	
	void deleteAppointment(int id);
	
	AppointmentModel getAppointmentById(int id);
	
	List<AppointmentModel> getAllAppointmentRequestByPatient(int id);
	
	List<AppointmentModel> getAllAppointmentCancelByPatient(int id);
	
	List<AppointmentModel> getAllAppointmentAcceptedByPatient(int id);
	
	List<AppointmentModel> getAllAppointmentRequestByDoctorID(int id);
	
	List<AppointmentModel> getAllAppointmentAcceptedByDoctorId(int id);
	
	List<AppointmentModel> getAllAppointmentCancelListByDoctorId(int id);
	
//	void patientCancelFunction(int id);
	void patientCancelFunction(int id,String cancelMessage);
	
	//NEW
	void changeStatusOfAppointment(int id,String status);
	void changeStatusOfAppointment(int id,String status,String cancelMessage);
	//NEW

	
}
