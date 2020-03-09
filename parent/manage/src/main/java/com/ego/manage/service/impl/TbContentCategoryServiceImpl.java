package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
	
	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;
	
	@Override
	public List<EasyUiTree> showCategroy(long id) {
		List<EasyUiTree> listTree = new ArrayList<>();
		
		List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(id);
		
		for (TbContentCategory cate : list) {
			EasyUiTree tree = new EasyUiTree();
			
			tree.setId(cate.getId());
			tree.setText(cate.getName());
			
			//设置打开关闭状态
			tree.setState(cate.getIsParent()?"closed":"open");
			
			listTree.add(tree);
		}
		return listTree;
	}

	@Override
	public EgoResult create(TbContentCategory cate) {
		EgoResult er = new EgoResult();
		
		//判断当前节点是不是已经存在
		List<TbContentCategory> childList = tbContentCategoryDubboServiceImpl.selByPid(cate.getParentId());
		
		for (TbContentCategory child : childList) {
			if(child.getName().equals(cate.getName())){
				er.setData("该分类名已存在");
				return er;
			}
		}
		
		Date date = new Date();
		cate.setCreated(date);
		cate.setUpdated(date);
		cate.setStatus(1);
		cate.setSortOrder(1);
		cate.setIsParent(false);
		long id = IDUtils.genItemId();
		cate.setId(id);
		int index = 0;
		try {
			index = tbContentCategoryDubboServiceImpl.insTbContentCategory(cate);
		} catch (Exception e) {
			er.setStatus(500);
			er.setData(e.getMessage());
			return er;
		}
		
		if(index > 0) {
			TbContentCategory parent = new TbContentCategory();
			parent.setId(cate.getParentId());
			parent.setIsParent(true);
			
			try {
				index += tbContentCategoryDubboServiceImpl.updTbContentCategoryById(parent);
			} catch (Exception e) {
				er.setStatus(500);
				er.setData(e.getMessage());
				return er;
			}
		}
		
		if(index == 2) {
			er.setStatus(200);
			Map<String,Long> map = new HashMap<>();
			map.put("id", id);
			er.setData(map);
		}
		return er;
	}

	@Override
	public int update(TbContentCategory cate) throws Exception {
		//判断是否有重复的名字
		TbContentCategory cateSelect = tbContentCategoryDubboServiceImpl.selById(cate.getId());
		
		List<TbContentCategory> child = tbContentCategoryDubboServiceImpl.selByPid(cateSelect.getParentId());
		for (TbContentCategory cates : child) {
			if(cates.getName().equals(cate.getName())) {
				return 0;
			}
		}
		
		return tbContentCategoryDubboServiceImpl.updTbContentCategoryById(cate);
	}

	@Override
	public EgoResult delete(TbContentCategory cate) {
		EgoResult er = new EgoResult();
		//设置状态为0 ---- 被删除状态为0
		cate.setStatus(0);
		int index = 0;
		try {
			index = tbContentCategoryDubboServiceImpl.updTbContentCategoryById(cate);
		} catch (Exception e) {
			er.setData(e.getMessage());
			er.setStatus(500);
		}
		
		//判断是否需要变换父类目的isParent属性
		if(index > 0) {
			TbContentCategory curr = tbContentCategoryDubboServiceImpl.selById(cate.getId());
			List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(curr.getParentId());
			if(list == null || list.size() == 0) {
				TbContentCategory parent = new TbContentCategory();
				parent.setId(curr.getParentId());
				parent.setIsParent(false);
				try {
					tbContentCategoryDubboServiceImpl.updTbContentCategoryById(parent);
					er.setStatus(200);
				} catch (Exception e) {
					er.setData("父类目更新失败");
				}
			}else {
				er.setStatus(200);
			}
		} 
		
		
		return er;
	}

}
