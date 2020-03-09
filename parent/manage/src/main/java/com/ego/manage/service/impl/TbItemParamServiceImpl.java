package com.ego.manage.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

@Service
public class TbItemParamServiceImpl implements TbItemParamService {

	@Reference
	private TbItemParamDubboService tbItemParamDubboService;
	
	@Reference
	private TbItemCatDubboService TbItemCatDubboServiceImpl;
	
	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		//获取分页数据
		EasyUIDataGrid datagrid = this.tbItemParamDubboService.showPage(page, rows);
		
		//获取该页数据
		List<TbItemParam> list = (List<TbItemParam>)datagrid.getRows();
		
		//因为前端还需要一个商品类目
		List<TbItemParamChild> listChild = new LinkedList<>();
		
		//循环查找商品类目
		for (TbItemParam param : list) {
			TbItemParamChild child = new TbItemParamChild();
			
			child.setId(param.getId());
			child.setItemCatId(param.getItemCatId());
			child.setParamData(param.getParamData());
			child.setCreated(param.getCreated());
			child.setUpdated(param.getUpdated());
			//通过商品的id找到名字
			child.setItemCatName(TbItemCatDubboServiceImpl.selById(child.getItemCatId()).getName());
			
			listChild.add(child);
		}
		
		datagrid.setRows(listChild);
		
		return datagrid;
	}

	@Override
	public int delete(String ids) throws Exception {
		return this.tbItemParamDubboService.delByIds(ids);
	}

	@Override
	public EgoResult showParam(long catId) {
		EgoResult er = new EgoResult();
		TbItemParam param = tbItemParamDubboService.selByCatid(catId);
		if(param != null) {
			er.setData(param);
			er.setStatus(200);
		}
		return er;
	}

	@Override
	public EgoResult save(TbItemParam param) throws Exception {
		Date date = new Date();
		param.setCreated(date);
		param.setUpdated(date);
		
		int index = tbItemParamDubboService.insParam(param);
		
		EgoResult er = new EgoResult();
		if(index > 0){
			er.setStatus(200);
		}
		return er;
	}

}
