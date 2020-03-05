package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;

public interface TbContentService {

	/**
	 * 分页显示内容信息
	 * @param categoryid
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showContent(long categoryId,int page,int rows);
}
