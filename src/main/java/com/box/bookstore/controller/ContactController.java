package com.box.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.box.bookstore.util.MailUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {

	@Autowired
	private MailUtil mailUtil;
	
//	@GetMapping("/contact")
//	public String getContact(HttpSession session) {
//		if(session.getAttribute("validuser")==null) {
//			return "login";
//		}
//		return "ContactForm";
//	}
	
	@PostMapping("/contact")
	public String postContact(@RequestParam String fullname,@RequestParam String email,@RequestParam String phonenumber,@RequestParam String message,HttpSession httpSession) {
		
//		if(httpSession.getAttribute("validuser")==null) {
//			return "login";
//		}

		String toEmail="anantashrestha45@gmail.com";
		String Message="Full name : "+fullname+"\nEmail: "+email+"\nPhone Number: "+phonenumber+"\nMessage: "+message;
		String subject="Information";
		mailUtil.sendEmail(toEmail,subject,Message);
		return "redirect:/contactus";
	}
	
	@PostMapping("/indexcontact")
	public String postIndexContact(@RequestParam String fullname,@RequestParam String email,@RequestParam String phonenumber,@RequestParam String message,HttpSession session) {
		
//		if(session.getAttribute("validuser")==null) {
//			return "login";
//		}
		String toEmail="anantashrestha45@gmail.com";
		String Message="Full name : "+fullname+"\nEmail: "+email+"\nPhone Number: "+phonenumber+"\nMessage: "+message;
		String subject="Information";
		mailUtil.sendEmail(toEmail,subject,Message);
		return "redirect:/";
	}
	
	
	@PostMapping("/patientContact")
	public String postPatientContact(@RequestParam String email,@RequestParam String subject,@RequestParam String message,Model model,HttpSession httpSession) {
		
		if(httpSession.getAttribute("validuser")==null) {
			return "login";
		}
		
		mailUtil.sendEmail(email,subject,message);
		model.addAttribute("gmail", email);
		return "patient_contact";
	}
	
}
