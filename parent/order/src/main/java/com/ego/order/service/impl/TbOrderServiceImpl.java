package com.ego.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.dao.JedisDao;
import com.ego.redis.dao.impl.JedisDaoImpl;

@Service
public class TbOrderServiceImpl implements TbOrderService {

	private static final String LinkedHashMap = null;

	@Resource
	private JedisDao jedisDaoImpl;

	@Value("${redis.cart.key}")
	private String cartKey;

	@Value("${passport.url}")
	private String passportUrl;

	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;

	@Override
	public List<TbItemChild> showOrderCart(List<Long> ids, HttpServletRequest request) {

		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");

		String result = HttpClientUtil.doPost(passportUrl + token);

		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);

		String key = cartKey + ((LinkedHashMap) er.getData()).get("username");

		String json = jedisDaoImpl.get(key);

		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);

		List<TbItemChild> listNew = new ArrayList<>();

		for (TbItemChild child : list) {
			for (Long id : ids) {
				if ((long) child.getId() == (long) id) {
					// 判断购买量是不是大于库存
					TbItem tbItem = tbItemDubboServiceImpl.selById(id);

					if (tbItem.getNum() >= child.getNum()) {
						child.setEnough(true);
					} else {
						child.setEnough(false);
					}
					listNew.add(child);
				}
			}
		}

		return listNew;
	}

	@Override
	public EgoResult create(MyOrderParam param, HttpServletRequest request) {
		
		//订单表
		TbOrder order = new TbOrder();
		order.setPayment(param.getPayment());
		order.setPaymentType(param.getPaymentType());

		Long id = IDUtils.genItemId();

		order.setOrderId(id + "");

		Date date = new Date();

		order.setCreateTime(date);
		order.setUpdateTime(date);
		
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");

		String result = HttpClientUtil.doPost(passportUrl + token);

		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);

		Map user = (LinkedHashMap) er.getData();
		
		order.setUserId(Long.parseLong(user.get("id").toString()));
		
		order.setBuyerNick(user.get("username").toString());
		
		order.setBuyerRate(0);
		
		//订单-商品关联表
		for (TbOrderItem item : param.getOrderItems()) {
			item.setId(IDUtils.genItemId()+"");
			item.setOrderId(id+"");
		}
		
		//收货人表
		TbOrderShipping shipping = param.getOrderShipping();
		
		shipping.setCreated(date);
		shipping.setUpdated(date);
		shipping.setOrderId(id+"");

		EgoResult erResult = new EgoResult();
		
		try {
			int index = tbOrderDubboServiceImpl.insOrder(order, param.getOrderItems(), shipping);
			
			if(index > 0) {
				erResult.setStatus(200);
				
				//删除购买的商品
				
				String json = jedisDaoImpl.get(cartKey+user.get("username"));
				
				List<TbItemChild> cartList = JsonUtils.jsonToList(json, TbItemChild.class);
				
				List<TbItemChild> listNew = new ArrayList<>();
				
				//商品id相同则删除
				for(TbItemChild child : cartList) {
					for (TbOrderItem item : param.getOrderItems()) {
						if((long)child.getId() == Long.parseLong(item.getItemId())) {
							listNew.add(child);
						}
					}
				}
				
				for (TbItemChild move : listNew) {
					cartList.remove(move);
				}
				
				jedisDaoImpl.set(cartKey+user.get("username"), JsonUtils.objectToJson(cartList));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return erResult;
	}

}
