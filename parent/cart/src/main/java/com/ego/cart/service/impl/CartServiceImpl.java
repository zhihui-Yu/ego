package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class CartServiceImpl implements CartService {

	@Resource
	private JedisDao jedisDaoImpl;

	@Reference
	private TbItemDubboService tbItemdubboServiceImpl;

	@Value("${redis.cart.key}")
	private String cartKey;

	@Value("${passport.url}")
	private String passportUrl;

	@Override
	public void addCart(long id, int num, HttpServletRequest request) {
		/**
		 * 将购物车信息存储到redis中
		 */

		// 存放所有购物车商品
		List<TbItemChild> list = new ArrayList<>();

		// 判断是否已登入
		// 获取redis中的登入信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");

		String jsonUser = HttpClientUtil.doPost(passportUrl + token);

		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);

		// jackson会把 date转为linkedhashMap
		String key = cartKey + ((LinkedHashMap) er.getData()).get("username");

		// 如果redis中存在key 则可以添加购物车
		if (jedisDaoImpl.exists(key)) {
			String json = jedisDaoImpl.get(key);
			if (json != null && !json.equals("")) {
				list = JsonUtils.jsonToList(json, TbItemChild.class);

				for (TbItemChild tbItemChild : list) {
					// 如果购物车中有该商品则 改变数量
					if ((long) tbItemChild.getId() == id) {
						tbItemChild.setNum(tbItemChild.getNum() + num);
						// 重新添加入redis
						jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
						return;
					}
				}
			}
		}

		// 如果不存在该商品
		TbItem item = tbItemdubboServiceImpl.selById(id);

		TbItemChild child = new TbItemChild();

		child.setId(item.getId());
		child.setTitle(item.getTitle());
		child.setImages(
				item.getImage() == null || item.getImage().equals("") ? new String[1] : item.getImage().split(","));
		child.setNum(num);
		child.setPrice(item.getPrice());

		// 如果redis中有商品信息 则 在后面在添加商品信息， 如果没有就重新加
		list.add(child);

		jedisDaoImpl.set(key, JsonUtils.objectToJson(list));

	}

	@Override
	public List<TbItemChild> showCart(HttpServletRequest request) {
		// 判断是否已登入
		// 获取redis中的登入信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");

		String jsonUser = HttpClientUtil.doPost(passportUrl + token);

		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);

		// jackson会把 date转为linkedhashMap
		String key = cartKey + ((LinkedHashMap) er.getData()).get("username");
		
		String json = jedisDaoImpl.get(key);
		return JsonUtils.jsonToList(json, TbItemChild.class);
	}

	@Override
	public EgoResult update(long id, int num, HttpServletRequest request) {
		// 判断是否已登入
		// 获取redis中的登入信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");

		String jsonUser = HttpClientUtil.doPost(passportUrl + token);

		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);

		// jackson会把 date转为linkedhashMap
		String key = cartKey + ((LinkedHashMap) er.getData()).get("username");
		
		String json = jedisDaoImpl.get(key);
		
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		
		//减少商品的数量
		for (TbItemChild child : list) {
			if(child.getId() == id) {
				child.setNum(num);
			}
		}
		
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		
		EgoResult egoResult = new EgoResult();
		if(ok.equals("OK")){
			egoResult.setStatus(200);
		}
		
		return egoResult;
	}

	@Override
	public EgoResult delete(long id, HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");

		String jsonUser = HttpClientUtil.doPost(passportUrl + token);

		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);

		// jackson会把 date转为linkedhashMap
		String key = cartKey + ((LinkedHashMap) er.getData()).get("username");
		
		String json = jedisDaoImpl.get(key);
		
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		
		TbItemChild move = null;
		
		//减少商品的数量
		for (TbItemChild child : list) {
			if(child.getId() == id) {
				move = child;
			}
		}
		
		list.remove(move);
		
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		
		EgoResult egoResult = new EgoResult();
		if(ok.equals("OK")){
			egoResult.setStatus(200);
		}
		
		return egoResult;
	}

}
