package com.mrgan.blog.web.action;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView; 

@Controller
@RequestMapping("/test")
public class TestAction {

	Logger logger = Logger.getLogger(TestAction.class);

	@RequestMapping("/test")
	public ModelAndView testRsa() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
}
