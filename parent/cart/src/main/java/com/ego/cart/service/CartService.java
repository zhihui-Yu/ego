package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;

public interface CartService {
	
	/**
	 * 加入购物车
	 * @param id
	 * @param num
	 * @param request
	 */
	void addCart (long id, int num, HttpServletRequest request);
	
	/**
	 * 显示购物车信息
	 * @param request
	 * @return
	 */
	List<TbItemChild> showCart(HttpServletRequest request);
	
	/**
	 * 根据id修改数量
	 * @param id
	 * @param num
	 * @param request
	 * @return
	 */
	EgoResult update(long id, int num, HttpServletRequest request);
	
	
	/**
	 * 删除商品
	 * @param id
	 * @param request
	 * @return
	 */
	EgoResult delete(long id, HttpServletRequest request);
}
