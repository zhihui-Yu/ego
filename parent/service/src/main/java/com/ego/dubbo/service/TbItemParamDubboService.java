package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
	/**
	 * 查找规格参数信息
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showPage(int page, int rows);
	
	/**
	 * 批量删除数据
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	int delByIds(String ids) throws Exception;
	
	/**
	 * 根据类目id查询参数模板
	 * @param catId
	 * @return
	 */
	TbItemParam selByCatid(long catId);
	
	/**
	 * 新增,支持主键自增
	 * @param param
	 * @return
	 */
	int insParam(TbItemParam param) throws Exception;
}
