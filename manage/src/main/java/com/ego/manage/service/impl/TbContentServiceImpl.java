package com.ego.manage.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;

@Service
public class TbContentServiceImpl implements TbContentService{

	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Override
	public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
		
		return tbContentDubboServiceImpl.selContentByPage(categoryId, page, rows);
	}

}
