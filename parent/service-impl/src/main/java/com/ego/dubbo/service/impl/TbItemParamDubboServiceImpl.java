package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{

	@Resource
	private TbItemParamMapper tbItemParaMapper;
	
	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		//设置分页条件
		PageHelper.startPage(page, rows);
		
		//如果使用 xxxxWithBlobs() 查询结果中包含带有 text 类的列,如果没有使用 withblobs() 方法不带有 text 类型.
		List<TbItemParam> list = tbItemParaMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		
		PageInfo<TbItemParam> pi = new PageInfo<>(list);

		//设置返回结果
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		return dataGrid;
	}

	@Override
	public int delByIds(String ids) throws Exception {
		//分割id
		String[] id = ids.split(",");
		
		//计算结果数
		int index = 0;
		
		for (String idStr : id) {
		
			index += tbItemParaMapper.deleteByPrimaryKey(Long.parseLong(idStr));
		}
		if(index == id.length){
			return 1;
		} else {
			throw new Exception("删除失败，原因：可能数据不存在");
		}
	}

	@Override
	public TbItemParam selByCatid(long catId) {
		//添加条件
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catId);
		
		List<TbItemParam> list = tbItemParaMapper.selectByExampleWithBLOBs(example);
		
		if(list != null && list.size() > 0){
			//要求每个类目只能有一个模板
			return list.get(0);
		}
		return null;
	}

	@Override
	public int insParam(TbItemParam param) throws Exception {
		int index = tbItemParaMapper.insertSelective(param);
		
		if(index != 1) {
			throw new Exception("添加不成功");
		}
		return index;
	}

}
