package com.ego.item.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.ParamItem;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
	
	@Override
	public String showParam(long itemId) {
		TbItemParamItem paramItem = tbItemParamItemDubboServiceImpl.selByItemid(itemId);
		
		//将字符串转对象
		List<ParamItem> list = JsonUtils.jsonToList(paramItem.getParamData(), ParamItem.class);
		
		//前端显示格式
		StringBuilder sb = new StringBuilder();
		
		for (ParamItem item : list) {
			sb.append("<table width='500' style='color:gray'>");
			for (int i = 0; i < item.getParams().size(); i++) {
				//第一行
				sb.append("<tr>");
				if(i==0){
					sb.append("<td align='right' width='30%'>"+item.getGroup()+"</td>");
					sb.append("<td align='right' width='30%'>"+item.getParams().get(i).getK()+"</td>");
					sb.append("<td>"+item.getParams().get(i).getV()+"</td>");
					
				} else {
					sb.append("<tb></tb>");
					sb.append("<td align='right'>"+item.getParams().get(i).getK()+"</td>");
					sb.append("<td align='right'>"+item.getParams().get(i).getV()+"</td>");
				}
				sb.append("</tr>");
			}
			sb.append("</table>");
			sb.append("<hr style='color:gray;'/>");
		}
		return sb.toString();
	}

}
