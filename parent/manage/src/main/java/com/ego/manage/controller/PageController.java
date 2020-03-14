package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	@RequestMapping("/")
	public String welcome(){
		return "index";
	}
	/**
	 * 显示页面
	 * @param page
	 * @return
	 */
	@RequestMapping("{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
}
