package com.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.order.pojo.MyOrderParam;

public interface TbOrderService {
	
	/**
	 * 显示订单页面  
	 * 		从redis中取出购物车的选中的内容
	 * @param id
	 * @param request
	 * @return
	 */
	List<TbItemChild> showOrderCart(List<Long> id, HttpServletRequest request);
	
	/**
	 * 创建订单
	 * @param param
	 * @param request
	 * @return
	 */
	EgoResult create(MyOrderParam param, HttpServletRequest request);
}
