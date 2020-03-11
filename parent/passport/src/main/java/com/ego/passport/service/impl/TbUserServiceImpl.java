package com.ego.passport.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;

@Service
public class TbUserServiceImpl implements TbUserService {
	
	@Reference
	private TbUserDubboService tbUserDubboServiceImpl;
	
	@Resource
	private JedisDao jedisDaoImpl;
	
	@Override
	public EgoResult login(TbUser user,HttpServletRequest request, HttpServletResponse response) {
		EgoResult er = new EgoResult();
		TbUser u = tbUserDubboServiceImpl.selByUser(user);
		if(u!=null){
			er.setStatus(200);
			//成功后 把用户信息放入redis中
			String key = UUID.randomUUID().toString();
			jedisDaoImpl.set(key, JsonUtils.objectToJson(u));
			//过期时间七天
			jedisDaoImpl.expire(key, 60*60*24*7);
			//产生cookie
			CookieUtils.setCookie(request, response, "TT_TOKEN", key, 60*60*24*7);
		} else {
			er.setMsg("用户名密码错误");
		}
		return er;
	}

	@Override
	public EgoResult getUserInfoByToken(String token) {
		EgoResult er = new EgoResult();
		String json = jedisDaoImpl.get(token);
		if(json!=null && !json.equals("")){
			TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
			//密码置空 安全
			user.setPassword("");
			er.setMsg("OK");
			er.setData(user);
			er.setStatus(200);
			return er;
		} else {
			er.setMsg("获取失败");
		}
		return null;
	}

	@Override
	public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
		
		//删除redis中的key
		long index = jedisDaoImpl.del(token);
			
		//设置cookie过期
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		
		EgoResult er = new EgoResult();
		er.setStatus(200);
		er.setMsg("OK");
		return er;
	}

}
