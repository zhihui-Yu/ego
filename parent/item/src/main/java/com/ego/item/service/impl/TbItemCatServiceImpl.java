package com.ego.item.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortMenu;
import com.ego.item.pojo.PortMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	
	@Override
	public PortMenu showCatItem() {
		//所有一级菜单
		List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
		
		PortMenu pm = new PortMenu();
		pm.setData(selAllMenu(list));
		
		return pm;
	}
	
	public List<Object> selAllMenu(List<TbItemCat> list) {
		List<Object> listNode = new LinkedList<>();
		
		//循环遍历 查找所有子节点
		for (TbItemCat tbItemCat : list) {
			//判断是不是父节点
			if(tbItemCat.getIsParent()) {
				PortMenuNode pmn = new PortMenuNode();
				pmn.setU("/products/"+tbItemCat.getId()+".html");
				pmn.setN("<a href ='/products/'"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				pmn.setI(selAllMenu(tbItemCatDubboServiceImpl.show(tbItemCat.getId())));
				listNode.add(pmn);
			} else {
				listNode.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		return listNode;
	}
}
