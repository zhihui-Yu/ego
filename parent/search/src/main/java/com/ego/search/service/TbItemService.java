package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

public interface TbItemService {
	/**
	 * 初始化  将所有信息传入solr
	 * @throws SolrServerException
	 * @throws IOException
	 */
	void init() throws SolrServerException,IOException;
	
	/**
	 * 查询功能
	 * @param query
	 * @param page
	 * @param rows
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	Map<String,Object> selByQuery(String query, int page, int rows) throws SolrServerException,IOException;
	
	/**
	 * 新增：新增时需要在solr中添加信息
	 * @param map
	 * @param desc
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	int add(Map<String,Object> map,String desc) throws SolrServerException,IOException;
}
