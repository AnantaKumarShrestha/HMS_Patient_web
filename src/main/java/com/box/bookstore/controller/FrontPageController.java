package com.box.bookstore.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.box.bookstore.model.PatientModel;

@Controller
public class FrontPageController {

	
	    @GetMapping("/")
	    public String home(Model model) {
	    	model.addAttribute("indexindicator","active");
	    	return "index";
	    }
	    

		@GetMapping("/home")
		public String gethome(Model model) {
			model.addAttribute("indexindicator","active");
			return "index";
		}
		
		@GetMapping("/about")
		public String getabout(Model model) {
			model.addAttribute("aboutindicator","active");
			return "about";
		}
		@GetMapping("/treatment")
		public String gettreatment(Model model) {
			model.addAttribute("treatmentindicator","active");
			return "treatment";
		}
		@GetMapping("/doctor")
		public String getdoctor(Model model) {
			model.addAttribute("doctorindicator","active");
			return "doctor";
		}
		@GetMapping("/testimonial")
		public String gettestimonial(Model model) {
			model.addAttribute("testimonialindicator","active");
			return "testimonial";
		}
		
		@GetMapping("/contactus")
		public String getcontactus(Model model) {
			model.addAttribute("contactindicator","active");
			return "contact";
		}
		
	
	    
	    
	 
}
