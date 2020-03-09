package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService {

	@Resource
	private TbContentMapper tbContentMapper;

	@Override
	public EasyUIDataGrid selContentByPage(long categoryId, int page, int rows) {
		// 设置分页条件
		PageHelper.startPage(page, rows);

		TbContentExample example = new TbContentExample();
		// 设置不是默认id情况
		if (categoryId != 0) {
			example.createCriteria().andCategoryIdEqualTo(categoryId);
		}

		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);

		PageInfo<TbContent> pi = new PageInfo<>(list);

		EasyUIDataGrid datagrid = new EasyUIDataGrid();

		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());

		return datagrid;
	}

	@Override
	public int insContent(TbContent content) throws Exception{
		int index = tbContentMapper.insertSelective(content);
		if(index > 0 ){
			return index;
		} else {
			throw new Exception("新增内容失败");
		}
	}

	@Override
	public int updContent(TbContent content) throws Exception {
		int index = tbContentMapper.updateByPrimaryKeyWithBLOBs(content);
		if(index > 0) {
			return index;
		} else {
			throw new Exception("内容修改出现错误");
		}
	}

	@Override
	public int delContent(String ids) throws Exception {
		int index = 0;
		String[] id = ids.split(",");
		for (String idStr : id) {
			index += tbContentMapper.deleteByPrimaryKey(Long.parseLong(idStr));
		}
		if(index == id.length) {
			return index;
		} else {
			throw new Exception("内容删除出现错误");
		}
	}

	@Override
	public List<TbContent> selByCount(int count, boolean isSort) {
		TbContentExample example = new TbContentExample();
		//排序
		if(isSort) {
			example.setOrderByClause("updated desc");
		}
		
		if(count!=0) {
			PageHelper.startPage(1, count);
			List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
			PageInfo<TbContent> pi = new PageInfo<>(list);
			return pi.getList();
		} else {
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
	}

}
