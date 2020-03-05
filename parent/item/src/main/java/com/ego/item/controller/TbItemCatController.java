package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemCatService;

@Controller
public class TbItemCatController {

	@Resource
	private TbItemCatService tbItemCatServiceImpl;
	
	@RequestMapping("rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue showMenu(String callback) {
		//将数据json化
		MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatServiceImpl.showCatItem());
		//设置返回函数名
		mjv.setJsonpFunction(callback);
		//返回数据
		return mjv;
	}
}
