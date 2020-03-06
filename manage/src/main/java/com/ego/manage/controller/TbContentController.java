package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

@Controller
public class TbContentController {

	@Resource
	private TbContentService tbContentServiceImpl;

	/**
	 * 查询所有类目
	 * 
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
		return tbContentServiceImpl.showContent(categoryId, page, rows);
	}

	/**
	 * 新增
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping("content/save")
	@ResponseBody
	public EgoResult save(TbContent content) {
		EgoResult er = new EgoResult();
		try {
			tbContentServiceImpl.save(content);
			er.setStatus(200);
		} catch (Exception e) {
			er.setStatus(500);
			er.setData(e.getMessage());
		}
		return er;
	}

	/**
	 * 修改
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping("rest/content/edit")
	@ResponseBody
	public EgoResult edit(TbContent content) {
		EgoResult er = new EgoResult();

		try {
			tbContentServiceImpl.edit(content);
			er.setStatus(200);
		} catch (Exception e) {
			er.setStatus(500);
			er.setData(e.getMessage());
		}

		return er;
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("content/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult er = new EgoResult();

		try {
			tbContentServiceImpl.delete(ids);
			er.setStatus(200);
		} catch (Exception e) {
			er.setStatus(500);
			er.setData(e.getMessage());
		}
		return er;
	}
}
