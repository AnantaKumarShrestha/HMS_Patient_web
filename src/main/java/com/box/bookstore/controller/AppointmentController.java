package com.box.bookstore.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.box.bookstore.api.DoctorRestController;
import com.box.bookstore.model.AppointmentModel;
import com.box.bookstore.model.PatientModel;
import com.box.bookstore.service.AppointmentService;
import com.box.bookstore.service.PatientService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppointmentController {

	
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PatientService patientService;
	@Autowired
	private DoctorRestController doctorRestController;
	
	@PostMapping("/appointment")
	public String postAppointment(AppointmentModel appointmentModel,HttpSession httpSession,RedirectAttributes redirectAttributes) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		if(getTimeTrue(appointmentModel.getAppointmentdate())) {
			redirectAttributes.addFlashAttribute("dateerror", "Not Valid Date");
			return "redirect:/patientinterface";
		}
		
		PatientModel p=(PatientModel) httpSession.getAttribute("validuser");
		int id=p.getId();
		appointmentModel.setPatientid(id);
		String sid=appointmentModel.getDoctorId();
		int iid=Integer.parseInt(sid);
		appointmentModel.setDoctor_name(doctorRestController.getDoctorName(iid));
		appointmentModel.setAppointmentStatus("pending");
		appointmentModel.setPatientCancel("nothing");
		String date=getFormatDate(appointmentModel.getAppointmentdate());
		appointmentModel.setAppointmentdate(date);
		appointmentService.addAppointment(appointmentModel);
		redirectAttributes.addFlashAttribute("successMessage", "Appointment request send successfully");
		return "redirect:/patientinterface";
	}
	
	public static String getFormatDate(String date) {
		 LocalDateTime dateTime = LocalDateTime.parse(date);

	        // Format LocalDateTime into desired format with AM/PM
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
	        String formattedDateTime = dateTime.format(formatter);
	        return formattedDateTime;
	}
	
	
	
	
	public static boolean getTimeTrue(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime chosenDate = LocalDateTime.parse(date, formatter);

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        return chosenDate.isBefore(currentDateTime);
	}

	
	@GetMapping("/appointmentRequestCancel")
	public String getAppointmentRequestCancel(@RequestParam int id,Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
	
		
		appointmentService.deleteAppointment(id);
	
		return "redirect:/appointmentRequestList";
		
	}
	
	@GetMapping("/appointmentCancel")
	public String getAppointmentCancel(@RequestParam int id,Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
	
		
		appointmentService.deleteAppointment(id);
	
		return "redirect:/appointmentCancelList";
		
	}
//	@GetMapping("/appointmentPatientCanceled")
//	public String getAppointmentPatientcanceled(@RequestParam int id,Model model,HttpSession httpSession) {
//		
//		if(httpSession.getAttribute("validuser")==null) {
//			return "login";
//		}
//	
//		
//		appointmentService.patientCancelFunction(id);
//	
//		return "redirect:/appointmentAcceptedList";
//		
//	}
	
	@GetMapping("/appointmentPatientCanceled")
	public String getAppointmentPatientcanceled(@RequestParam int id,@RequestParam String cancelMessage,Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
	
		
		appointmentService.patientCancelFunction(id,cancelMessage);
	
		return "redirect:/appointmentAcceptedList";
		
	}
	
	@GetMapping("/appointmentViewDetailsForRequest")
	public String getAppointmentViewDetailsForRequest(@RequestParam int id,Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		model.addAttribute("appointmentRequestListIndicator","active");
		model.addAttribute("appointmentObject", appointmentService.getAppointmentById(id));
		return "appointment_view_details_for_request";
	}
	
	@GetMapping("/appointmentViewDetailsForAccepted")
	public String getAppointmentViewDetailsForAccpted(@RequestParam int id,Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		model.addAttribute("appointmentAcceptedListIndicator","active");
		model.addAttribute("appointmentObject", appointmentService.getAppointmentById(id));
		return "appointment_view_details_for_accepted";
	}
	
	@GetMapping("/appointmentViewDetailsForCancel")
	public String getAppointmentViewDetailsForCancel(@RequestParam int id,Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		model.addAttribute("appointmentCancelListIndicator","active");
		model.addAttribute("appointmentObject", appointmentService.getAppointmentById(id));
		return "appointment_view_details_for_cancel";
	}
	
	@GetMapping("/appointmentAcceptedContactToDoctor")
	public String getAppointmentAcceptedContactToDoctor(@RequestParam int id,Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		model.addAttribute("appointmentAcceptedListIndicator","active");
		model.addAttribute("doctorObject", doctorRestController.getDoctor(id));
		return "appointment_view_doctor_contact";
	}
	
	
	
	
	//////////////
	
	
	
}
