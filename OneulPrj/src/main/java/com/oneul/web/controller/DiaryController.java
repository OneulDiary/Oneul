package com.oneul.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/diary/")
public class DiaryController {
	
	@RequestMapping("list")
	public String list() {
		
		return "diary/list";
	}
	
	@RequestMapping("reg")
	public String reglist() {
		
		return "diary/reg";
	}

}
