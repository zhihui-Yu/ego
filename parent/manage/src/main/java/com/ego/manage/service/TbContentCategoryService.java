package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryService {

	/**
	 * 查询所有类目并转换easyui tree 属性要求
	 * @param id
	 * @return
	 */
	List<EasyUiTree> showCategroy(long id);
	
	/**
	 * 类目新增
	 * @param cate
	 * @return
	 */
	EgoResult create(TbContentCategory cate);
	
	/**
	 * 修改类目信息
	 * @param cate
	 * @return
	 */
	int update(TbContentCategory cate) throws Exception;
	
	/**
	 * 删除类目信息(逻辑删除)
	 * @param cate
	 * @return
	 */
	EgoResult delete(TbContentCategory cate);
}
