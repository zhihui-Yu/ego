package com.ego.passport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

@Controller
public class TbUserController {
	
	@Resource
	private TbUserService tbUserServiceImpl;
	
	/**
	 * 显示登入页面
	 * @param url 点击登入前的页面
	 * @param model
	 * @param interurl 重定向的页面
	 * @return
	 */
	@RequestMapping("user/showLogin")
	public String showLogin(@RequestHeader(value="Referer",defaultValue="") String url, Model model, String interurl) {
		model.addAttribute("redirect",url.equals("")?interurl:url);
		return "login";
	}
	
	/**
	 * 登入
	 * @param user
	 * @return
	 */
	@RequestMapping("user/login")
	@ResponseBody
	public EgoResult login(TbUser user,HttpServletRequest request, HttpServletResponse response) {
		return tbUserServiceImpl.login(user, request, response);
	}
	
	/**
	 * JSONP 跨域获取用户信息
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("user/token/{token}")
	@ResponseBody
	public Object getUserInfo(@PathVariable String token, String callback) {
		EgoResult er = tbUserServiceImpl.getUserInfoByToken(token);
		//如果有回调函数名
		if(callback != null && !callback.equals("")){
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		//没有回调函数名 就直接返回
		return er;
	}
	
	/**
	 * 退出
	 * @param token
	 * @param callback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("user/logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable String token, String callback,
						HttpServletRequest request,
						HttpServletResponse response){
		EgoResult er = tbUserServiceImpl.logout(token, request, response);
		if(callback != null && !callback.equals("")){
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		return er;
	}
}
