package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;

public interface TbItemService {
	/**
	 * 显示商品
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(int page,int rows);
	/**
	 * 批量修改商品状态
	 * @param ids
	 * @param status
	 * @return
	 */
	int update(String ids,byte status);
}
