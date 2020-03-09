package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	
	@Value("${search.url}")
	private String url;
	
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		return tbItemDubboServiceImpl.show(page, rows);
	}
	
	@Override
	public int update(String ids, byte status) {
		int index = 0 ;
		TbItem item = new TbItem();
		String[] idsStr = ids.split(",");
		for (String id : idsStr) {
			item.setId(Long.parseLong(id));
			item.setStatus(status);
			index +=tbItemDubboServiceImpl.updItemStatus(item);
		}
		if(index==idsStr.length){
			return 1;
		}
		return 0;
	}
	
	@Override
	public int save(TbItem item, String desc, String itemParams) throws Exception {
		long id = IDUtils.genItemId();
		
		item.setId(id);
		
		Date date = new Date();
		
		item.setCreated(date);
		
		item.setUpdated(date);
		
		item.setStatus((byte)1);
		
		TbItemDesc itemDesc = new TbItemDesc();
		
		itemDesc.setItemDesc(desc);
		
		itemDesc.setItemId(id);
		
		itemDesc.setCreated(date);
		
		itemDesc.setUpdated(date);
		
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setCreated(date);
		paramItem.setUpdated(date);
		paramItem.setItemId(id);
		paramItem.setParamData(itemParams);
		
		int index = 0;
		
		index = this.tbItemDubboServiceImpl.insTbItemDesc(item, itemDesc, paramItem);
		
		
		//将数据与solr同步
		//内部类使用的变量 要用final修饰  这样确保在运行时不会找不到变量
		final TbItem itemFinal = item;
		final String descFinal = desc;
		new Thread(){
			public void run() {
				Map<String,Object> map = new HashMap<>();
				map.put("item", itemFinal);
				map.put("desc", descFinal);
				HttpClientUtil.doPostJson(url,JsonUtils.objectToJson(map));
			};
		}.start();
		
		return index;
	}
}
