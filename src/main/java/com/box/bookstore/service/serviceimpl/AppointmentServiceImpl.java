package com.box.bookstore.service.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.box.bookstore.model.AppointmentModel;
import com.box.bookstore.repo.Appointmentrepo;
import com.box.bookstore.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private Appointmentrepo appointmentrepo;

	@Override
	public void addAppointment(AppointmentModel appointmentModel) {
		// TODO Auto-generated method stub
		appointmentrepo.save(appointmentModel);
		
	}

	@Override
	public List<AppointmentModel> getAllAppointment() {
		// TODO Auto-generated method stub
		return appointmentrepo.findAll();
	}

	@Override
	public List<AppointmentModel> getAllAppointmentByPatientId(int id) {
		// TODO Auto-generated method stub
		return appointmentrepo.findByPatientid(id);
	}

	@Override
	public void deleteAppointment(int id) {
		// TODO Auto-generated method stub
		
		appointmentrepo.deleteById(id);
		
	}

	@Override
	public AppointmentModel getAppointmentById(int id) {
		// TODO Auto-generated method stub
		return appointmentrepo.findById(id).get();
	}

	@Override
	public List<AppointmentModel> getAllAppointmentRequestByPatient(int id) {
		// TODO Auto-generated method stub
		
		List<AppointmentModel> appointmentList=appointmentrepo.findByPatientid(id);
		List<AppointmentModel> appointmentRequest=new ArrayList<>();
		for (AppointmentModel appointment:appointmentList) {
			String check="pending";
			String appointmentReq=appointment.getAppointmentStatus();
			if(appointmentReq.equals(check)) {
				appointmentRequest.add(appointment);
			}
		}
		
		Collections.reverse(appointmentRequest);
		return appointmentRequest;
	}

	@Override
	public List<AppointmentModel> getAllAppointmentCancelByPatient(int id) {
		// TODO Auto-generated method stub
	
		List<AppointmentModel> appointmentList=appointmentrepo.findByPatientid(id);
		List<AppointmentModel> appointmentCancel=new ArrayList<>();
		for (AppointmentModel appointment:appointmentList) {
			String check="canceled";
			String appointmentCan=appointment.getAppointmentStatus();
		
			if(appointmentCan.equals(check)) {
				appointmentCancel.add(appointment);
			}
		}
		
		return appointmentCancel;
	}
	

	@Override
	public List<AppointmentModel> getAllAppointmentAcceptedByPatient(int id) {
		// TODO Auto-generated method stub
	

		List<AppointmentModel> appointmentList=appointmentrepo.findByPatientid(id);
		List<AppointmentModel> appointmentAccepted=new ArrayList<>();
		for (AppointmentModel appointment:appointmentList) {
			String check="accepted";
			String appointmentAccept=appointment.getAppointmentStatus();
			String checkcancel="cancel";
			String appointmentCancel=appointment.getPatientCancel();
			
			if(appointmentAccept.equals(check)) {
				
				if(!(appointmentCancel.equals(checkcancel))) {
					
				appointmentAccepted.add(appointment);
				
				}
			}
		}
		
		return appointmentAccepted;
	}

	@Override
	public List<AppointmentModel> getAllAppointmentRequestByDoctorID(int id) {
		String doc_id=Integer.toString(id);
		List<AppointmentModel> appointmentList=appointmentrepo.findByDoctorId(doc_id);
		List<AppointmentModel> appointmentRequest=new ArrayList<>();
		for(AppointmentModel appointment:appointmentList) {
			String check="pending";
			String status=appointment.getAppointmentStatus();
//			String checkCancel="cancel";
//			String cancelStatus=appointment.get
			if(status.equals(check)) {
				appointmentRequest.add(appointment);
			}
		}
		return appointmentRequest;
	}

	@Override
	public List<AppointmentModel> getAllAppointmentAcceptedByDoctorId(int id) {

		String doc_id=Integer.toString(id);
		List<AppointmentModel> appointmentList=appointmentrepo.findByDoctorId(doc_id);
		List<AppointmentModel> appointmentAccepted=new ArrayList<>();
		for(AppointmentModel appointment:appointmentList) {
			String check="accepted";
			String status=appointment.getAppointmentStatus();
			String cancelCheck="cancel";
			String patientCancelStatus=appointment.getPatientCancel();
			if(status.equals(check)) {
				if(!(patientCancelStatus.equals(cancelCheck))) {
				appointmentAccepted.add(appointment);
				}
			}
		}
		return appointmentAccepted;
	}
	
	@Override
	public List<AppointmentModel> getAllAppointmentCancelListByDoctorId(int id){
		String doc_id=Integer.toString(id);
		List<AppointmentModel> appointmentList=appointmentrepo.findByDoctorId(doc_id);
		List<AppointmentModel> appointmentCancel=new ArrayList<>();
		for(AppointmentModel appointment:appointmentList) {
			
			String cancelCheck="cancel";
			String patientCancelStatus=appointment.getPatientCancel();
		
		     if(patientCancelStatus.equals(cancelCheck)){
				appointmentCancel.add(appointment);
				
			}
		}
		return appointmentCancel;
	}

//	@Override
//	public void patientCancelFunction(@RequestParam int id) {
//		// TODO Auto-generated method stub
//		AppointmentModel appointmentModel=appointmentrepo.findById(id).get();
//		appointmentModel.setPatientCancel("cancel");
//		appointmentrepo.save(appointmentModel);
//		
//		
//	}
	
	@Override
	public void patientCancelFunction(@RequestParam int id,String cancelMessage) {
		// TODO Auto-generated method stub
		AppointmentModel appointmentModel=appointmentrepo.findById(id).get();
		appointmentModel.setPatientCancel("cancel");
		appointmentModel.setCancelMessage(cancelMessage);
		appointmentrepo.save(appointmentModel);
		
		
	}
	
	//NEW

	@Override
	public void changeStatusOfAppointment(int id,String status) {
		// TODO Auto-generated method stub
		AppointmentModel appointmentModel=appointmentrepo.findById(id).get();
		appointmentModel.setAppointmentStatus(status);
		appointmentrepo.save(appointmentModel);
		
		
	}

	@Override
	public void changeStatusOfAppointment(int id,String status,String cancelMessage) {
		// TODO Auto-generated method stub
		AppointmentModel appointmentModel=appointmentrepo.findById(id).get();
		appointmentModel.setAppointmentStatus(status);
		appointmentModel.setCancelMessage(cancelMessage);
		appointmentrepo.save(appointmentModel);
		
		
	}
	
	//NEW
	



}
