package com.chairking.poom.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chairking.poom.admin.model.service.AdminService;

@Controller
@RequestMapping("/blame")
public class BlameController {
	@Autowired
	private AdminService service;
	

}
