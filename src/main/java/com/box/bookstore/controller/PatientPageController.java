package com.box.bookstore.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.box.bookstore.api.DoctorRestController;
import com.box.bookstore.model.AppointmentModel;
import com.box.bookstore.model.ChangePasswordRequest;
import com.box.bookstore.model.DoctorModel;
import com.box.bookstore.model.PatientModel;
import com.box.bookstore.repo.Patient_repo;
import com.box.bookstore.service.AppointmentService;
import com.box.bookstore.service.PatientService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;

@Controller
public class PatientPageController {
	
	@Autowired 
	private PatientService patientService;
	
	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private DoctorRestController doctorRestController;

	

	
	@GetMapping("/patientinterface")
	public String getPatienInterface(Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		List<DoctorModel> doctorList=null;
		try {
		doctorList=doctorRestController.getDoctorList();
		}catch (Exception e) {
			// TODO: handle exception
		}
	//	PatientModel p=(PatientModel) httpSession.getAttribute("validuser");
		model.addAttribute("appointmentindicator","active");
		model.addAttribute("doctorList",doctorList);
		model.addAttribute("appointmentList",appointmentService.getAllAppointment());
		return "patientuserinterface";
		
	}
	
	
	@PostMapping("/change_patient_password")
	public String changeDoctorPassword(ChangePasswordRequest changePasswordRequest,Model model,HttpSession httpSession,RedirectAttributes redirectAttributes) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		PatientModel patient=(PatientModel) httpSession.getAttribute("validuser");
		
		String currentPassword=changePasswordRequest.getCurrentPassword();
		String newPassword = changePasswordRequest.getNewPassword();
		String confirmNewPassword = changePasswordRequest.getConfirmNewPassword();
		
		
		if(patient.getPassword().equals(currentPassword)) {
			if(newPassword.equals(confirmNewPassword)){
				patient.setPassword(newPassword);
               patientService.addpatient(patient);
               
               redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully");
				
               return "redirect:/patientinterface";
			}
		//	model.addAttribute("password_not_matched","Password not matched");
			redirectAttributes.addFlashAttribute("password_not_matched","Password not matched");
			return "redirect:/user/change-password";
		}
	//	model.addAttribute("password_not_matched","Current Password not matched");
		redirectAttributes.addFlashAttribute("password_not_matched","Current Password not matched");
		return "redirect:/user/change-password";
	}
	
    public static String generateRandomString(int length) {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charSet.length());
            char randomChar = charSet.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
    
  
    
	
	@PostMapping("/forgot_patient_password")
	public String forgotDoctorPassword(@RequestParam("email")String email,Model model,RedirectAttributes redirectAttributes) {
		
		PatientModel patient =patientService.getPatientByEmail(email);

		if(patient==null) {
			redirectAttributes.addFlashAttribute("error","Email not found");
	        return "redirect:/patient/forgot";
		}
		
		try {
		patientService.changePasswordAndEmail(patient,generateRandomString(10));
        redirectAttributes.addFlashAttribute("successMessage", "Password changed and send to your email successfully Change password immediately");
        return "redirect:/patient/login";
		}catch(Exception e) {
			
		}
		redirectAttributes.addFlashAttribute("error","Email not found");
        return "redirect:/patient/forgot";
	}
	
	@GetMapping("/upload")
	public String getPatienIne(PatientModel patientModel,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		return "upload";
		
	}
	
	@GetMapping("/user/change-password")
	public String getUserChangePassword(PatientModel patientModel,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		return "userchangepassword";
		
	}
	

	
	
	@GetMapping("/appointmentlist")
	public String getAppointmentList(Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		PatientModel p=(PatientModel) httpSession.getAttribute("validuser");

		List<AppointmentModel> appointment=appointmentService.getAllAppointmentRequestByPatient(p.getId());
		model.addAttribute("appointmentRequestListIndicator","active");
		model.addAttribute("appointmentList",appointment );
	//	model.addAttribute("patient",appointment);
		
		return "appointmentlist";
		
	}
	
	@GetMapping("/appointmentRequestList")
	public String getAppointmentRequestList(Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		PatientModel p=(PatientModel) httpSession.getAttribute("validuser");

		List<AppointmentModel> appointment=appointmentService.getAllAppointmentRequestByPatient(p.getId());
		model.addAttribute("appointmentRequestListIndicator","active");
		model.addAttribute("appointmentList",appointment );
	//	model.addAttribute("patient",appointment);
		
		return "appointmentlist";
		
	}
	
	

	@GetMapping("/appointmentAcceptedList")
	public String getAppointmentAcceptedList(Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		PatientModel p=(PatientModel) httpSession.getAttribute("validuser");

		List<AppointmentModel> appointment=appointmentService.getAllAppointmentAcceptedByPatient(p.getId());
		model.addAttribute("appointmentAcceptedListIndicator","active");
		model.addAttribute("appointmentList",appointment );
		return "appointmentAcceptList";
		
	}
	

	
	@GetMapping("/appointmentCancelList")
	public String getAppointmentCancelList(Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		PatientModel p=(PatientModel) httpSession.getAttribute("validuser");

		List<AppointmentModel> appointment=appointmentService.getAllAppointmentCancelByPatient(p.getId());
		model.addAttribute("appointmentCancelListIndicator","active");
		model.addAttribute("appointmentList",appointment );
	//	model.addAttribute("patient",appointment);
		
		return "appointmentCanceledList";
		
	}
	
	@GetMapping("/patientContact")
	public String getPatientContact(@RequestParam int id,Model model) {
		String gmail=doctorRestController.getDoctorGmail(id);
		model.addAttribute("gmail", gmail);
		return "patient_contact";
	}
	

	
	

	


}
