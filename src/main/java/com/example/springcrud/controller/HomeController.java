package com.example.springcrud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	@Value("${server.servlet.context-path}")
    private String contextPath; //
	
	@GetMapping({"/", "/home"})
	public String home(Model model) {
		model.addAttribute("contextPath", contextPath);
		
		log.debug("slf4j debug 메서드 test.......");
			
		return "home";
	}

}