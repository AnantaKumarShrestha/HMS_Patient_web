package com.box.bookstore.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.box.bookstore.model.PatientModel;
import com.box.bookstore.repo.Patient_repo;
import com.box.bookstore.service.PatientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient")
public class PatientController {
	
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private Patient_repo patient_repo;
	
	@GetMapping("/login")
	public String getlogin() {
		return "login";
	}
	
	
	@PostMapping("/login")
	public String postlogin(PatientModel patientModel,Model model,RedirectAttributes redirectAttributes,HttpSession session) {
		
		PatientModel p=patientService.findpatient(patientModel);
		if(p==null) {
			model.addAttribute("username_error","username or password not matched");
			return "login";
		}
	  
		try {
		if(p.getRegistered().equals("yes")) {
//			int n=p.getId();			
//			return "redirect:/patientinterface/"+n;
//			redirectAttributes.addFlashAttribute("patientobj",p);
			session.setAttribute("validuser",p);
			session.setMaxInactiveInterval(9999999*99999*999999);
			return "redirect:/patientinterface";
		}
		}catch(Exception e) {
		
		
		int id=p.getId();
		model.addAttribute("userObject", patientService.getPatientId(id));
		return "registration";
		}
		
		int id=p.getId();
		model.addAttribute("userObject", patientService.getPatientId(id));
		return "registration";
	}
	
	
	@GetMapping("/registration")
	public String getRegistration() {
		return "registration";
	}

	
	@GetMapping("/signup")
	public String getsignup() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String postsignup(PatientModel patientModel,Model model) {
		
		String pass=patientModel.getPassword();
		String conpass=patientModel.getConpassword();
		PatientModel p=patientService.findsameemail(patientModel);
		if(p==null) {
		if(pass.equals(conpass)){
			
			patientService.addpatient(patientModel);
			return "login";
		}
		model.addAttribute("same_username_found", "Password not matched");
		return "signup";
		}
		model.addAttribute("same_username_found", "This email is already registered");
		return "signup";
		
	}
	
	@GetMapping("/logout")
	private String logOut(HttpSession session) {
		session.invalidate();//session kill
		return "login";
	}
	

//	@PostMapping("/registration")
//	public String postregistration(PatientModel patientModel) {
//		patientModel.setRegistered("yes");
//		patientService.addpatient(patientModel);
//		return "redirect:/patientinterface";
//		
//	}

	
	@PostMapping("/registration")
	public String postregistration(PatientModel patientModel,Model model,RedirectAttributes redirectAttributes) {
		PatientModel patient=patient_repo.findByGmail(patientModel.getPatientPersonalDetailsModel().getGmail());
		
		int id=patientModel.getId();
		if(patient==null) {
			
			if(!getDateTrue(patientModel.getPatientPersonalDetailsModel().getDob())) {
//				redirectAttributes.addAttribute("userObject", patientService.getPatientId(id));
//				redirectAttributes.addAttribute("message","Not Valid Date");
				model.addAttribute("userObject", patientService.getPatientId(id));
				model.addAttribute("message","Not Valid Date");
				return "registration";
			}
			
			if(!isAtLeast16YearsOld(patientModel.getPatientPersonalDetailsModel().getDob())) {
//				redirectAttributes.addAttribute("userObject", patientService.getPatientId(id));
//				redirectAttributes.addAttribute("message","16 Years old");
				model.addAttribute("userObject", patientService.getPatientId(id));
				model.addAttribute("message","Age Should Be 16 Years Old");
				return "registration";
			}
			
		patientModel.setRegistered("yes");
		patientService.addpatient(patientModel);
		return "redirect:/patientinterface";
		}
		
		
		System.out.println("as:"+id);
		
		model.addAttribute("userObject", patientService.getPatientId(id));
		model.addAttribute("message","Enter gmail is already used");
		

		
//		redirectAttributes.addAttribute("userObject", patientService.getPatientId(id));
//		redirectAttributes.addAttribute("message","Enter gmail is already used");
//		return "redirect:/patient/registration";
		return "registration";
	}
	
	public static boolean getDateTrue(String date) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate chosenDate = LocalDate.parse(date, formatter);

	    // Get the current date
	    LocalDate currentDate = LocalDate.now();
	    return chosenDate.isBefore(currentDate);
	}
	
	public static boolean isAtLeast16YearsOld(String date) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate chosenDate = LocalDate.parse(date, formatter);

	    // Get the current date
	    LocalDate currentDate = LocalDate.now();

	    // Calculate the period between the chosen date and the current date
	    Period period = Period.between(chosenDate, currentDate);

	    // Check if the period is at least 16 years
	    return period.getYears() >= 16;
	}

	
	
	@GetMapping("/forgot")
	public String forgotPassword() {
		return "forget_patient";
	}
	
	
	

}
