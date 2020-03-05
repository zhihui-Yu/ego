package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.manage.service.TbContentService;

@Controller
public class TbContentController {
	
	@Resource
	private TbContentService tbContentServiceImpl;
	
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDataGrid showContent(long categoryId, int page, int rows){
		return tbContentServiceImpl.showContent(categoryId, page, rows);
	}
}
