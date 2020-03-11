package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.item.service.TbItemService;

@Controller
public class TbItemCotroller {

	@Resource
	private TbItemService tbItemServiceImpl;
	
	@RequestMapping("item/{id}.html")
	public String showItemDetails(@PathVariable long id, Model model) {
		model.addAttribute("item",tbItemServiceImpl.show(id));
		return "item";
	}
}
