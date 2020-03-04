package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

@Controller
public class TbItemParamController {
	@Resource
	private TbItemParamService tbItemParamServiceImpl;

	/**
	 * 查询规格参数信息
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("item/param/list")
	@ResponseBody
	public EasyUIDataGrid showPage(int page, int rows) {
		return this.tbItemParamServiceImpl.showPage(page, rows);
	}
	
	/**
	 * 批量删除规格参数信息
	 * @param ids
	 * @return
	 */
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult er = new EgoResult();
		
		try {
			int index = tbItemParamServiceImpl.delete(ids);
			if(index == 1) {
				er.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return er;
	}
	
	/**
	 * 查询商品的模板
	 * 有则不能新增，无则可以
	 * @param catId
	 * @return
	 */
	@RequestMapping("item/param/query/itemcatid/{catId}")
	@ResponseBody
	public EgoResult show(@PathVariable long catId) {
		return tbItemParamServiceImpl.showParam(catId);
	}
	
	/**
	 * 新增商品规格参数
	 * @param param
	 * @param catId
	 * @return
	 */
	@RequestMapping("item/param/save/{catId}") 
	@ResponseBody
	public EgoResult save(TbItemParam param, @PathVariable long catId) {
		param.setItemCatId(catId);
		try {
			return tbItemParamServiceImpl.save(param);
		} catch (Exception e) {
			return new EgoResult();
		}
	}
}

