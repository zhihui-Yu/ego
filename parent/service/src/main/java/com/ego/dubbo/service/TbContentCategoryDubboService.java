package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {
	
	/**
	 * 根据父id查询所有子项目
	 * @param id
	 * @return
	 */
	List<TbContentCategory> selByPid(long id);
	
	/**
	 * 新增
	 * @param cate
	 * @return
	 */
	int insTbContentCategory (TbContentCategory cate) throws Exception;
	
	/**
	 * 修改父节点的isParent属性
	 * @param cate
	 * @return
	 */
	int updTbContentCategoryById(TbContentCategory cate) throws Exception;
	
	/**
	 * 通过id查询类目信息
	 * @param id
	 * @return
	 */
	TbContentCategory selById(long id);
	
}
