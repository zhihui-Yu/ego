package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Controller
public class TbContentCategoryController {

	@Resource
	private TbContentCategoryService tbContentCategoryServiceImpl;

	/**
	 * 查找所有类目信息
	 * @param id
	 * @return
	 */
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyUiTree> showCategory(@RequestParam(defaultValue = "0") long id) {
		return tbContentCategoryServiceImpl.showCategroy(id);
	}

	/**
	 * 创建类目
	 * @param cate
	 * @return
	 */
	@RequestMapping("content/category/create")
	@ResponseBody
	public EgoResult create(TbContentCategory cate) {
		return tbContentCategoryServiceImpl.create(cate);
	}
	
	/**
	 * 更新类目
	 * @param cate
	 * @return
	 */
	@RequestMapping("content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory cate) {
		
		EgoResult er = new EgoResult();
		try {
			int index = tbContentCategoryServiceImpl.update(cate);
			if(index > 0) {
				er.setStatus(200);
			}
		} catch (Exception e) {
			er.setStatus(500);
			er.setData(e.getMessage());
		}
		
		return er;
	}
	/**
	 * 删除类目
	 * @param cate
	 * @return
	 */
	@RequestMapping("content/category/delete")
	@ResponseBody
	public EgoResult delete(TbContentCategory cate) {
		return tbContentCategoryServiceImpl.delete(cate);
	}
}
