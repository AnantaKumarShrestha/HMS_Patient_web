package com.box.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/website")
public class Webcontroller {
	
	
	@GetMapping("/home")
	public String gethome() {
		return "patientuserinterface";
	}
	
	@GetMapping("/about")
	public String getabout() {
		return "about";
	}
	@GetMapping("/treatment")
	public String gettreatment() {
		return "treatment";
	}
	@GetMapping("/doctor")
	public String getdoctor() {
		return "doctor";
	}
	@GetMapping("/testimonial")
	public String gettestimonial(Model model) {
		model.addAttribute("indicator","active");
		return "testimonial";
	}
	
	@GetMapping("/contactus")
	public String getcontactus() {
		return "contact";
	}

}
