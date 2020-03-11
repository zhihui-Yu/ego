package com.ego.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.pojo.TbItemChild;
import com.ego.search.service.TbItemService;
@Service
public class TbItemServiceImpl implements TbItemService{
	
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Resource
	private CloudSolrClient solrClient;
	
	
	@Override
	public void init() throws SolrServerException, IOException {
		//查询所有正常的商品
		List<TbItem> listItem = tbItemDubboServiceImpl.selAllByStatus((byte)1);
		for (TbItem item : listItem) {
			//查找相应的类目信息
			TbItemCat itemCat = tbItemCatDubboServiceImpl.selById(item.getCid());
			//对应的商品描述信息
			TbItemDesc itemDesc = tbItemDescDubboServiceImpl.selByItemid(item.getId());
			
			SolrInputDocument doc = new SolrInputDocument();
			
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSellPoint());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_image", item.getImage());
			doc.addField("item_category_name", itemCat.getName());
			doc.addField("item_desc", itemDesc.getItemDesc());
			doc.setField("item_update", item.getUpdated());
			
			solrClient.add(doc);
		}
		
		solrClient.commit();
	}


	@Override
	public Map<String, Object> selByQuery(String query, int page, int rows) throws SolrServerException, IOException {
		
		SolrQuery params = new SolrQuery();
		
		//设置分页条件
		params.setStart(rows*(page-1));
		params.setRows(rows);
		
		//设置查询条件 query加""为精确查询  否则为模糊查询
		params.setQuery("item_keywords:"+query);
		
		//设置排序
		params.setSort("item_update", ORDER.desc);
		
		//设置高亮
		params.setHighlight(true);
		params.addHighlightField("item_title");
		params.setHighlightSimplePre("<span style='color:red'>");
		params.setHighlightSimplePost("</span>");
		
		QueryResponse response = solrClient.query(params);
		
		//商品信息 list
		List<TbItemChild> listChild = new ArrayList<>();
		
		//没有高亮的内容
		SolrDocumentList listSolr = response.getResults();
		
		//高亮
		Map<String, Map<String,List<String>>> hh = response.getHighlighting();
		
		for (SolrDocument doc : listSolr) {
			
			TbItemChild child = new TbItemChild();
			
			//设置id
			child.setId(Long.parseLong(doc.getFieldValue("id").toString()));
			
			//判断有无高亮部分
			List<String> list = hh.get(doc.getFieldValue("id")).get("item_title");
			if(list!=null && list.size()>0) {
				child.setTitle(list.get(0));
			} else {
				child.setTitle(doc.getFieldValue("id").toString());
			}
			
			//设置价格
			child.setPrice((Long)doc.getFieldValue("item_price"));
			
			//设置图片  转为数组类型
			Object image = doc.getFieldValue("item_image");
			
			child.setImages(image == null || image.equals("")? new String[1]:image.toString().split(","));
			
			listChild.add(child);
			
		}
		
		Map<String,Object> resultMap = new HashMap<>();
		
		resultMap.put("itemList", listChild);
		resultMap.put("totalPages", listSolr.getNumFound()%rows == 0 ? listSolr.getNumFound()/rows : (listSolr.getNumFound()/rows) + 1);
		return resultMap;
	}


	@Override
	public int add(Map<String, Object> map, String desc) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", map.get("id"));
		doc.setField("item_title", map.get("title"));
		doc.setField("item_sell_point", map.get("sellPoint"));
		doc.setField("item_price", map.get("price"));
		doc.setField("item_image", map.get("image"));
		doc.setField("item_category_name", tbItemCatDubboServiceImpl.selById(Integer.parseInt(map.get("cid").toString())).getName());
		doc.setField("item_desc", desc);
		
		UpdateResponse response = solrClient.add(doc);
		solrClient.commit();
		
		if(response.getStatus() == 0){
			return 1;
		}
		return 0;
	}

}
