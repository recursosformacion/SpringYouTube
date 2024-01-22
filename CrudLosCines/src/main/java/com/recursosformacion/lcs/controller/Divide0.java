package com.recursosformacion.lcs.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/error")
public class Divide0 {

	@GetMapping("/")
	public String darError() {
		int a=100;
		int b=0;
		int c=a/b;
		return "No sale nunca";
				
	}
}
