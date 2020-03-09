package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {
	
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<TbContentCategory> selByPid(long id) {
		//创建条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		//状态1 是没有被删除的
		example.createCriteria().andParentIdEqualTo(id).andStatusEqualTo(1);
		//返回查询结果
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int insTbContentCategory(TbContentCategory cate) throws Exception {
		
		int index = tbContentCategoryMapper.insertSelective(cate);
		if(index > 0 ) {
			return index;
		} else {
			throw new Exception("新增节点失败");
		}
	}

	@Override
	public int updTbContentCategoryById(TbContentCategory cate) throws Exception{
		TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(cate.getId());
		if(category.getStatus()==0){
			throw new Exception("类目已删除，无法再删除");
		} else {
			int index = tbContentCategoryMapper.updateByPrimaryKeySelective(cate);
			if(index > 0) {
				return index;
			} else {
				throw new Exception("修改类目失败");
			}
		}
	}

	@Override
	public TbContentCategory selById(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}
}
