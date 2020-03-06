package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentService {

	/**
	 * 分页显示内容信息
	 * @param categoryid
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showContent(long categoryId,int page,int rows);
	
	/**
	 * 新增
	 * @param content
	 * @return
	 */
	int save(TbContent content) throws Exception;
	
	/**
	 * 修改内容
	 * @param content
	 * @return
	 * @throws Exception
	 */
	int edit(TbContent content) throws Exception;
	
	/**
	 * 删除内容
	 * @param content
	 * @return
	 * @throws Exception
	 */
	int delete(String ids) throws Exception;
}
