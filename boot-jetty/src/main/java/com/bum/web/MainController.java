package com.bum.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bum.service.HelloWorldService;

@Controller
public class MainController {

	@Autowired
	private HelloWorldService helloWorldService;

	@GetMapping("/")
	@ResponseBody
	public String helloWorld() {
		return this.helloWorldService.getHelloMessage();
	}

}
