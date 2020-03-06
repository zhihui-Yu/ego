package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentDubboService {
	/**
	 * 分页查询
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selContentByPage(long categoryId, int page, int rows);
	
	/**
	 * 新增
	 * @param content
	 * @return
	 */
	int insContent(TbContent content) throws Exception;
	
	/**
	 * 修改
	 * @param content
	 * @return
	 * @throws Exception
	 */
	int updContent(TbContent content) throws Exception;
	
	/**
	 * 删除
	 * @param content
	 * @return
	 * @throws Exception
	 */
	int delContent(String ids) throws Exception;
	
	/**
	 * 查出最近的前n个
	 * @param count
	 * @param isSort
	 * @return
	 */
	List<TbContent> selByCount(int count, boolean isSort);
}
