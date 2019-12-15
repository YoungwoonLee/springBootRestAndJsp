package com.chandler.rest.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
	@Value("${welcome.message:test}")
	private String message = "Hello World";
	
	@RequestMapping(value="/")
	public String index( Map<String, Object> model ) {
		model.put("message", this.message);
		return "index";
	}

}
